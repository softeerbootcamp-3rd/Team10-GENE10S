package com.genesisairport.reservation.response;

import lombok.Builder;
import lombok.Getter;

public class ReservationResponse {

    public interface CarInfo {
        String getSellName();
        String getPlateNumber();
    }

}
