package com.genesisairport.reservation.respository;

import com.genesisairport.reservation.response.ReservationResponse;

import java.util.List;

public interface RepairShopRepositoryCustom {

    public List<ReservationResponse.AvailableDate> findAvailableTimes(String shopName);

}
