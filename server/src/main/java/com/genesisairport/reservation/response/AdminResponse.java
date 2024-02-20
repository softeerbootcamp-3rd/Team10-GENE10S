package com.genesisairport.reservation.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class AdminResponse {

    @Getter
    @Builder
    public static class ReservationDetail {
        private long reservationId;
        private String shopName;
        private String customerName;
        private String sellName;
        private String departureTime;
        private String arrivalTime;
        private String stage;
    }

    @Getter
    @Builder
    public static class AccountDetailForPage {
        private List<AdminResponse.AccountDetail> accountDetailList;
        private PageInfo pageInfo;
    }

    @Getter
    @Builder
    public static class AccountDetail {
        private long id;
        private String adminId;
        private String adminName;
        private String phoneNumber;
        private String createDateTime;
    }

    @Getter
    @Builder
    public static class PageInfo {
        private long page;
        private long size;
        private long totalElements;
        private long totalPages;
    }
}
