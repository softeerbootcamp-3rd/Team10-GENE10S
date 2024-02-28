package com.genesisairport.reservation.repository;

import com.genesisairport.reservation.entity.AvailableTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public interface AvailableTimeRepository extends JpaRepository<AvailableTime, Long>, AvailableTimeRepositoryCustom {
    @Query("SELECT a FROM AvailableTime a WHERE a.repairShop.id = :repairShopId AND a.reservationDate = :reservationDate AND a.reservationTime = :reservationTime")
    Optional<AvailableTime> findExactAvailableTime(Long repairShopId, LocalDate reservationDate, LocalTime reservationTime);

    @Query(value = """
            UPDATE available_time a
            SET a.reservation_count = a.reservation_count + 1
            WHERE a.repair_shop_id = :repairShopId
            AND a.reservation_date = :reservationDate
            AND a.reservation_time = :reservationTime
            """, nativeQuery = true)
    @Modifying
    @Transactional
    Integer increaseReservationCount(Long repairShopId, Date reservationDate, Time reservationTime);
}
