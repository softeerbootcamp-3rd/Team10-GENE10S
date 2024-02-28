package com.genesisairport.reservation.controller.admin;

import com.genesisairport.reservation.common.enums.ResponseCode;
import com.genesisairport.reservation.common.exception.GeneralException;
import com.genesisairport.reservation.common.model.DataResponseDto;
import com.genesisairport.reservation.common.model.ResponseDto;
import com.genesisairport.reservation.request.AdminRequest;
import com.genesisairport.reservation.service.admin.AShopService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AShopControllerTest {

    @Mock
    private AShopService aShopService;

    @InjectMocks
    private AShopController aShopController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAvailableDate_ValidData_ReturnsOk() {
        String shopName = "shop";
        String businessDayFrom = "2022-02-27";
        String businessDayTo = "2022-02-28";

        ResponseEntity<ResponseDto> responseEntity = aShopController.getAvailableDate(shopName, businessDayFrom, businessDayTo);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(aShopService).getAvailableTime(shopName, businessDayFrom, businessDayTo);
    }

    @Test
    void getAvailableDate_NullShopName_ThrowsBadRequestException() {
        assertThrows(GeneralException.class, () -> aShopController.getAvailableDate(null, "2022-02-27", "2022-02-28"));
    }

    @Test
    void getAvailableDate_NullBusinessDayFrom_ThrowsBadRequestException() {
        assertThrows(GeneralException.class, () -> aShopController.getAvailableDate("shop", null, "2022-02-28"));
    }

    @Test
    void getAvailableDate_NullBusinessDayTo_ThrowsBadRequestException() {
        assertThrows(GeneralException.class, () -> aShopController.getAvailableDate("shop", "2022-02-27", null));
    }

    @Test
    void deleteAvailableDateWithReservation_NullShopName_ThrowsBadRequestException() {
        assertThrows(GeneralException.class, () -> aShopController.deleteAvailableDateWithReservation(null, "2022-02-27", "message"));
    }

    @Test
    void deleteAvailableDateWithReservation_NullBusinessDay_ThrowsBadRequestException() {
        assertThrows(GeneralException.class, () -> aShopController.deleteAvailableDateWithReservation("shop", null, "message"));
    }

    @Test
    void deleteAvailableDateWithReservation_NullMessage_ThrowsBadRequestException() {
        assertThrows(GeneralException.class, () -> aShopController.deleteAvailableDateWithReservation("shop", "2022-02-27", null));
    }
}
