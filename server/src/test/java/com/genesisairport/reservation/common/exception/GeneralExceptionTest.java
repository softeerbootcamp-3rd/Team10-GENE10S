package com.genesisairport.reservation.common.exception;

import static org.junit.jupiter.api.Assertions.*;

import com.genesisairport.reservation.common.enums.ResponseCode;
import org.junit.jupiter.api.Test;

public class GeneralExceptionTest {

    @Test
    public void testConstructorAndGetters() {
        ResponseCode responseCode = ResponseCode.BAD_REQUEST;
        String errorMessage = "Error message";
        GeneralException exception = new GeneralException(responseCode, errorMessage);

        assertEquals(responseCode, exception.getResponseCode());
        assertEquals(errorMessage, exception.getMessage());
    }
}
