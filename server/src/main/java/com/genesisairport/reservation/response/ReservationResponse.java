package com.genesisairport.reservation.response;

import lombok.Builder;
import lombok.Getter;

public class ReservationResponse {

<<<<<<< HEAD
    public interface CarInfo {
        String getSellName();
        String getPlateNumber();
    }

=======
    @Getter
    @Builder
    public static class CarInfo {
        private String sellName;
        private String plateNumber;
    }

    @Getter
    @Builder
    public static class CouponValid {
        public boolean valid;
    }
>>>>>>> develop
}
