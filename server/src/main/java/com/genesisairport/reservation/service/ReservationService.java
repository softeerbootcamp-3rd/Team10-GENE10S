package com.genesisairport.reservation.service;

import com.genesisairport.reservation.respository.CouponRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationService {

    private final CouponRepository couponRepository;

    public Boolean validateCoupon(String serialNumber) {
        return couponRepository.existsBySerialNumber(serialNumber);
    }
}
