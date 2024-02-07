package com.genesisairport.reservation.controller;

import com.genesisairport.reservation.common.DataResponseDto;
import com.genesisairport.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
