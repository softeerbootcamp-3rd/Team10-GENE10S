package com.genesisairport.reservation.respository;

import com.genesisairport.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query("SELECT r.id FROM Reservation r WHERE r.customer.id = :customerId ORDER BY r.createDateTime LIMIT 1")
    Long findReservationByCustomerId(Long customerId);

    List<Reservation> findReservationsByCustomerId(Long customerId);
}
