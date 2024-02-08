package com.genesisairport.reservation.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ReservationPostResponse {
    private boolean reservationStatus;
    private String repairShopAddress;
    private String customerName;
}
