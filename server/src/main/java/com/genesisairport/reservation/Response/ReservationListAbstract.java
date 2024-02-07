package com.genesisairport.reservation.Response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ReservationListAbstract {
    private List<ReservationAbstract> reservationList;

    @Getter
    @Builder
    public static class ReservationAbstract{
        private long reservationId;
        private String from;
        private String to;
        private String progressStage;
        private String carSellName;
        private String carPlateNumber;
        private String repairShop;
        private String repairShopAddress;
    }
}
