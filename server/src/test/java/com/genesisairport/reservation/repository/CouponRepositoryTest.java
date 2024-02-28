package com.genesisairport.reservation.repository;

import com.genesisairport.reservation.TestJPAQueryFactoryConfiguration;
import com.genesisairport.reservation.entity.Coupon;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@DataJpaTest
@Import(TestJPAQueryFactoryConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class CouponRepositoryTest {

    @Autowired
    private CouponRepository couponRepository;

    @Test
    void findCouponBySerialNumber_WhenCouponExists_ReturnCoupon() {
        Coupon coupon = new Coupon();
        coupon.setSerialNumber("ABC123");
        couponRepository.save(coupon);

        Optional<Coupon> foundCouponOptional = couponRepository.findCouponBySerialNumber("ABC123");

        Assertions.assertTrue(foundCouponOptional.isPresent());
        Coupon foundCoupon = foundCouponOptional.get();
        Assertions.assertEquals("ABC123", foundCoupon.getSerialNumber());
    }

    @Test
    void findCouponBySerialNumber_WhenCouponDoesNotExist_ReturnEmptyOptional() {
        Optional<Coupon> foundCouponOptional = couponRepository.findCouponBySerialNumber("XYZ789");

        Assertions.assertTrue(foundCouponOptional.isEmpty());
    }
}
