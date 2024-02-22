package com.genesisairport.reservation.common.util;

import com.genesisairport.reservation.common.enums.RedisKey;
import com.genesisairport.reservation.entity.RepairShop;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ConcurrencyManager {
    private final RedisUtil redisUtil;

    public Integer increase(RedisKey key, String id) {
        String redisKey = key.getKey() + id;
        Object value = redisUtil.increaseValue(redisKey);
        return (Integer) value;
    }

    public String createDateKey(RepairShop repairShop, LocalDateTime localDateTime) {
        String[] split = CommonDateFormat.localDatetime(localDateTime).split(" ");
        return repairShop.getShopName() + ":" + split[0] + "T" + split[1];
    }

    public void decrease(RedisKey key, String id) {
        String redisKey = key.getKey() + id;
        redisUtil.decreaseValue(redisKey);
    }

    public Optional<Integer> get(RedisKey redisKey, String fromDateTimeKey) {
        String key = redisKey.getKey() + ":" + fromDateTimeKey;
        Integer values = (Integer) redisUtil.getValues(key);
        return Optional.ofNullable(values);
    }

    public void setNx(RedisKey redisKey, String fromDateTimeKey, Integer existCount) {
        String key = redisKey.getKey() + ":" + fromDateTimeKey;
        redisUtil.setNx(key, existCount);
    }
}
