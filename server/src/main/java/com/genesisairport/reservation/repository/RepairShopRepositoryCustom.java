package com.genesisairport.reservation.repository;

import com.genesisairport.reservation.response.ReservationResponse;

import java.time.LocalDate;
import java.util.List;

public interface RepairShopRepositoryCustom {

    ReservationResponse.DateInfo findAvailableDates(String shopName);

    List<ReservationResponse.TimeInfo> findAvailableTimes(String shopName, LocalDate date);

}
