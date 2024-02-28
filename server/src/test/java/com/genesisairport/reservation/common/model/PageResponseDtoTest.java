package com.genesisairport.reservation.common.model;

import static org.junit.jupiter.api.Assertions.*;

import com.genesisairport.reservation.common.enums.ResponseCode;
import org.junit.jupiter.api.Test;

public class PageResponseDtoTest {

    @Test
    public void testPageResponseDtoWithDefaultMessage() {
        String testData = "Test Data";
        PageInfo pageInfo = new PageInfo(1, 10, 100, 10);
        PageResponseDto<String> response = PageResponseDto.of(testData, pageInfo);

        assertTrue(response.getSuccess());
        assertEquals(ResponseCode.OK.getCode(), response.getCode());
        assertEquals(ResponseCode.OK.getMessage(), response.getMessage());
        assertEquals(testData, response.getData());
        assertEquals(pageInfo, response.getPageInfo());
    }

    @Test
    public void testPageResponseDtoWithCustomMessage() {
        String testData = "Test Data";
        String customMessage = "Custom Message";
        PageInfo pageInfo = new PageInfo(1, 10, 100, 10);
        PageResponseDto<String> response = PageResponseDto.of(testData, pageInfo, customMessage);

        assertTrue(response.getSuccess());
        assertEquals(ResponseCode.OK.getCode(), response.getCode());
        assertEquals(customMessage, response.getMessage());
        assertEquals(testData, response.getData());
        assertEquals(pageInfo, response.getPageInfo());
    }

    @Test
    public void testEmptyPageResponseDto() {
        PageResponseDto<String> response = PageResponseDto.empty();

        assertTrue(response.getSuccess());
        assertEquals(ResponseCode.OK.getCode(), response.getCode());
        assertEquals(ResponseCode.OK.getMessage(), response.getMessage());
        assertNull(response.getData());
        assertNull(response.getPageInfo());
    }
}
