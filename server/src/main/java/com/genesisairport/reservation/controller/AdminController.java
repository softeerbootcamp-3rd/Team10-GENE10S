package com.genesisairport.reservation.controller;

import com.genesisairport.reservation.common.ResponseCode;
import com.genesisairport.reservation.common.ResponseDto;
import com.genesisairport.reservation.request.AdminRequest;
import com.genesisairport.reservation.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/v2/admin")
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/reservation/progress")
    public ResponseEntity registerStage(
            @RequestBody AdminRequest.StageInfo requestBody) {
        log.debug("관리자 | 진행 단계 추가");

        adminService.saveStage(requestBody);
        return new ResponseEntity<>(ResponseDto.of(true, ResponseCode.OK), HttpStatus.OK);
    }

}
