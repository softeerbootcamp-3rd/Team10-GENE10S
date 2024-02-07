package com.genesisairport.reservation.response;

import lombok.Builder;
import lombok.Getter;

public class ReservationResponse {

    @Getter
    @Builder
    public static class CarInfo {
        private String sellName;
        private String plateNumber;
    }

}
