package com.genesisairport.reservation.repository;

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

    Reservation findReservationById(long reservationId);

    @Query("SELECT r FROM Reservation r" +
            " WHERE r.customer.id = :customerId " +
            "ORDER BY r.progressStage desc, r.departureTime")
    List<Reservation> findReservationsByCustomerId(Long customerId);

    @Query("SELECT r FROM Reservation r " +
            "WHERE (r.departureTime = :businessTime OR r.arrivalTime = :businessTime) " +
            "AND r.repairShop.id = :repairShopId " +
            "AND r.progressStage NOT IN ('완료', '취소')")
    Optional<Reservation> findReservationBy(Long repairShopId, LocalDateTime businessTime);

    @Query("SELECT r FROM Reservation r " +
            "WHERE (r.departureTime = :businessTime OR r.arrivalTime = :businessTime) " +
            "AND r.repairShop.id = :repairShopId " +
            "AND r.progressStage NOT IN ('완료', '취소')")
    List<Reservation> findReservationsBy(Long repairShopId, LocalDateTime businessTime);
}
