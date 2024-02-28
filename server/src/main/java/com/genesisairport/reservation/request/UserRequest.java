package com.genesisairport.reservation.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserRequest {
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Login {
        private String grantType;
        private String code;
        private String redirectUri;
        private String clientId;
        private String clientSecret;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserInfo {
        private String name;
        private String birthdate;
        private String phoneNumber;
    }
}
