package com.genesisairport.reservation.request;

import com.genesisairport.reservation.common.enums.ProgressStage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StageInfo {
        private Long reservationId;
        private ProgressStage progress;
        private String detail;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CommentInfo {
        private Long reservationId;
        private String comment;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Login {
        private String adminId;
        private String adminPwd;
    }

    @Getter
    @Builder
    public static class AccountDetail {
        private String adminId;
        private String adminName;
        private String phoneNumber;
        private String sortColumn;
        private String sortDirection;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReservationTime {
        private String shopName;
        private String businessDay;
    }
}
