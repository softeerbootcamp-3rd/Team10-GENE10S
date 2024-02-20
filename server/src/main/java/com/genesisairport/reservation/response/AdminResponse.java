package com.genesisairport.reservation.response;

import lombok.Builder;
import lombok.Getter;

public class AdminResponse {

    @Getter
    @Builder
    public static class ReservationDetail {
        private long reservationId;
        private String shopName;
        private String customerName;
        private String sellName;
        private String departureTime;
        private String arrivalTime;
        private String stage;
    }
}
