package com.genesisairport.reservation.common.enums;

import static org.junit.jupiter.api.Assertions.*;

import com.genesisairport.reservation.common.exception.GeneralException;
import org.junit.jupiter.api.Test;

public class ProgressStageTest {

    @Test
    public void testOfValidProgress() {
        ProgressStage stage = ProgressStage.of("정비중");
        assertEquals(ProgressStage.MAINTENANCE, stage);
    }

    @Test
    public void testOfInvalidProgress() {
        ProgressStage stage = ProgressStage.of("없는단계");
        assertNull(stage);
    }

    @Test
    public void testFromNameValidName() {
        ProgressStage stage = ProgressStage.fromName("보관중");
        assertEquals(ProgressStage.PARKING, stage);
    }

    @Test
    public void testFromNameInvalidName() {
        assertThrows(GeneralException.class, () -> {
            ProgressStage.fromName("잘못된 단계");
        });
    }
}