package com.genesisairport.reservation.respository;

import com.genesisairport.reservation.response.ReservationResponse;

public interface RepairShopRepositoryCustom {

    ReservationResponse.DateInfo findAvailableTimes(String shopName);

}
