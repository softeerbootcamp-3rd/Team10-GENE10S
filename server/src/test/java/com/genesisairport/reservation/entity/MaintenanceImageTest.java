package com.genesisairport.reservation.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class MaintenanceImageTest {

    @Test
    void createMaintenanceImage_shouldCreateInstanceWithCorrectValues() {
        Long id = 1L;
        Integer status = 1;
        String imageUrl = "http://example.com/image.jpg";
        String objectKey = "object_key";
        LocalDateTime createDatetime = LocalDateTime.now();
        LocalDateTime updateDatetime = LocalDateTime.now();
        Reservation reservation = new Reservation();

        MaintenanceImage maintenanceImage = MaintenanceImage.builder()
                .id(id)
                .status(status)
                .imageUrl(imageUrl)
                .objectKey(objectKey)
                .createDatetime(createDatetime)
                .updateDatetime(updateDatetime)
                .reservation(reservation)
                .build();

        assertNotNull(maintenanceImage);
        assertEquals(id, maintenanceImage.getId());
        assertEquals(status, maintenanceImage.getStatus());
        assertEquals(imageUrl, maintenanceImage.getImageUrl());
        assertEquals(objectKey, maintenanceImage.getObjectKey());
        assertEquals(createDatetime, maintenanceImage.getCreateDatetime());
        assertEquals(updateDatetime, maintenanceImage.getUpdateDatetime());
        assertEquals(reservation, maintenanceImage.getReservation());
    }
}
