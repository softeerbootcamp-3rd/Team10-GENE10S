package com.genesisairport.reservation.controller.admin;

import com.genesisairport.reservation.common.enums.ProgressStage;
import com.genesisairport.reservation.common.enums.ResponseCode;
import com.genesisairport.reservation.common.exception.GeneralException;
import com.genesisairport.reservation.common.model.*;
import com.genesisairport.reservation.common.util.SessionUtil;
import com.genesisairport.reservation.entity.MaintenanceImage;
import com.genesisairport.reservation.request.AdminRequest;
import com.genesisairport.reservation.response.AdminResponse;
import com.genesisairport.reservation.response.ReservationResponse;
import com.genesisairport.reservation.service.S3Service;
import com.genesisairport.reservation.service.admin.AReservationService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AReservationControllerTest {

    @Mock
    private AReservationService aReservationService;

    @Mock
    private S3Service s3Service;

    @Mock
    private HttpServletRequest httpServletRequest;

    @InjectMocks
    private AReservationController aReservationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void deleteImage_ValidId_ReturnsOk() {
        Long id = 1L;
        MaintenanceImage maintenanceImage = mock(MaintenanceImage.class);
        when(aReservationService.getMaintenanceImage(id)).thenReturn(maintenanceImage);

        ResponseEntity<ResponseDto> responseEntity = aReservationController.deleteImage(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().getSuccess());
        assertEquals(ResponseCode.OK.getCode(), responseEntity.getBody().getCode());
        verify(s3Service).deleteFile(maintenanceImage.getObjectKey());
        verify(aReservationService).deleteMaintenanceImage(id);
    }

    @Test
    void registerStage_ValidData_ReturnsOk() {
        AdminRequest.StageInfo requestBody = new AdminRequest.StageInfo(1L, ProgressStage.COMPLETED, "stage");
        when(aReservationService.saveStage(requestBody)).thenReturn(1L);

        ResponseEntity<DataResponseDto<ReservationResponse.ProgressId>> responseEntity =
                aReservationController.registerStage(requestBody);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(1L, responseEntity.getBody().getData().getStepId());
    }

    @Test
    void deleteStage_ValidId_ReturnsOk() {
        Long stepId = 1L;

        ResponseEntity<ResponseDto> responseEntity = aReservationController.deleteStage(stepId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().getSuccess());
        assertEquals(ResponseCode.OK.getCode(), responseEntity.getBody().getCode());
        verify(aReservationService).deleteStage(stepId);
    }

    @Test
    void updateComment_ValidData_ReturnsOk() {
        AdminRequest.CommentInfo requestBody = new AdminRequest.CommentInfo(1L, "comment");
        
        ResponseEntity<ResponseDto> responseEntity = aReservationController.updateComment(requestBody);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().getSuccess());
        assertEquals(ResponseCode.OK.getCode(), responseEntity.getBody().getCode());
        verify(aReservationService).updateComment(requestBody);
    }

    @Test
    void checkReservation_ValidData_ReturnsOk() {
        String shopName = "shop";
        String businessDay = "2022-02-27";

        ResponseEntity<ResponseDto> responseEntity =
                aReservationController.checkReservation(shopName, businessDay);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertTrue(responseEntity.getBody().getSuccess());
        assertEquals(ResponseCode.OK.getCode(), responseEntity.getBody().getCode());
    }
}
