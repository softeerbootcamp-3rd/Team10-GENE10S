package com.genesisairport.reservation.controller.admin;

import com.genesisairport.reservation.common.GeneralException;
import com.genesisairport.reservation.common.ResponseCode;
import com.genesisairport.reservation.common.ResponseDto;
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

}
