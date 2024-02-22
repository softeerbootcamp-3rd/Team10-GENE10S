package com.genesisairport.reservation.repository;

import com.genesisairport.reservation.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {
    Optional<Coupon> findCouponBySerialNumber(String serialNumber);
}
