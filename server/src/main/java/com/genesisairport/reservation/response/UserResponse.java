package com.genesisairport.reservation.response;

import lombok.*;

public class UserResponse {

    @Getter
    @Builder
    public static class Login {
        public String sid;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Profile {
        private String name;
        private String email;
        private String carSellName;
        private String imageUrl;
    }
}
