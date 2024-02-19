package com.genesisairport.reservation.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
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

    public String saveFile(MultipartFile file) throws IOException {
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
                        .withCannedAcl(CannedAccessControlList.PublicRead)
        );
        return s3Client.getUrl(bucket, folderName + uniqueFilename).toString();
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
