package com.genesisairport.reservation.common.model;

import static org.junit.jupiter.api.Assertions.*;

import com.genesisairport.reservation.common.enums.ResponseCode;
import org.junit.jupiter.api.Test;

public class DataResponseDtoTest {

    @Test
    public void testDataResponseDtoWithDefaultMessage() {
        String testData = "Test Data";
        DataResponseDto<String> response = DataResponseDto.of(testData);

        assertTrue(response.getSuccess());
        assertEquals(ResponseCode.OK.getCode(), response.getCode());
        assertEquals(ResponseCode.OK.getMessage(), response.getMessage());
        assertEquals(testData, response.getData());
    }

    @Test
    public void testDataResponseDtoWithCustomMessage() {
        String testData = "Test Data";
        String customMessage = "Custom Message";
        DataResponseDto<String> response = DataResponseDto.of(testData, customMessage);

        assertTrue(response.getSuccess());
        assertEquals(ResponseCode.OK.getCode(), response.getCode());
        assertEquals(customMessage, response.getMessage());
        assertEquals(testData, response.getData());
    }

    @Test
    public void testEmptyDataResponseDto() {
        DataResponseDto<String> response = DataResponseDto.empty();

        assertTrue(response.getSuccess());
        assertEquals(ResponseCode.OK.getCode(), response.getCode());
        assertEquals(ResponseCode.OK.getMessage(), response.getMessage());
        assertNull(response.getData());
    }
}
