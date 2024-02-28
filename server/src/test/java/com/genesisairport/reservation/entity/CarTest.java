package com.genesisairport.reservation.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CarTest {

    @Test
    void createCar_shouldCreateInstanceWithCorrectValues() {
        Long id = 1L;
        String vin = "ABC123456789DEF";
        String sellName = "Genesis G80";
        String plateNumber = "123가 1234";
        LocalDateTime createDatetime = LocalDateTime.now();
        LocalDateTime updateDatetime = LocalDateTime.now();
        Customer customer = new Customer();

        Car car = Car.builder()
                .id(id)
                .vin(vin)
                .sellName(sellName)
                .plateNumber(plateNumber)
                .createDatetime(createDatetime)
                .updateDatetime(updateDatetime)
                .customer(customer)
                .build();

        // Then
        assertNotNull(car);
        assertEquals(id, car.getId());
        assertEquals(vin, car.getVin());
        assertEquals(sellName, car.getSellName());
        assertEquals(plateNumber, car.getPlateNumber());
        assertEquals(createDatetime, car.getCreateDatetime());
        assertEquals(updateDatetime, car.getUpdateDatetime());
        assertEquals(customer, car.getCustomer());
    }
}
