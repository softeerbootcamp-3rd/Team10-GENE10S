package com.genesisairport.reservation.respository;

import com.genesisairport.reservation.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {
    Coupon findCouponBySerialNumber(String serialNumber);
}
