package com.genesisairport.reservation.Response;

import lombok.Builder;
import lombok.Getter;

public class CouponValidResponse {

    @Getter
    @Builder
    public static class Dto {
        public boolean valid;
    }
}
