package com.genesisairport.reservation.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CarImageTest {

    @Test
    void createCarImage_shouldCreateInstanceWithCorrectValues() {
        Long id = 1L;
        String sellName = "Genesis G80";
        String imageUrl = "https://example.com/image.jpg";
        LocalDateTime createDatetime = LocalDateTime.now();
        LocalDateTime updateDatetime = LocalDateTime.now();

        CarImage carImage = new CarImage();
        carImage.setId(id);
        carImage.setSellName(sellName);
        carImage.setImageUrl(imageUrl);
        carImage.setCreateDatetime(createDatetime);
        carImage.setUpdateDatetime(updateDatetime);

        assertNotNull(carImage);
        assertEquals(id, carImage.getId());
        assertEquals(sellName, carImage.getSellName());
        assertEquals(imageUrl, carImage.getImageUrl());
        assertEquals(createDatetime, carImage.getCreateDatetime());
        assertEquals(updateDatetime, carImage.getUpdateDatetime());
    }
}
