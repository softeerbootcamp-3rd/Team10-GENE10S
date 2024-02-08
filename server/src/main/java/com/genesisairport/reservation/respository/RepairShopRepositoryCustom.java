package com.genesisairport.reservation.respository;

import com.genesisairport.reservation.response.ReservationResponse;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface RepairShopRepositoryCustom {

    ReservationResponse.DateInfo findAvailableDates(String shopName);

    List<ReservationResponse.TimeInfo> findAvailableTimes(String shopName, LocalDate date);

}
