package com.genesisairport.reservation.controller.admin;

import com.genesisairport.reservation.common.enums.ResponseCode;
import com.genesisairport.reservation.common.exception.GeneralException;
import com.genesisairport.reservation.common.model.DataResponseDto;
import com.genesisairport.reservation.common.model.ResponseDto;
import com.genesisairport.reservation.common.util.SessionUtil;
import com.genesisairport.reservation.request.AdminRequest;
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
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/v2/admin/reservation")
public class AReservationController {
    private final AReservationService aReservationService;
    private final S3Service s3Service;

    @PostMapping("/{id}/image")
    public ResponseEntity<ResponseDto> uploadImage(@PathVariable("id") Long id,
                                                   @RequestParam("status") Integer status,
                                                   @RequestPart("image") MultipartFile image) throws IOException {
        String imageUrl = s3Service.saveFile(image);
        aReservationService.addMaintenanceImage(id, status, imageUrl);
        return new ResponseEntity<>(ResponseDto.of(true, ResponseCode.OK), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseDto> searchAllReservations(HttpServletRequest request, @RequestBody AdminRequest.ReservationDetail requestBody) {
        Long userId = SessionUtil.getAdminIdFromSession(request);

        if (!Objects.isNull(userId)) {
            throw new GeneralException(ResponseCode.BAD_REQUEST, "로그인이 필요합니다.");
        }
        return new ResponseEntity(
                DataResponseDto.of(aReservationService.getAllReservations(requestBody)),
                HttpStatus.OK
        );
    }

    @PostMapping("/progress")
    public ResponseEntity registerStage(
            @RequestBody AdminRequest.StageInfo requestBody) {
        log.debug("관리자 | 진행 단계 추가");

        if (requestBody.getProgress() == null) {
            throw new GeneralException(ResponseCode.INTERNAL_ERROR, "유효하지 않은 진행단계입니다.");
        }

        aReservationService.saveStage(requestBody);
        return new ResponseEntity<>(ResponseDto.of(true, ResponseCode.OK), HttpStatus.OK);
    }

    @DeleteMapping("/progress")
    public ResponseEntity deleteStage(
            @RequestBody AdminRequest.StageInfo requestBody) {
        log.debug("관리자 | 진행 단계 삭제");

        if (requestBody.getProgress() == null) {
            throw new GeneralException(ResponseCode.INTERNAL_ERROR, "유효하지 않은 진행단계입니다.");
        }

        aReservationService.deleteStage(requestBody);
        return new ResponseEntity<>(ResponseDto.of(true, ResponseCode.OK), HttpStatus.OK);
    }
}