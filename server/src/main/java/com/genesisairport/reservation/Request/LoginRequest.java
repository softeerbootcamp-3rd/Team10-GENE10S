package com.genesisairport.reservation.Request;

import lombok.Builder;
import lombok.Getter;

public class LoginRequest {
    @Getter
    @Builder
    public static class Login {
        private String grantType;
        private String code;
        private String redirectUri;
        private String clientId;
        private String clientSecret;
    }
}
