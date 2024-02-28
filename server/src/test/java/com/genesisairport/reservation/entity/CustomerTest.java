package com.genesisairport.reservation.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CustomerTest {

    @Test
    void createCustomer_shouldCreateInstanceWithCorrectValues() {
        Long id = 1L;
        String email = "test@example.com";
        String name = "John Doe";
        LocalDate birthdate = LocalDate.of(1990, 5, 15);
        String phoneNumber = "123-456-7890";
        LocalDateTime createDateTime = LocalDateTime.now();
        LocalDateTime updateDateTime = LocalDateTime.now();
        List<Reservation> reservations = new ArrayList<>();
        List<Car> cars = new ArrayList<>();

        Customer customer = Customer.builder()
                .id(id)
                .email(email)
                .name(name)
                .birthdate(birthdate)
                .phoneNumber(phoneNumber)
                .createDateTime(createDateTime)
                .updateDateTime(updateDateTime)
                .reservation(reservations)
                .cars(cars)
                .build();

        assertNotNull(customer);
        assertEquals(id, customer.getId());
        assertEquals(email, customer.getEmail());
        assertEquals(name, customer.getName());
        assertEquals(birthdate, customer.getBirthdate());
        assertEquals(phoneNumber, customer.getPhoneNumber());
        assertEquals(createDateTime, customer.getCreateDateTime());
        assertEquals(updateDateTime, customer.getUpdateDateTime());
        assertEquals(reservations, customer.getReservation());
        assertEquals(cars, customer.getCars());
    }
}
