package com.genesisairport.reservation.entity;

import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AvailableTimeTest {

    @Test
    void createAvailableTime_shouldCreateInstanceWithCorrectValues() {
        Long id = 1L;
        Date reservationDate = Date.valueOf("2024-02-27");
        Time reservationTime = Time.valueOf("10:00:00");
        Integer reservationCount = 5;
        LocalDateTime createDatetime = LocalDateTime.now();
        LocalDateTime updateDatetime = LocalDateTime.now();

        AvailableTime availableTime = AvailableTime.builder()
                .id(id)
                .reservationDate(reservationDate)
                .reservationTime(reservationTime)
                .reservationCount(reservationCount)
                .createDatetime(createDatetime)
                .updateDatetime(updateDatetime)
                .build();

        assertNotNull(availableTime);
        assertEquals(id, availableTime.getId());
        assertEquals(reservationDate, availableTime.getReservationDate());
        assertEquals(reservationTime, availableTime.getReservationTime());
        assertEquals(reservationCount, availableTime.getReservationCount());
        assertEquals(createDatetime, availableTime.getCreateDatetime());
        assertEquals(updateDatetime, availableTime.getUpdateDatetime());
    }
}
