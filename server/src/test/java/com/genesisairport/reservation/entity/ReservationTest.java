package com.genesisairport.reservation.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ReservationTest {

    @Test
    void createReservation_shouldCreateInstanceWithCorrectValues() {
        Long id = 1L;
        LocalDateTime departureTime = LocalDateTime.now();
        LocalDateTime arrivalTime = LocalDateTime.now().plusHours(2);
        String contactNumber = "1234567890";
        String sellName = "Sample Car";
        String plateNumber = "ABC123";
        String serviceType = "Regular Maintenance";
        String customerRequest = "Replace brake pads";
        String progressStage = "Pending";
        String inspectionResult = "Brake pads need replacement";
        LocalDateTime createDateTime = LocalDateTime.now();
        LocalDateTime updateDateTime = LocalDateTime.now();
        Customer customer = new Customer();
        List<MaintenanceImage> maintenanceImages = new ArrayList<>();
        Coupon coupon = new Coupon();
        RepairShop repairShop = new RepairShop();
        List<Step> steps = new ArrayList<>();

        Reservation reservation = Reservation.builder()
                .id(id)
                .departureTime(departureTime)
                .arrivalTime(arrivalTime)
                .contactNumber(contactNumber)
                .sellName(sellName)
                .plateNumber(plateNumber)
                .serviceType(serviceType)
                .customerRequest(customerRequest)
                .progressStage(progressStage)
                .inspectionResult(inspectionResult)
                .createDateTime(createDateTime)
                .updateDateTime(updateDateTime)
                .customer(customer)
                .maintenanceImage(maintenanceImages)
                .coupon(coupon)
                .repairShop(repairShop)
                .step(steps)
                .build();

        assertNotNull(reservation);
        assertEquals(id, reservation.getId());
        assertEquals(departureTime, reservation.getDepartureTime());
        assertEquals(arrivalTime, reservation.getArrivalTime());
        assertEquals(contactNumber, reservation.getContactNumber());
        assertEquals(sellName, reservation.getSellName());
        assertEquals(plateNumber, reservation.getPlateNumber());
        assertEquals(serviceType, reservation.getServiceType());
        assertEquals(customerRequest, reservation.getCustomerRequest());
        assertEquals(progressStage, reservation.getProgressStage());
        assertEquals(inspectionResult, reservation.getInspectionResult());
        assertEquals(createDateTime, reservation.getCreateDateTime());
        assertEquals(updateDateTime, reservation.getUpdateDateTime());
        assertEquals(customer, reservation.getCustomer());
        assertEquals(maintenanceImages, reservation.getMaintenanceImage());
        assertEquals(coupon, reservation.getCoupon());
        assertEquals(repairShop, reservation.getRepairShop());
        assertEquals(steps, reservation.getStep());
    }
}
