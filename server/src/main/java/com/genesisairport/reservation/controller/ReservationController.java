package com.genesisairport.reservation.controller;

import com.genesisairport.reservation.Response.ReservationDateResponse;
import com.genesisairport.reservation.common.DataResponseDto;
import com.genesisairport.reservation.service.ReservationService;
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

    @GetMapping("/available/{repair_shop}")
    public ResponseEntity<List<ReservationDateResponse>> getAvailableDate() {
        log.debug("예약 가능 날짜 확인 API");

        return new ResponseEntity(reservationService.getAvailableDates(), HttpStatus.OK);
    }
}
