package com.genesisairport.reservation.common.enums;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class RedisKeyTest {

    @Test
    public void testGetKey() {
        RedisKey key = RedisKey.RESERVATION;
        assertEquals("reservation", key.getKey());
    }
}
