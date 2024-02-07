package com.genesisairport.reservation.Response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ReservationDateResponse {

    private String date;
    private List<TimeSlot> timeSlots;

    @Getter
    @Builder
    public static class TimeSlot {
        private int time;
        private boolean available;
    }
}
