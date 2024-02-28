package com.genesisairport.reservation.response;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AdminResponseTest {

    @Test
    void createAdminInfo_shouldCreateInstanceWithCorrectValues() {
        String adminName = "John Doe";

        AdminResponse.AdminInfo adminInfo = AdminResponse.AdminInfo.builder()
                .adminName(adminName)
                .build();

        assertNotNull(adminInfo);
        assertEquals(adminName, adminInfo.getAdminName());
    }

    @Test
    void createReservationDetail_shouldCreateInstanceWithCorrectValues() {
        long reservationId = 123456L;
        String shopName = "블루핸즈 인천공항점";
        String customerName = "김희진";
        String sellName = "Genesis G80";
        String departureTime = "2024-02-27 10:00:00";
        String arrivalTime = "2024-02-27 12:00:00";
        String stage = "정비중";

        AdminResponse.ReservationDetail reservationDetail = AdminResponse.ReservationDetail.builder()
                .reservationId(reservationId)
                .shopName(shopName)
                .customerName(customerName)
                .sellName(sellName)
                .departureTime(departureTime)
                .arrivalTime(arrivalTime)
                .stage(stage)
                .build();

        assertNotNull(reservationDetail);
        assertEquals(reservationId, reservationDetail.getReservationId());
        assertEquals(shopName, reservationDetail.getShopName());
        assertEquals(customerName, reservationDetail.getCustomerName());
        assertEquals(sellName, reservationDetail.getSellName());
        assertEquals(departureTime, reservationDetail.getDepartureTime());
        assertEquals(arrivalTime, reservationDetail.getArrivalTime());
        assertEquals(stage, reservationDetail.getStage());
    }

    @Test
    void createAccountDetail_shouldCreateInstanceWithCorrectValues() {
        long id = 1L;
        String adminId = "admin123";
        String adminName = "John Doe";
        String phoneNumber = "123-456-7890";
        String createDateTime = "2024-02-27 10:00:00";

        AdminResponse.AccountDetail accountDetail = AdminResponse.AccountDetail.builder()
                .id(id)
                .adminId(adminId)
                .adminName(adminName)
                .phoneNumber(phoneNumber)
                .createDateTime(createDateTime)
                .build();

        assertNotNull(accountDetail);
        assertEquals(id, accountDetail.getId());
        assertEquals(adminId, accountDetail.getAdminId());
        assertEquals(adminName, accountDetail.getAdminName());
        assertEquals(phoneNumber, accountDetail.getPhoneNumber());
        assertEquals(createDateTime, accountDetail.getCreateDateTime());
    }

    @Test
    void createAvailableTime_shouldCreateInstanceWithCorrectValues() {
        String date = "2024-02-27";
        List<String> availableTime = Arrays.asList("10:00", "11:00", "12:00");

        AdminResponse.AvailableTime availableTimeObj = AdminResponse.AvailableTime.builder()
                .date(date)
                .availableTime(availableTime)
                .build();

        assertNotNull(availableTimeObj);
        assertEquals(date, availableTimeObj.getDate());
        assertEquals(availableTime, availableTimeObj.getAvailableTime());
    }

    @Test
    void createUploadImage_shouldCreateInstanceWithCorrectValues() {
        Long imageId = 123L;
        String imageUrl = "https://example.com/image.jpg";

        AdminResponse.UploadImage uploadImage = AdminResponse.UploadImage.builder()
                .imageId(imageId)
                .imageUrl(imageUrl)
                .build();

        assertNotNull(uploadImage);
        assertEquals(imageId, uploadImage.getImageId());
        assertEquals(imageUrl, uploadImage.getImageUrl());
    }
}
