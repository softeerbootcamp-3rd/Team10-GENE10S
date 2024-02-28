package com.genesisairport.reservation.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CouponTest {

    @Test
    void createCoupon_shouldCreateInstanceWithCorrectValues() {
        Long id = 1L;
        String serialNumber = "ABC123";
        LocalDate expiredDate = LocalDate.of(2024, 12, 31);
        Boolean isUsed = false;
        LocalDateTime createDatetime = LocalDateTime.now();
        LocalDateTime updateDatetime = LocalDateTime.now();

        Coupon coupon = Coupon.builder()
                .id(id)
                .serialNumber(serialNumber)
                .expiredDate(expiredDate)
                .isUsed(isUsed)
                .createDatetime(createDatetime)
                .updateDatetime(updateDatetime)
                .build();

        assertNotNull(coupon);
        assertEquals(id, coupon.getId());
        assertEquals(serialNumber, coupon.getSerialNumber());
        assertEquals(expiredDate, coupon.getExpiredDate());
        assertEquals(isUsed, coupon.getIsUsed());
        assertEquals(createDatetime, coupon.getCreateDatetime());
        assertEquals(updateDatetime, coupon.getUpdateDatetime());
    }
}
