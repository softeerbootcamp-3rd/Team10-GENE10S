package com.genesisairport.reservation.request;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ReservationRequestTest {

    @Test
    void createReservationPost_shouldCreateInstanceWithCorrectValues() {
        String couponSerialNumber = "123456789";
        String shopName = "Shop A";
        String departureTime = "2024-03-01T10:00:00";
        String arrivalTime = "2024-03-01T12:00:00";
        String contactNumber = "123-456-7890";
        String carSellName = "Car A";
        String carPlateNumber = "ABC123";
        Object serviceType = "Service Type A";
        String customerRequest = "Customer Request A";

        ReservationRequest.ReservationPost reservationPost = ReservationRequest.ReservationPost.builder()
                .couponSerialNumber(couponSerialNumber)
                .shopName(shopName)
                .departureTime(departureTime)
                .arrivalTime(arrivalTime)
                .contactNumber(contactNumber)
                .carSellName(carSellName)
                .carPlateNumber(carPlateNumber)
                .serviceType(serviceType)
                .customerRequest(customerRequest)
                .build();

        assertNotNull(reservationPost);
        assertEquals(couponSerialNumber, reservationPost.getCouponSerialNumber());
        assertEquals(shopName, reservationPost.getShopName());
        assertEquals(departureTime, reservationPost.getDepartureTime());
        assertEquals(arrivalTime, reservationPost.getArrivalTime());
        assertEquals(contactNumber, reservationPost.getContactNumber());
        assertEquals(carSellName, reservationPost.getCarSellName());
        assertEquals(carPlateNumber, reservationPost.getCarPlateNumber());
        assertEquals(serviceType, reservationPost.getServiceType());
        assertEquals(customerRequest, reservationPost.getCustomerRequest());
    }
}
