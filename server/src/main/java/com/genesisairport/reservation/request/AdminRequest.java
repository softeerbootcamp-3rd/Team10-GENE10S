package com.genesisairport.reservation.request;

import lombok.Builder;
import lombok.Getter;

public class AdminRequest {
    @Getter
    @Builder
    public static class ReservationDetail {
        private String shopName;
        private String startPickupDateTime;
        private String endPickupDateTime;
        private String startReturnDateTime;
        private String endReturnDateTime;
        private String customerName;
        private String sellName;
        private String stage;
        private String sortColumn;
        private String sortDirection;
    }
}
