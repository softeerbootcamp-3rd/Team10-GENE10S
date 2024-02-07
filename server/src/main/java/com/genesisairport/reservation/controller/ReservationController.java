package com.genesisairport.reservation.controller;

import com.genesisairport.reservation.Response.ReservationListAbstract;
import com.genesisairport.reservation.Response.ReservationPostResponse;
import com.genesisairport.reservation.common.DataResponseDto;
import com.genesisairport.reservation.service.ReservationService;
import com.genesisairport.reservation.Response.ReservationDateResponse;

import lombok.Data;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
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

    @PostMapping("/")
    public ResponseEntity<DataResponseDto<ReservationPostResponse>> saveReservation(@RequestBody Map<String, Object> requestBody) {
        log.debug("예약 정보 저장");

        return new ResponseEntity<>(DataResponseDto.of(reservationService.reserve(requestBody)), HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<DataResponseDto<ReservationListAbstract>> getReservationList() {
        log.debug("특정 사용자 예약 내역 조회");

        ReservationListAbstract reservationList = reservationService.getReservationList();

        return new ResponseEntity<>(DataResponseDto.of(reservationList), HttpStatus.OK);
    }
}
