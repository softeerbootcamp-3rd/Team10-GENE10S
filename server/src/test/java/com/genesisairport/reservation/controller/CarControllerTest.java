package com.genesisairport.reservation.controller;

import com.genesisairport.reservation.common.exception.GeneralException;
import com.genesisairport.reservation.common.model.ResponseDto;
import com.genesisairport.reservation.request.CarRequest;
import com.genesisairport.reservation.service.CarService;
import com.genesisairport.reservation.common.util.SessionUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CarControllerTest {

    @Mock
    private CarService carService;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private CarController carController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void registerVehicle_InvalidSession_ThrowsForbiddenException() {
        CarRequest.CarPost requestBody = new CarRequest.CarPost(/* populate with valid data */);

        when(SessionUtil.getUserIdFromSession(request)).thenReturn(null);

        assertThrows(GeneralException.class, () -> carController.registerVehicle(request, requestBody));
        verifyNoInteractions(carService);
    }

    @Test
    void deleteVehicle_InvalidSession_ThrowsForbiddenException() {
        Long carId = 1L;

        when(SessionUtil.getUserIdFromSession(request)).thenReturn(null);

        assertThrows(GeneralException.class, () -> carController.deleteVehicle(request, carId));
        verifyNoInteractions(carService);
    }
}
