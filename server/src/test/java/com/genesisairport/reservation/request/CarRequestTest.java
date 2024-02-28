package com.genesisairport.reservation.request;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CarRequestTest {

    @Test
    void createCarPost_shouldCreateInstanceWithCorrectValues() {
        String sellName = "Genesis G80";
        String plateNumber = "123가 1234";

        CarRequest.CarPost carPost = CarRequest.CarPost.builder()
                .sellName(sellName)
                .plateNumber(plateNumber)
                .build();

        assertNotNull(carPost);
        assertEquals(sellName, carPost.getSellName());
        assertEquals(plateNumber, carPost.getPlateNumber());
    }
}
