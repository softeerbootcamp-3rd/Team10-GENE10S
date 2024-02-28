package com.genesisairport.reservation.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

public class ReservationResponse {

    public interface CarInfo {
        String getSellName();
        String getPlateNumber();
        String getImageUrl();
        String getCarId();
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class CouponValid {
        public boolean valid;
    }

    @Getter
    @Builder
    public static class DateInfo {
        private List<String> availableDates;
    }

    @Getter
    @Builder
    public static class TimeList {
        private List<TimeInfo> timeSlots;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class TimeInfo {
        private Integer time;
        private Boolean available;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReservationPostResponse {
        private boolean reservationStatus;
        private String repairShopAddress;
        private String customerName;
    }

    @Getter
    @Builder
    public static class ReservationInfoAbstract{
        private long reservationId;
        private String departureTime;
        private String arrivalTime;
        private String progressStage;
        private String carSellName;
        private String repairShop;
        private String imageUrl;

        // 기본 생성자를 추가합니다.
        public ReservationInfoAbstract() {
        }

        // 이 생성자를 사용하여 객체를 생성할 수 있도록 합니다.
        public ReservationInfoAbstract(long reservationId, String departureTime, String arrivalTime, String progressStage, String carSellName, String repairShop, String imageUrl) {
            this.reservationId = reservationId;
            this.departureTime = departureTime;
            this.arrivalTime = arrivalTime;
            this.progressStage = progressStage;
            this.carSellName = carSellName;
            this.repairShop = repairShop;
            this.imageUrl = imageUrl;
        }
    }

    @Getter
    @Builder
    public static class ReservationDetail {
        private long reservationId;
        private long customerId;
        private String customerName;
        private String couponSerialNumber;
        private String repairShop;
        private String repairShopAddress;
        private String from;
        private String to;
        private String contactNumber;
        private String carSellName;
        private String carPlateNumber;
        private Map<String, Boolean> serviceType;
        private String customerRequest;
        private List<ProgressStage> progressStage;
        private String checkupResult;
        private List<ImageContainer> beforeImages;
        private List<ImageContainer> afterImages;
        private String inspectionResult;
        private String managerPhoneNumber;
        private String imageUrl;

        @Getter
        @Builder
        public static class ImageContainer {
            private long id;
            private String url;
        }

        @Getter
        @Builder
        public static class ProgressStage {
            private Long id;
            private String step;
            private String date;
            private String detail;
        }
    }

    @Getter
    @Builder
    public static class ProgressId {
        private Long stepId;
    }

    @Getter
    @Builder
    public static class hasReservation {
        private boolean hasReservation;
    }
}
