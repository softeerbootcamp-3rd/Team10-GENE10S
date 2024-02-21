package com.genesisairport.reservation.controller.admin;

import com.genesisairport.reservation.common.enums.ResponseCode;
import com.genesisairport.reservation.common.exception.GeneralException;

import com.genesisairport.reservation.common.model.DataResponseDto;
import com.genesisairport.reservation.common.model.ResponseDto;
import com.genesisairport.reservation.common.util.SessionUtil;

import com.genesisairport.reservation.entity.MaintenanceImage;
import com.genesisairport.reservation.request.AdminRequest;
import com.genesisairport.reservation.response.AdminResponse;
import com.genesisairport.reservation.response.ReservationResponse;
import com.genesisairport.reservation.service.S3Service;
import com.genesisairport.reservation.service.admin.AReservationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/v2/admin/reservation")
public class AReservationController {
    private final AReservationService aReservationService;
    private final S3Service s3Service;

    @PostMapping("/{id}/image")
    public ResponseEntity<DataResponseDto<AdminResponse.UploadImage>>
        uploadImage(@PathVariable("id") Long id,
                    @RequestParam("status") Integer status,
                    @RequestPart("image") MultipartFile image) throws IOException {
        S3Service.S3Result s3Result = s3Service.saveFile(image);
        AdminResponse.UploadImage result =
                aReservationService.addMaintenanceImage(id, status, s3Result.getUrl(), s3Result.getObjectKey());
        return new ResponseEntity<>(DataResponseDto.of(result), HttpStatus.OK);
    }

    @DeleteMapping("/image/{id}")
    public ResponseEntity<ResponseDto> deleteImage(@PathVariable("id") Long id) {
        MaintenanceImage image = aReservationService.getMaintenanceImage(id);
        s3Service.deleteFile(image.getObjectKey());
        aReservationService.deleteMaintenanceImage(id);
        return new ResponseEntity<>(ResponseDto.of(true, ResponseCode.OK), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<DataResponseDto<List<AdminResponse.ReservationDetail>>>
        searchAllReservations(HttpServletRequest request, @RequestBody AdminRequest.ReservationDetail requestBody) {
        Long userId = SessionUtil.getAdminIdFromSession(request);

        if (!Objects.isNull(userId)) {
            throw new GeneralException(ResponseCode.BAD_REQUEST, "로그인이 필요합니다.");
        }
        return new ResponseEntity<>(
                DataResponseDto.of(aReservationService.getAllReservations(requestBody)),
                HttpStatus.OK
        );
    }

    @PostMapping("/progress")
    public ResponseEntity<DataResponseDto<ReservationResponse.ProgressId>> registerStage(
            @RequestBody AdminRequest.StageInfo requestBody
    ) {
        if (requestBody.getProgress() == null)
            throw new GeneralException(ResponseCode.BAD_REQUEST, "유효하지 않은 진행단계입니다.");

        Long insertedId = aReservationService.saveStage(requestBody);
        return new ResponseEntity<>(DataResponseDto.of(
                ReservationResponse.ProgressId.builder()
                        .stepId(insertedId)
                        .build()),
                HttpStatus.OK);
    }

    @DeleteMapping("/progress/{stepId}")
    public ResponseEntity<ResponseDto> deleteStage(@PathVariable Long stepId) {
        aReservationService.deleteStage(stepId);
        return new ResponseEntity<>(ResponseDto.of(true, ResponseCode.OK), HttpStatus.OK);
    }

    @PutMapping("/comment")
    public ResponseEntity updateComment(@RequestBody AdminRequest.CommentInfo requestBody) {

        if (requestBody.getReservationId() == null)
            throw new GeneralException(ResponseCode.INTERNAL_SERVER_ERROR, "예약 Id를 받아오지 못했습니다.");

        if (requestBody.getComment() == null)
            throw new GeneralException(ResponseCode.INTERNAL_SERVER_ERROR, "코멘트를 받아오지 못했습니다.");

        aReservationService.updateComment(requestBody);

        return new ResponseEntity<>(ResponseDto.of(true, ResponseCode.OK), HttpStatus.OK);
    }
}
