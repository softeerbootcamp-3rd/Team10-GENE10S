package com.genesisairport.reservation.common.util;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.genesisairport.reservation.common.enums.ResponseCode;
import com.genesisairport.reservation.common.exception.GeneralException;
import com.genesisairport.reservation.common.model.ResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.ServletWebRequest;

public class GlobalExceptionHandlerTest {

    @Test
    public void testHandleGeneralException() {
        GeneralException generalException = new GeneralException(ResponseCode.NOT_FOUND, "Resource not found");
        MockHttpServletRequest servletRequest = new MockHttpServletRequest();
        ServletWebRequest webRequest = new ServletWebRequest(servletRequest);

        GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();

        ResponseEntity<Object> responseEntity = exceptionHandler.handleGeneralException(generalException, webRequest);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        ResponseDto body = (ResponseDto) responseEntity.getBody();
        assertNotNull(body);
        assertFalse(body.getSuccess());
        assertEquals(ResponseCode.NOT_FOUND.getCode(), body.getCode());
        assertEquals("Resource not found", body.getMessage());
    }

    @Test
    public void testHandleException() {
        Exception exception = new Exception("Internal Server Error");
        MockHttpServletRequest servletRequest = new MockHttpServletRequest();
        ServletWebRequest webRequest = new ServletWebRequest(servletRequest);

        GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();

        ResponseEntity<Object> responseEntity = exceptionHandler.exception(exception, webRequest);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        ResponseDto body = (ResponseDto) responseEntity.getBody();
        assertNotNull(body);
        assertFalse(body.getSuccess());
        assertEquals(ResponseCode.INTERNAL_SERVER_ERROR.getCode(), body.getCode());
        assertEquals("Internal Server Error", body.getMessage());
    }
}
