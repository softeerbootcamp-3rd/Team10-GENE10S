package com.genesisairport.reservation.controller;

import com.genesisairport.reservation.request.ReservationRequest;
import com.genesisairport.reservation.common.DataResponseDto;


import com.genesisairport.reservation.entity.Customer;
import com.genesisairport.reservation.response.ReservationResponse;
import com.genesisairport.reservation.service.ReservationService;

import com.genesisairport.reservation.service.SessionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@Slf4j
@RequestMapping("/v1/reservation")
public class ReservationController {

    private final ReservationService reservationService;
    private final SessionService sessionService;

    @GetMapping("/coupon/valid")
    public ResponseEntity<DataResponseDto<ReservationResponse.CouponValid>> couponValidation(
            @RequestParam(value = "couponNumber") String serialNumber) {
        return new ResponseEntity<>(DataResponseDto.of(ReservationResponse.CouponValid.builder()
                .valid(reservationService.validateCoupon(serialNumber))
                .build()), HttpStatus.OK);
    }

    @GetMapping("/car-list")
    public ResponseEntity getCarList(HttpServletRequest request) {
        Long userId = SessionService.getUserIdFromSession(request);
        System.out.println("userId ===== " + userId);
        Optional<Customer> customer = sessionService.getLoggedInCustomer(request);
        // TODO: customer emtpy일 때 response 추가
        return new ResponseEntity(
                DataResponseDto.of(reservationService.getCarList(customer.get().getId())),
                HttpStatus.OK
        );
    }

    @GetMapping("/date")
    public ResponseEntity getAvailableDates(@RequestParam(value = "repairShop") String repairShop) {
        log.debug("예약 가능 날짜 확인 API");

        return new ResponseEntity(
                DataResponseDto.of(reservationService.getAvailableDates(repairShop)),
                HttpStatus.OK
        );
    }

    @GetMapping("/time")
    public ResponseEntity getAvailableTimes(
            @RequestParam(value = "repairShop") String repairShop,
            @RequestParam(value = "date") @DateTimeFormat(pattern = "yyyyMMdd") LocalDate date) {

        return new ResponseEntity(
                DataResponseDto.of(reservationService.getAvailableTimes(repairShop, date)),
                HttpStatus.OK
        );
    }


    @PostMapping
    public ResponseEntity<DataResponseDto<ReservationResponse.ReservationPostResponse>> saveReservation(HttpServletRequest request, @RequestBody ReservationRequest.ReservationPost requestBody) {
        log.debug("예약 정보 저장");

        Optional<Customer> customer = sessionService.getLoggedInCustomer(request);

        return new ResponseEntity<>(DataResponseDto.of(reservationService.reserve(customer.get().getId(), requestBody)), HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<DataResponseDto<List<ReservationResponse.ReservationInfoAbstract>>> getReservationList(HttpServletRequest request) {
        log.debug("특정 사용자 예약 내역 조회");

        Optional<Customer> customer = sessionService.getLoggedInCustomer(request);

        List<ReservationResponse.ReservationInfoAbstract> reservationList = reservationService.getReservationList(customer.get().getId());

        return new ResponseEntity<>(DataResponseDto.of(reservationList), HttpStatus.OK);
    }
}
