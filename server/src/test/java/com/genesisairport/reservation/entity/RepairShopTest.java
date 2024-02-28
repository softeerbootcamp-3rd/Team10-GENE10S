package com.genesisairport.reservation.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RepairShopTest {

    @Test
    void createRepairShop_shouldCreateInstanceWithCorrectValues() {
        Long id = 1L;
        String shopName = "Sample Repair Shop";
        Integer capacityPerTime = 10;
        String address = "123 Main St, City, Country";
        LocalDateTime createDatetime = LocalDateTime.now();
        LocalDateTime updateDatetime = LocalDateTime.now();
        List<Reservation> reservations = new ArrayList<>();
        List<AvailableTime> availableTimes = new ArrayList<>();

        RepairShop repairShop = RepairShop.builder()
                .id(id)
                .shopName(shopName)
                .capacityPerTime(capacityPerTime)
                .address(address)
                .createDatetime(createDatetime)
                .updateDatetime(updateDatetime)
                .reservation(reservations)
                .availableTime(availableTimes)
                .build();

        assertNotNull(repairShop);
        assertEquals(id, repairShop.getId());
        assertEquals(shopName, repairShop.getShopName());
        assertEquals(capacityPerTime, repairShop.getCapacityPerTime());
        assertEquals(address, repairShop.getAddress());
        assertEquals(createDatetime, repairShop.getCreateDatetime());
        assertEquals(updateDatetime, repairShop.getUpdateDatetime());
        assertEquals(reservations, repairShop.getReservation());
        assertEquals(availableTimes, repairShop.getAvailableTime());
    }
}
