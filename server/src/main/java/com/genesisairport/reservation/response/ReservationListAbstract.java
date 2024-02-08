package com.genesisairport.reservation.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ReservationListAbstract{
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
