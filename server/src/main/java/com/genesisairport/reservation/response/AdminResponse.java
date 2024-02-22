package com.genesisairport.reservation.response;

import lombok.AllArgsConstructor;
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
    public static class AccountDetail {
        private long id;
        private String adminId;
        private String adminName;
        private String phoneNumber;
        private String createDateTime;
    }
      
    @AllArgsConstructor
    public static class AvailableTime {
        private String date;
        private List<String> availableTime;
    }

    @Getter
    @Builder
    public static class UploadImage {
        private Long imageId;
        private String imageUrl;
    }
}
