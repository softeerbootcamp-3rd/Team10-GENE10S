package com.genesisairport.reservation.common.model;

import static org.junit.jupiter.api.Assertions.*;

import com.genesisairport.reservation.common.enums.ResponseCode;
import org.junit.jupiter.api.Test;

public class ResponseDtoTest {

    @Test
    public void testConstructorAndGetters() {
        Boolean success = true;
        Integer code = 200;
        String message = "Success";

        ResponseDto responseDto = new ResponseDto(success, code, message);

        assertEquals(success, responseDto.getSuccess());
        assertEquals(code, responseDto.getCode());
        assertEquals(message, responseDto.getMessage());
    }

    @Test
    public void testStaticOfMethodWithResponseCode() {
        Boolean success = true;
        ResponseCode responseCode = ResponseCode.OK;

        ResponseDto responseDto = ResponseDto.of(success, responseCode);

        assertEquals(success, responseDto.getSuccess());
        assertEquals(responseCode.getCode(), responseDto.getCode());
        assertEquals(responseCode.getMessage(), responseDto.getMessage());
    }

    @Test
    public void testStaticOfMethodWithResponseCodeAndException() {
        Boolean success = false;
        ResponseCode responseCode = ResponseCode.INTERNAL_SERVER_ERROR;
        Exception exception = new Exception("Internal Server Error");

        ResponseDto responseDto = ResponseDto.of(success, responseCode, exception);

        assertEquals(success, responseDto.getSuccess());
        assertEquals(responseCode.getCode(), responseDto.getCode());
        assertEquals("Internal Server Error", responseDto.getMessage());
    }

    @Test
    public void testStaticOfMethodWithResponseCodeAndMessage() {
        Boolean success = false;
        ResponseCode responseCode = ResponseCode.BAD_REQUEST;
        String customMessage = "Bad Request Error";

        ResponseDto responseDto = ResponseDto.of(success, responseCode, customMessage);

        assertEquals(success, responseDto.getSuccess());
        assertEquals(responseCode.getCode(), responseDto.getCode());
        assertEquals(customMessage, responseDto.getMessage());
    }
}
