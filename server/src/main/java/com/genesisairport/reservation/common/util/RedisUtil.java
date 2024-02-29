package com.genesisairport.reservation.common.util;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class RedisUtil {
    private static final String SESSION_PREFIX = "spring:session:sessions:";
    private final RedisTemplate<String, Object> redisTemplate;

    public RedisUtil(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setValues(String key, Object data) {
        ValueOperations<String, Object> values = redisTemplate.opsForValue();
        values.set(key, data, Duration.ofHours(1));
    }

    public Object getValues(String key) {
        ValueOperations<String, Object> values = redisTemplate.opsForValue();
        return values.get(key);
    }

    public void deleteValues(String key) {
        redisTemplate.delete(key);
    }

    public Boolean isValid(String sessionKey) {
        return redisTemplate.hasKey(SESSION_PREFIX + sessionKey);
    }

    public Object increaseValue(String key) {
        ValueOperations<String, Object> values = redisTemplate.opsForValue();
        return values.increment(key);

//        return values.get(key);
    }

    public void decreaseValue(String key) {
        ValueOperations<String, Object> values = redisTemplate.opsForValue();
        values.decrement(key);
    }

    public void setNx(String key, Integer value) {
        ValueOperations<String, Object> values = redisTemplate.opsForValue();
        values.setIfAbsent(key, value);
    }
}
