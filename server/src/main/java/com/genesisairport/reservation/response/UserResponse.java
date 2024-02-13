package com.genesisairport.reservation.response;

import com.genesisairport.reservation.entity.Car;
import lombok.*;

import java.util.List;

public class UserResponse {

    @Getter
    @Builder
    public static class Login {
        public String sid;
    }

    @Getter
    @Setter
    @Builder
    public static class UserInfo {
        private Long userId;
        private String name;
        private String email;
        private String birthdate;
        private String phoneNumber;
        private List<CarInfo> carList;

        @Getter
        @Setter
        @Builder
        public static class CarInfo {
            private Long carId;
            private String vin;
            private String sellName;
            private String plateNumber;
            private String imageUrl;
        }
    }

    @Getter
    @Setter
    @Builder
    public static class Profile {
        private String name;
        private String email;
        private String carSellName;
        private String imageUrl;
    }
}
