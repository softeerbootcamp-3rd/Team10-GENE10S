package com.genesisairport.reservation.common.util;

import com.genesisairport.reservation.common.enums.RedisKey;
import com.genesisairport.reservation.entity.RepairShop;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class ConcurrencyManager {
    private final RedisUtil redisUtil;

    public Long increase(RedisKey key, String id) {
        String redisKey = key.getKey() + ":" + id;
        Object value = redisUtil.increaseValue(redisKey);
        if (value == null) {
            return 0L;
        }
        return Long.valueOf(value.toString());
    }

    public String createDateTimeKey(RepairShop repairShop, LocalDateTime localDateTime) {
        return repairShop.getId() + ":" + localDateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
    }

    public void decrease(RedisKey key, String id) {
        String redisKey = key.getKey() + ":" + id;
        redisUtil.decreaseValue(redisKey);
    }

    public Optional<Integer> get(RedisKey redisKey, String fromDateTimeKey) {
        String key = redisKey.getKey() + ":" + fromDateTimeKey;
        Object value = redisUtil.getValues(key);
        if (value == null) {
            return Optional.empty();
        }
        return Optional.of(Integer.valueOf(value.toString()));
    }

    public void setNx(RedisKey redisKey, String fromDateTimeKey, Integer existCount) {
        String key = redisKey.getKey() + ":" + fromDateTimeKey;
        redisUtil.setNx(key, existCount);
    }
}
