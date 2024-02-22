package com.genesisairport.reservation.repository;

import com.genesisairport.reservation.entity.RepairShop;
import com.genesisairport.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long>, ReservationRepositoryCustom {
    @Query("SELECT r FROM Reservation r WHERE r.customer.id = :customerId  ORDER BY r.createDateTime LIMIT 1")
    Reservation findReservationByCustomerId(@Param("customerId") Long customerId);

    Optional<Reservation> findById(long reservationId);

    @Query("SELECT r FROM Reservation r" +
            " WHERE r.customer.id = :customerId " +
            "ORDER BY r.progressStage desc, r.departureTime")
    List<Reservation> findReservationsByCustomerId(Long customerId);
}
