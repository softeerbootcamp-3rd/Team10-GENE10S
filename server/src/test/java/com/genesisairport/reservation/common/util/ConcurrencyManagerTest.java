package com.genesisairport.reservation.common.util;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.genesisairport.reservation.common.enums.RedisKey;
import com.genesisairport.reservation.common.util.RedisUtil;
import com.genesisairport.reservation.entity.RepairShop;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class ConcurrencyManagerTest {

    private RedisUtil redisUtil;
    private ConcurrencyManager concurrencyManager;

    @BeforeEach
    public void setUp() {
        redisUtil = mock(RedisUtil.class);
        concurrencyManager = new ConcurrencyManager(redisUtil);
    }

    @Test
    public void testIncrease() {
        RedisKey redisKey = RedisKey.RESERVATION;
        String id = "123";
        String redisKeyName = redisKey.getKey() + ":" + id;

        when(redisUtil.increaseValue(redisKeyName)).thenReturn(1L);

        Long result = concurrencyManager.increase(redisKey, id);
        assertEquals(1L, result);
    }

    @Test
    public void testCreateDateTimeKey() {
        RepairShop repairShop = new RepairShop();
        repairShop.setId(456L);
        LocalDateTime localDateTime = LocalDateTime.of(2022, 2, 28, 10, 15, 30);

        String expectedKey = "456:202202281015";
        String resultKey = concurrencyManager.createDateTimeKey(repairShop, localDateTime);

        assertEquals(expectedKey, resultKey);
    }

    @Test
    public void testDecrease() {
        RedisKey redisKey = RedisKey.LOGIN_USER;
        String id = "789";
        String redisKeyName = redisKey.getKey() + ":" + id;

        concurrencyManager.decrease(redisKey, id);

        verify(redisUtil).decreaseValue(redisKeyName);
    }

    @Test
    public void testGet() {
        RedisKey redisKey = RedisKey.RESERVATION;
        String fromDateTimeKey = "202202281015";
        String redisKeyName = redisKey.getKey() + ":" + fromDateTimeKey;

        when(redisUtil.getValues(redisKeyName)).thenReturn("2");

        Optional<Integer> result = concurrencyManager.get(redisKey, fromDateTimeKey);
        assertTrue(result.isPresent());
        assertEquals(2, result.get());
    }

    @Test
    public void testSetNx() {
        RedisKey redisKey = RedisKey.RESERVATION;
        String fromDateTimeKey = "202202281015";
        Integer existCount = 3;
        String redisKeyName = redisKey.getKey() + ":" + fromDateTimeKey;

        concurrencyManager.setNx(redisKey, fromDateTimeKey, existCount);

        verify(redisUtil).setNx(redisKeyName, existCount);
    }
}
