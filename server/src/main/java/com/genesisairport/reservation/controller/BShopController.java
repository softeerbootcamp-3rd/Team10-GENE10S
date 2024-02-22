package com.genesisairport.reservation.controller;

import com.genesisairport.reservation.common.enums.ResponseCode;
import com.genesisairport.reservation.common.model.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/test")
public class BShopController {

    @GetMapping("/")
    public ResponseEntity<ResponseDto> deleteAvailableDateWithReservation() {
        log.debug("예약되어있는 시간 삭제");
        log.info("예약되어있는 시간 삭제");
        System.out.println("shopb test");
        return new ResponseEntity<>(ResponseDto.of(true, ResponseCode.OK), HttpStatus.OK);

    }

    @GetMapping("/asdf")
    public String asdf() {
        return "asdf";
    }
}