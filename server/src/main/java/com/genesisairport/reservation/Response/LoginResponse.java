package com.genesisairport.reservation.Response;

import lombok.Builder;
import lombok.Getter;

public class LoginResponse {

    @Getter
    @Builder
    public static class Login {
        public String sid;
    }
}