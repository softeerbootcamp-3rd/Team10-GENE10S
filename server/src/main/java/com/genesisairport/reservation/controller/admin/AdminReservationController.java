package com.genesisairport.reservation.controller.admin;

import com.genesisairport.reservation.common.exception.GeneralException;
import com.genesisairport.reservation.common.enums.ResponseCode;
import com.genesisairport.reservation.common.model.ResponseDto;
import com.genesisairport.reservation.request.AdminRequest;
import com.genesisairport.reservation.service.admin.AdminReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/v2/admin")
public class AdminReservationController {

    private final AdminReservationService adminReservationService;

    @PostMapping("/reservation/progress")
    public ResponseEntity registerStage(
            @RequestBody AdminRequest.StageInfo requestBody) {
        log.debug("관리자 | 진행 단계 추가");

        if (requestBody.getProgress() == null) {
            throw new GeneralException(ResponseCode.INTERNAL_ERROR, "유효하지 않은 진행단계입니다.");
        }

        adminReservationService.saveStage(requestBody);
        return new ResponseEntity<>(ResponseDto.of(true, ResponseCode.OK), HttpStatus.OK);
    }

    @DeleteMapping("/reservation/progress")
    public ResponseEntity deleteStage(
            @RequestBody AdminRequest.StageInfo requestBody) {
        log.debug("관리자 | 진행 단계 삭제");

        if (requestBody.getProgress() == null) {
            throw new GeneralException(ResponseCode.INTERNAL_ERROR, "유효하지 않은 진행단계입니다.");
        }

        adminReservationService.deleteStage(requestBody);
        return new ResponseEntity<>(ResponseDto.of(true, ResponseCode.OK), HttpStatus.OK);
    }

    @PutMapping("/reservation/comment")
    public ResponseEntity updateComment(@RequestBody AdminRequest.CommentInfo requestBody) {
        log.debug("관리자 | 정비소 코멘트 업데이트");

        if (requestBody.getReservationId() == null)
            throw new GeneralException(ResponseCode.INTERNAL_ERROR, "예약 Id를 받아오지 못했습니다.");

        if (requestBody.getComment() == null)
            throw new GeneralException(ResponseCode.INTERNAL_ERROR, "코멘트를 받아오지 못했습니다.");

        return new ResponseEntity<>(ResponseDto.of(true, ResponseCode.OK), HttpStatus.OK);
    }

}
