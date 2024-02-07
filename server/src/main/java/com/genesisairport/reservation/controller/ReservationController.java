package com.genesisairport.reservation.controller;

import com.genesisairport.reservation.common.DataResponseDto;
import com.genesisairport.reservation.service.ReservationService;
import com.genesisairport.reservation.response.ReservationDateResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@Slf4j
@RequestMapping("/v1/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping("/car-list")
    public ResponseEntity getCarList() {
        return new ResponseEntity(
                DataResponseDto.of(reservationService.getCarList()),
                HttpStatus.OK
        );
    }


    @GetMapping("/available/{repair_shop}")
    public ResponseEntity<DataResponseDto<Map<String, List<ReservationDateResponse>>>> getAvailableDate() {
        log.debug("예약 가능 날짜 확인 API");

        return new ResponseEntity<>(DataResponseDto.of(reservationService.getAvailableDates()), HttpStatus.OK);
    }
}
