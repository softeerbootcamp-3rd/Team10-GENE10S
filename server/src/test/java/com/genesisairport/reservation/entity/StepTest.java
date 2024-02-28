package com.genesisairport.reservation.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class StepTest {

    @Test
    void createStep_shouldCreateInstanceWithCorrectValues() {
        // Given
        Long id = 1L;
        String stage = "Inspection";
        LocalDateTime date = LocalDateTime.now();
        String detail = "Performed inspection of vehicle.";
        LocalDateTime createDatetime = LocalDateTime.now();
        LocalDateTime updateDatetime = LocalDateTime.now();
        Reservation reservation = new Reservation();

        // When
        Step step = Step.builder()
                .id(id)
                .stage(stage)
                .date(date)
                .detail(detail)
                .createDatetime(createDatetime)
                .updateDatetime(updateDatetime)
                .reservation(reservation)
                .build();

        // Then
        assertNotNull(step);
        assertEquals(id, step.getId());
        assertEquals(stage, step.getStage());
        assertEquals(date, step.getDate());
        assertEquals(detail, step.getDetail());
        assertEquals(createDatetime, step.getCreateDatetime());
        assertEquals(updateDatetime, step.getUpdateDatetime());
        assertEquals(reservation, step.getReservation());
    }
}
