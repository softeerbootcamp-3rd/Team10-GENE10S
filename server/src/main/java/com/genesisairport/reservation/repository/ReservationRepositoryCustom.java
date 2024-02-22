package com.genesisairport.reservation.repository;

import com.genesisairport.reservation.request.AdminRequest;
import com.genesisairport.reservation.response.AdminResponse;

import java.util.List;

public interface ReservationRepositoryCustom {

    List<AdminResponse.ReservationDetail> findReservations(
            AdminRequest.ReservationDetail reservationDetail, String pageSize, String pageNumber
    );
}
