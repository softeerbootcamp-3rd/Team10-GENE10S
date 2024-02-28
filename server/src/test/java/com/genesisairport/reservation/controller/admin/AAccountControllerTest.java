package com.genesisairport.reservation.controller.admin;

import com.genesisairport.reservation.common.enums.ResponseCode;
import com.genesisairport.reservation.common.exception.GeneralException;
import com.genesisairport.reservation.common.model.DataResponseDto;
import com.genesisairport.reservation.common.model.PageInfo;
import com.genesisairport.reservation.common.model.PageResponseDto;
import com.genesisairport.reservation.common.model.ResponseDto;
import com.genesisairport.reservation.common.util.RedisUtil;
import com.genesisairport.reservation.entity.Admin;
import com.genesisairport.reservation.request.AdminRequest;
import com.genesisairport.reservation.response.AdminResponse;
import com.genesisairport.reservation.service.admin.AAccountService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AAccountControllerTest {

    @Mock
    private AAccountService adminAccountService;

    @Mock
    private RedisUtil redisUtil;

    @Mock
    private HttpServletRequest httpServletRequest;

    @InjectMocks
    private AAccountController adminAccountController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void adminLogin_ValidLogin_ReturnsOk() {
        AdminRequest.Login loginDto = new AdminRequest.Login("admin", "password");
        Admin admin = new Admin(1L, "admin", "Admin Name", "adminpassword", "1234567890", LocalDateTime.now(), LocalDateTime.now());
        when(adminAccountService.adminLogin(loginDto)).thenReturn(admin);
        HttpSession httpSession = mock(HttpSession.class);
        when(httpServletRequest.getSession(true)).thenReturn(httpSession);

        ResponseEntity<DataResponseDto<AdminResponse.AdminInfo>> responseEntity = adminAccountController.adminLogin(loginDto, httpServletRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("Admin Name", responseEntity.getBody().getData().getAdminName());
    }

    @Test
    void adminLogout_ValidSession_LogoutSuccessfully() {
        HttpSession httpSession = mock(HttpSession.class);
        when(httpServletRequest.getSession(false)).thenReturn(httpSession);

        ResponseEntity<ResponseDto> responseEntity = adminAccountController.adminLogout(httpServletRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().getSuccess());
        assertEquals(ResponseCode.OK.getCode(), responseEntity.getBody().getCode());
        verify(httpSession).invalidate();
    }

    @Test
    void adminLogout_InvalidSession_ThrowsException() {
        when(httpServletRequest.getSession(false)).thenReturn(null);

        assertThrows(GeneralException.class, () -> adminAccountController.adminLogout(httpServletRequest));
    }

    @Test
    void isValidSession_InvalidSession_ReturnsFalse() {
        when(httpServletRequest.getSession(false)).thenReturn(null);

        ResponseEntity<DataResponseDto<Boolean>> responseEntity = adminAccountController.isValidSession(httpServletRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertFalse(responseEntity.getBody().getData());
    }
}
