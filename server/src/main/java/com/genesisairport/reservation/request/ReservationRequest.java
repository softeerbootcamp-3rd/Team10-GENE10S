package com.genesisairport.reservation.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ReservationRequest {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReservationPost {
        private String couponSerialNumber;
        private String shopName;
        private String departureTime;
        private String arrivalTime;
        private String contactNumber;
        private String carSellName;
        private String carPlateNumber;
        private Object serviceType;
        private String customerRequest;
    }
}
