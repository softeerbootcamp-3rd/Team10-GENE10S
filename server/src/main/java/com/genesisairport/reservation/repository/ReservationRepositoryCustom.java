package com.genesisairport.reservation.repository;

import com.genesisairport.reservation.request.AdminRequest;
import com.genesisairport.reservation.response.AdminResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepositoryCustom {

    Page<AdminResponse.ReservationDetail> findReservations(
            AdminRequest.ReservationDetail reservationDetail, Pageable pageable,
            Long count, LocalDateTime startDateTime, LocalDateTime endDateTime
    );

    LocalDateTime findNthFastestByDepartureTime(long n);
    LocalDateTime findNthSlowestByDepartureTime(long n);
    LocalDateTime findNthFastestByArrivalTime(long n);
    LocalDateTime findNthSlowestByArrivalTime(long n);
}
