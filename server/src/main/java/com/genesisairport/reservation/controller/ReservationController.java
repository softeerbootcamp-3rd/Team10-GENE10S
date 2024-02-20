package com.genesisairport.reservation.controller;

import com.genesisairport.reservation.common.enums.ResponseCode;
import com.genesisairport.reservation.common.model.ResponseDto;
import com.genesisairport.reservation.request.ReservationRequest;
import com.genesisairport.reservation.common.model.DataResponseDto;


import com.genesisairport.reservation.response.ReservationResponse;
import com.genesisairport.reservation.service.ReservationService;

import com.genesisairport.reservation.common.util.SessionUtil;
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
@Slf4j
@RequestMapping("/v1/reservation")
public class ReservationController {

    private final ReservationService reservationService;
    private final SessionUtil sessionUtil;

    @GetMapping("/coupon/valid")
    public ResponseEntity<DataResponseDto<ReservationResponse.CouponValid>> couponValidation(
            @RequestParam(value = "couponNumber") String serialNumber) {
        return new ResponseEntity<>(DataResponseDto.of(ReservationResponse.CouponValid.builder()
                .valid(reservationService.validateCoupon(serialNumber))
                .build()), HttpStatus.OK);
    }

    @GetMapping("/car-list")
    public ResponseEntity getCarList(final HttpServletRequest request) {
        // TODO: customer emtpy일 때 response 추가
        Long userId = SessionUtil.getUserIdFromSession(request);
        return new ResponseEntity(
                DataResponseDto.of(reservationService.getCarList(userId)),
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
    public ResponseEntity saveReservation(final HttpServletRequest request, @RequestBody ReservationRequest.ReservationPost requestBody) {
        log.debug("예약 정보 저장");

        Long userId = SessionUtil.getUserIdFromSession(request);
        return new ResponseEntity<>(DataResponseDto.of(reservationService.reserve(userId, requestBody)), HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity getReservationList(final HttpServletRequest request) {
        log.debug("특정 사용자 예약 내역 조회");

        Long userId = SessionUtil.getUserIdFromSession(request);
        List<ReservationResponse.ReservationInfoAbstract> reservationList = reservationService.getReservationList(userId);

        return new ResponseEntity<>(DataResponseDto.of(reservationList), HttpStatus.OK);
    }

    @GetMapping("{reservationId}/detail")
    public ResponseEntity<ResponseDto> getReservationDetail(@PathVariable(value = "reservationId") long reservationId) {
        log.debug("예약 내역 상세 조회");

        Optional<ReservationResponse.ReservationDetail> reservationDetail = reservationService.getReservationDetail(reservationId);
        if (reservationDetail.isEmpty()) {
            return new ResponseEntity<>(ResponseDto.of(false, ResponseCode.INTERNAL_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(DataResponseDto.of(reservationDetail.get()), HttpStatus.OK);
    }
}
