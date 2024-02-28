package com.genesisairport.reservation.controller;

import com.genesisairport.reservation.common.enums.ResponseCode;
import com.genesisairport.reservation.common.exception.GeneralException;
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

    @GetMapping("/coupon/valid")
    public ResponseEntity<DataResponseDto<ReservationResponse.CouponValid>> couponValidation(
            @RequestParam(value = "couponNumber") String serialNumber) {
        return new ResponseEntity<>(DataResponseDto.of(ReservationResponse.CouponValid.builder()
                .valid(reservationService.validateCoupon(serialNumber))
                .build()), HttpStatus.OK);
    }

    @GetMapping("/car-list")
    public ResponseEntity<DataResponseDto<List<ReservationResponse.CarInfo>>>
        getCarList(final HttpServletRequest request)
    {
        Long userId = SessionUtil.getUserIdFromSession(request);
        return new ResponseEntity<>(
                DataResponseDto.of(reservationService.getCarList(userId)),
                HttpStatus.OK
        );
    }

    @GetMapping("/date")
    public ResponseEntity<DataResponseDto<ReservationResponse.DateInfo>> getAvailableDates(@RequestParam(value = "repairShop") String repairShop) {
        log.debug("예약 가능 날짜 확인 API");

        return new ResponseEntity<>(
                DataResponseDto.of(reservationService.getAvailableDates(repairShop)),
                HttpStatus.OK
        );
    }

    @GetMapping("/time")
    public ResponseEntity<DataResponseDto<ReservationResponse.TimeList>> getAvailableTimes(
            @RequestParam(value = "repairShop") String repairShop,
            @RequestParam(value = "date") @DateTimeFormat(pattern = "yyyyMMdd") LocalDate date) {

        return new ResponseEntity<>(
                DataResponseDto.of(reservationService.getAvailableTimes(repairShop, date)),
                HttpStatus.OK
        );
    }


    @PostMapping
    public ResponseEntity<DataResponseDto<ReservationResponse.ReservationPostResponse>>
        saveReservation(final HttpServletRequest request, @RequestBody ReservationRequest.ReservationPost requestBody)
    {
        log.debug("예약 정보 저장");

        Long userId = SessionUtil.getUserIdFromSession(request);
        return new ResponseEntity<>(
                DataResponseDto.of(reservationService.reserve(userId, requestBody), "예약되었습니다."),
                HttpStatus.OK
        );
    }

    @GetMapping("/list")
    public ResponseEntity<DataResponseDto<List<ReservationResponse.ReservationInfoAbstract>>>
        getReservationList(final HttpServletRequest request)
    {
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
            throw new GeneralException(ResponseCode.NOT_FOUND, "예약 내역이 존재하지 않습니다.");
        }

        return new ResponseEntity<>(DataResponseDto.of(reservationDetail.get()), HttpStatus.OK);
    }
}
