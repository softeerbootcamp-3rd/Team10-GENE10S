package com.genesisairport.reservation.controller;

import com.genesisairport.reservation.common.enums.ResponseCode;
import com.genesisairport.reservation.common.exception.GeneralException;
import com.genesisairport.reservation.common.model.DataResponseDto;
import com.genesisairport.reservation.request.ReservationRequest;
import com.genesisairport.reservation.response.ReservationResponse;
import com.genesisairport.reservation.service.ReservationService;
import com.genesisairport.reservation.common.util.SessionUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ReservationControllerTest {

    @InjectMocks
    private ReservationController reservationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void couponValidation_ValidSerialNumber_ReturnsOk() {
        String serialNumber = "ABC123";
        ReservationResponse.CouponValid couponValid = new ReservationResponse.CouponValid(true);

        ResponseEntity<DataResponseDto<ReservationResponse.CouponValid>> responseEntity = reservationController.couponValidation(serialNumber);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}
