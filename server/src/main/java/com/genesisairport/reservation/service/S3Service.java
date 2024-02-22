package com.genesisairport.reservation.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.genesisairport.reservation.common.enums.ResponseCode;
import com.genesisairport.reservation.common.exception.GeneralException;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class S3Service {
    private final AmazonS3 s3Client;

    @Value("${S3_BUCKET}")
    private String bucket;

    private final String folderName = "maintenance/";

    @Getter
    @Builder
    public static class S3Result {
        private String url;
        private String objectKey;
    }

    public S3Result saveFile(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();

        // 파일 이름에서 확장자 추출
        String fileExtension = "";
        assert originalFilename != null;
        int lastDotIndex = originalFilename.lastIndexOf(".");
        if (lastDotIndex != -1) {
            fileExtension = originalFilename.substring(lastDotIndex);
        }

        // 중복되지 않은 파일 이름 생성
        String uniqueFilename = originalFilename;
        if (doesFileExist(folderName + uniqueFilename)) {
            int count = 1;
            while (doesFileExist(folderName + uniqueFilename)) {
                uniqueFilename = originalFilename.substring(0, lastDotIndex) + "(" + count + ")" + fileExtension;
                count++;
            }
        }

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        s3Client.putObject(
                new PutObjectRequest(bucket, folderName + uniqueFilename, file.getInputStream(), metadata)
        );
        return S3Result.builder()
                .url(s3Client.getUrl(bucket, folderName + uniqueFilename).toString())
                .objectKey(folderName + uniqueFilename)
                .build();
    }

    public void deleteFile(String objectKey) {
        try {
            s3Client.deleteObject(bucket, objectKey);
        } catch (AmazonS3Exception e) {
            e.printStackTrace();
            if (e.getStatusCode() != HttpStatus.NOT_FOUND.value()) {
                throw new GeneralException(ResponseCode.INTERNAL_SERVER_ERROR, "S3 파일 삭제 중 오류가 발생했습니다.");
            }
        }
    }

    private boolean doesFileExist(String filename) {
        try {
            s3Client.getObjectMetadata(bucket, filename);
            return true;
        } catch (AmazonS3Exception e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND.value()) {
                return false;
            }
            throw e;
        }
    }
}
