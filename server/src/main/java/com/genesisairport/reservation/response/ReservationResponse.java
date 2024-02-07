package com.genesisairport.reservation.response;

import lombok.Builder;
import lombok.Getter;

import java.sql.Time;
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
    public static class AvailableDate {
        private String date;
        private List<TimeInfo> timeSlots;
    }

    @Getter
    @Builder
    public static class TimeInfo {
        private Integer time;
        private Boolean available;
    }

}
