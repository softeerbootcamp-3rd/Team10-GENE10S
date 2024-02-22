package com.genesisairport.reservation.repository;

import com.genesisairport.reservation.entity.AvailableTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.sql.Time;
import java.util.Optional;

public interface AvailableTimeRepository extends JpaRepository<AvailableTime, Long>, AvailableTimeRepositoryCustom {
    @Query("SELECT a FROM AvailableTime a WHERE a.repairShop.id = :repairShopId AND a.reservationDate = :reservationDate AND a.reservationTime = :reservationTime")
    Optional<AvailableTime> findExactAvailableTime(Long repairShopId, Date reservationDate, Time reservationTime);

    @Query("""
            UPDATE AvailableTime a
            SET a.reservationCount = a.reservationCount + 1
            WHERE a.repairShop.id = :repairShopId
            AND a.reservationDate = :reservationDate
            AND a.reservationTime = :reservationTime
            """)
    void increaseReservationCount(Long repairShopId, Date reservationDate, Time reservationTime);
}
