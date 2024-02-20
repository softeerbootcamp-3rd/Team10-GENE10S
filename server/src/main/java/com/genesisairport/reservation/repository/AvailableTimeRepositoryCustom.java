package com.genesisairport.reservation.repository;

import com.genesisairport.reservation.response.AdminResponse;

import java.time.LocalDate;
import java.util.List;

public interface AvailableTimeRepositoryCustom {
    List<AdminResponse.AvailableTime> findAvailableTimeList(String shopName, LocalDate dateFrom, LocalDate dateTo);
}
