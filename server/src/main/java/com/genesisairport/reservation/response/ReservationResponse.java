package com.genesisairport.reservation.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.sql.Time;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

public class ReservationResponse {

    public interface CarInfo {
        String getSellName();
        String getPlateNumber();
    }

    @Getter
    @Builder
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
    public static class ReservationPostResponse {
        private boolean reservationStatus;
        private String repairShopAddress;
        private String customerName;
    }

    @Getter
    @Builder
    public static class ReservationListAbstract{
        private long reservationId;
        private String departureTime;
        private String arrivalTime;
        private String progressStage;
        private String carSellName;
        private String repairShop;

        // 기본 생성자를 추가합니다.
        public ReservationListAbstract() {
        }

        // 이 생성자를 사용하여 객체를 생성할 수 있도록 합니다.
        public ReservationListAbstract(long reservationId, String departureTime, String arrivalTime, String progressStage, String carSellName, String repairShop) {
            this.reservationId = reservationId;
            this.departureTime = departureTime;
            this.arrivalTime = arrivalTime;
            this.progressStage = progressStage;
            this.carSellName = carSellName;
            this.repairShop = repairShop;
        }
    }
}
