package com.genesisairport.reservation.controller.admin;

import com.genesisairport.reservation.common.enums.ResponseCode;
import com.genesisairport.reservation.common.exception.GeneralException;
import com.genesisairport.reservation.common.model.DataResponseDto;
import com.genesisairport.reservation.common.model.PageInfo;
import com.genesisairport.reservation.common.model.PageResponseDto;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public ResponseEntity<ResponseDto> searchAllReservations(
            Pageable pageable,
            HttpServletRequest request,
            @RequestParam(value = "shopName", required = false) String shopName,
            @RequestParam(value = "startPickUpDateTime", required = false) String startPickUpDateTime,
            @RequestParam(value = "endPickUpDateTime", required = false) String endPickUpDateTime,
            @RequestParam(value = "startReturnDateTime", required = false) String startReturnDateTime,
            @RequestParam(value = "endReturnDateTime", required = false) String endReturnDateTime,
            @RequestParam(value = "customerName", required = false) String customerName,
            @RequestParam(value = "sellName", required = false) String sellName,
            @RequestParam(value = "stage", required = false) String stage,
            @RequestParam(value = "sortColumn", required = false) String sortColumn,
            @RequestParam(value = "sortDirection", required = false) String sortDirection)
            {

        Long userId = SessionUtil.getAdminIdFromSession(request);

        if (!Objects.isNull(userId)) {
            throw new GeneralException(ResponseCode.FORBIDDEN, "로그인 정보를 불러오는 데에 실패했습니다.");
        }

        AdminRequest.ReservationDetail requestBody = AdminRequest.ReservationDetail
                .builder()
                .shopName(shopName)
                .startPickupDateTime(startPickUpDateTime)
                .endPickupDateTime(endPickUpDateTime)
                .startReturnDateTime(startReturnDateTime)
                .endReturnDateTime(endReturnDateTime)
                .customerName(customerName)
                .sellName(sellName)
                .stage(stage)
                .sortColumn(sortColumn)
                .sortDirection(sortDirection)
                .build();

        Page<AdminResponse.ReservationDetail> reservationDetailPage = aReservationService.getAllReservations(
                requestBody, pageable
        );

        List<AdminResponse.ReservationDetail> reservationDetailList = reservationDetailPage.getContent();
        PageInfo pageInfo = PageInfo.builder()
                .page(reservationDetailPage.getNumber())
                .size(reservationDetailPage.getSize())
                .totalElements(reservationDetailPage.getTotalElements())
                .totalPages(reservationDetailPage.getTotalPages())
                .build();

        return new ResponseEntity(
                PageResponseDto.of(reservationDetailList, pageInfo),
                HttpStatus.OK
        );
    }

    @PostMapping("/progress")
    public ResponseEntity<DataResponseDto<ReservationResponse.ProgressId>> registerStage(
            @RequestBody AdminRequest.StageInfo requestBody
    ) {
        if (requestBody.getProgress() == null)
            throw new GeneralException(ResponseCode.BAD_REQUEST, "진행 단계 ID를 입력해주세요.");

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
    public ResponseEntity<ResponseDto> updateComment(@RequestBody AdminRequest.CommentInfo requestBody) {

        if (requestBody.getReservationId() == null)
            throw new GeneralException(ResponseCode.BAD_REQUEST, "예약 아이디를 입력해주세요.");

        if (requestBody.getComment() == null)
            throw new GeneralException(ResponseCode.BAD_REQUEST, "댓글을 입력해주세요.");

        aReservationService.updateComment(requestBody);
        return new ResponseEntity<>(ResponseDto.of(true, ResponseCode.OK), HttpStatus.OK);
    }

    @GetMapping("/check")
    public ResponseEntity<ResponseDto> checkReservation(
            @RequestParam (value = "shopName") String shopName,
            @RequestParam (value = "businessDay") String businessDay) {

        if (shopName.isEmpty()) {
            throw new GeneralException(ResponseCode.BAD_REQUEST, "지점명을 입력해주세요.");
        }
        if (businessDay.isEmpty()) {
            throw new GeneralException(ResponseCode.BAD_REQUEST, "확인할 날짜를 입력해주세요.");
        }

        boolean hasReservation = aReservationService.checkReservation(shopName, businessDay);

        return new ResponseEntity<>(DataResponseDto.of(
                ReservationResponse.hasReservation.builder()
                        .hasReservation(hasReservation)
                        .build())
                , HttpStatus.OK);
    }
}
