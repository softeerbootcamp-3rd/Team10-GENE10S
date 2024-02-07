package com.genesisairport.reservation.controller;

import com.genesisairport.reservation.Response.CouponValidResponse;
import com.genesisairport.reservation.common.DataResponseDto;
import com.genesisairport.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@Slf4j
@RequestMapping("/v1")
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping("/reservation/coupon/valid")
    public ResponseEntity<DataResponseDto<CouponValidResponse.Dto>> couponValidation(
            @RequestParam(value = "couponNumber") String serialNumber) {
        return new ResponseEntity<>(DataResponseDto.of(CouponValidResponse.Dto.builder()
                .valid(reservationService.validateCoupon(serialNumber))
                .build()), HttpStatus.OK);
    }
}
