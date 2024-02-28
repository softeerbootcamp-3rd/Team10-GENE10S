package com.genesisairport.reservation.common.util;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.time.Duration;

public class RedisUtilTest {

    private RedisTemplate<String, Object> redisTemplate;
    private ValueOperations<String, Object> valueOperations;
    private RedisUtil redisUtil;

    @BeforeEach
    public void setUp() {
        redisTemplate = mock(RedisTemplate.class);
        valueOperations = mock(ValueOperations.class);
        redisUtil = new RedisUtil(redisTemplate);
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
    }

    @Test
    public void testSetValues() {
        String key = "test_key";
        Object data = "test_data";

        redisUtil.setValues(key, data);

        verify(valueOperations).set(eq(key), eq(data), any(Duration.class));
    }

    @Test
    public void testGetValues() {
        String key = "test_key";
        Object expectedData = "test_data";

        when(valueOperations.get(key)).thenReturn(expectedData);

        Object result = redisUtil.getValues(key);

        assertEquals(expectedData, result);
    }

    @Test
    public void testDeleteValues() {
        String key = "test_key";

        redisUtil.deleteValues(key);

        verify(redisTemplate).delete(key);
    }

    @Test
    public void testIsValid() {
        String sessionKey = "test_session_key";

        when(redisTemplate.hasKey(anyString())).thenReturn(true);

        boolean result = redisUtil.isValid(sessionKey);

        assertTrue(result);
    }

    @Test
    public void testIncreaseValue() {
        String key = "test_key";
        Object initialValue = 5;

        when(valueOperations.get(key)).thenReturn(initialValue);
        when(valueOperations.increment(key)).thenReturn(6L);

        Object result = redisUtil.increaseValue(key);

        assertEquals(6L, result);
    }

    @Test
    public void testDecreaseValue() {
        String key = "test_key";
        Object initialValue = 5;

        when(valueOperations.get(key)).thenReturn(initialValue);

        redisUtil.decreaseValue(key);

        verify(valueOperations).decrement(key);
    }

    @Test
    public void testSetNx() {
        String key = "test_key";
        Integer value = 5;

        redisUtil.setNx(key, value);

        verify(valueOperations).setIfAbsent(key, value);
    }
}
