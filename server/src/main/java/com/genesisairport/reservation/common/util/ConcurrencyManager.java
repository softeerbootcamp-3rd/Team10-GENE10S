package com.genesisairport.reservation.common.util;

import com.genesisairport.reservation.common.enums.RedisKey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConcurrencyManager {

    private final RedisUtil redisUtil;
    public Integer increase(RedisKey key, String id) {
        String redisKey = key.getKey() + id;
        Object value = redisUtil.increaseValue(redisKey);
        return (Integer) value;
    }

    public void decrease(RedisKey key, String id) {
        String redisKey = key.getKey() + id;
        redisUtil.decreaseValue(redisKey);
    }
}
