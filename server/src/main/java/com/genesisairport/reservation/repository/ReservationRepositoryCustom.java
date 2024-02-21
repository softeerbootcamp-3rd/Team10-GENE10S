package com.genesisairport.reservation.repository;

import com.genesisairport.reservation.request.AdminRequest;
import com.genesisairport.reservation.response.AdminResponse;

import java.util.List;

public interface ReservationRepositoryCustom {

    List<AdminResponse.ReservationDetail> findReservations(
            String shopName, String startPickUpDateTime, String endPickUpDateTime, String startReturnDateTime,
            String endReturnDateTime, String customerName, String sellName, String stage,
            String sortColumn, String sortDirection, String pageSize, String pageNumber
    );
}
