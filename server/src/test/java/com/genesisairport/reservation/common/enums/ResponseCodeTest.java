package com.genesisairport.reservation.common.enums;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class ResponseCodeTest {

    @Test
    public void testGetMessage() {
        ResponseCode responseCode = ResponseCode.BAD_REQUEST;
        assertEquals("Bad request", responseCode.getMessage());
    }

    @Test
    public void testGetMessageWithCustomMessage() {
        ResponseCode responseCode = ResponseCode.BAD_REQUEST;
        String customMessage = "Custom error message";
        assertEquals(customMessage, responseCode.getMessage(customMessage));
    }

    @Test
    public void testGetMessageWithThrowable() {
        ResponseCode responseCode = ResponseCode.BAD_REQUEST;
        String errorMessage = "Reason why it isn't valid";
        Throwable exception = new Exception(errorMessage);
        String expectedMessage = errorMessage;
        assertEquals(expectedMessage, responseCode.getMessage(exception));
    }

    @Test
    public void testValueOfWithHttpStatus() {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ResponseCode responseCode = ResponseCode.valueOf(status);
        assertEquals(ResponseCode.BAD_REQUEST, responseCode);
    }

    @Test
    public void testValueOfWithUnknownHttpStatus() {
        HttpStatus status = HttpStatus.NOT_IMPLEMENTED;
        ResponseCode responseCode = ResponseCode.valueOf(status);
        assertEquals(ResponseCode.INTERNAL_SERVER_ERROR, responseCode);
    }

    @Test
    public void testToString() {
        ResponseCode responseCode = ResponseCode.BAD_REQUEST;
        assertEquals("BAD_REQUEST (400)", responseCode.toString());
    }
}
