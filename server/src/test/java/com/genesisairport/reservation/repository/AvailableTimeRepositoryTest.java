package com.genesisairport.reservation.repository;

import com.genesisairport.reservation.TestJPAQueryFactoryConfiguration;
import com.genesisairport.reservation.entity.AvailableTime;
import com.genesisairport.reservation.entity.RepairShop;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Date;
import java.sql.Time;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@Import(TestJPAQueryFactoryConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class AvailableTimeRepositoryTest {

    @Autowired
    private AvailableTimeRepository availableTimeRepository;

    @Test
    void findExactAvailableTime_WhenAvailableTimeExists_ReturnAvailableTime() {
        long repairShopId = 1L;
        Date reservationDate = Date.valueOf("2024-02-27");
        Time reservationTime = Time.valueOf("09:00:00");

        AvailableTime availableTime = new AvailableTime();
        availableTime.setRepairShop(RepairShop.builder().id(repairShopId).build());
        availableTime.setReservationDate(reservationDate);
        availableTime.setReservationTime(reservationTime);
        availableTimeRepository.save(availableTime);

        Optional<AvailableTime> foundAvailableTimeOptional = availableTimeRepository.findExactAvailableTime(repairShopId, reservationDate, reservationTime);

        assertTrue(foundAvailableTimeOptional.isPresent());
        AvailableTime foundAvailableTime = foundAvailableTimeOptional.get();
        assertEquals(repairShopId, foundAvailableTime.getRepairShop().getId());
        assertEquals(reservationDate, foundAvailableTime.getReservationDate());
        assertEquals(reservationTime, foundAvailableTime.getReservationTime());
    }

    @Test
    void findExactAvailableTime_WhenAvailableTimeDoesNotExist_ReturnEmptyOptional() {
        long repairShopId = 1L;
        Date reservationDate = Date.valueOf("2024-02-27");
        Time reservationTime = Time.valueOf("09:00:00");

        Optional<AvailableTime> foundAvailableTimeOptional = availableTimeRepository.findExactAvailableTime(repairShopId, reservationDate, reservationTime);

        assertTrue(foundAvailableTimeOptional.isEmpty());
    }
}
