package com.genesisairport.reservation.response;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ReservationResponseTest {

    @Test
    void createCouponValid_shouldCreateInstanceWithCorrectValue() {
        boolean valid = true;

        ReservationResponse.CouponValid couponValid = ReservationResponse.CouponValid.builder()
                .valid(valid)
                .build();

        assertNotNull(couponValid);
        assertEquals(valid, couponValid.isValid());
    }

    @Test
    void createDateInfo_shouldCreateInstanceWithCorrectValues() {
        List<String> availableDates = Arrays.asList("2024-02-27", "2024-02-28");

        ReservationResponse.DateInfo dateInfo = ReservationResponse.DateInfo.builder()
                .availableDates(availableDates)
                .build();

        assertNotNull(dateInfo);
        assertEquals(availableDates, dateInfo.getAvailableDates());
    }

    @Test
    void createTimeList_shouldCreateInstanceWithCorrectValues() {
        List<ReservationResponse.TimeInfo> timeSlots = Arrays.asList(
                ReservationResponse.TimeInfo.builder().time(10).available(true).build(),
                ReservationResponse.TimeInfo.builder().time(11).available(false).build()
        );

        ReservationResponse.TimeList timeList = ReservationResponse.TimeList.builder()
                .timeSlots(timeSlots)
                .build();

        assertNotNull(timeList);
        assertEquals(timeSlots, timeList.getTimeSlots());
    }

    @Test
    void createReservationPostResponse_shouldCreateInstanceWithCorrectValues() {
        boolean reservationStatus = true;
        String repairShopAddress = "서울특별시";
        String customerName = "John Doe";

        ReservationResponse.ReservationPostResponse reservationPostResponse = ReservationResponse.ReservationPostResponse.builder()
                .reservationStatus(reservationStatus)
                .repairShopAddress(repairShopAddress)
                .customerName(customerName)
                .build();

        assertNotNull(reservationPostResponse);
        assertEquals(reservationStatus, reservationPostResponse.isReservationStatus());
        assertEquals(repairShopAddress, reservationPostResponse.getRepairShopAddress());
        assertEquals(customerName, reservationPostResponse.getCustomerName());
    }

    @Test
    void createReservationInfoAbstract_shouldCreateInstanceWithCorrectValues() {
        long reservationId = 123456L;
        String departureTime = "2024-02-27T10:00:00";
        String arrivalTime = "2024-02-27T12:00:00";
        String progressStage = "정비중";
        String carSellName = "Genesis G80";
        String repairShop = "블루핸즈 인천공항점";
        String imageUrl = "https://example.com/image.jpg";

        ReservationResponse.ReservationInfoAbstract reservationInfoAbstract = new ReservationResponse.ReservationInfoAbstract(reservationId, departureTime, arrivalTime, progressStage, carSellName, repairShop, imageUrl);

        assertNotNull(reservationInfoAbstract);
        assertEquals(reservationId, reservationInfoAbstract.getReservationId());
        assertEquals(departureTime, reservationInfoAbstract.getDepartureTime());
        assertEquals(arrivalTime, reservationInfoAbstract.getArrivalTime());
        assertEquals(progressStage, reservationInfoAbstract.getProgressStage());
        assertEquals(carSellName, reservationInfoAbstract.getCarSellName());
        assertEquals(repairShop, reservationInfoAbstract.getRepairShop());
        assertEquals(imageUrl, reservationInfoAbstract.getImageUrl());
    }

    @Test
    void createReservationDetail_shouldCreateInstanceWithCorrectValues() {
        // Given
        long reservationId = 123456L;
        long customerId = 7890L;
        String customerName = "John Doe";
        String couponSerialNumber = "COUPON123";
        String repairShop = "블루핸즈 인천공항점";
        String repairShopAddress = "서울특별시";
        String from = "2024-02-27 10:00:00";
        String to = "2024-02-27 12:00:00";
        String contactNumber = "123-456-7890";
        String carSellName = "Genesis G80";
        String carPlateNumber = "123가 1234";
        Map<String, Boolean> serviceType = new HashMap<>();
        serviceType.put("type1", true);
        serviceType.put("type2", false);
        String customerRequest = "안녕안녕";
        List<ReservationResponse.ReservationDetail.ProgressStage> progressStage = Arrays.asList(
                ReservationResponse.ReservationDetail.ProgressStage.builder().id(1L).step("예약완료").date("2024-02-27").detail("Detail 1").build(),
                ReservationResponse.ReservationDetail.ProgressStage.builder().id(2L).step("정비중").date("2024-02-28").detail("Detail 2").build()
        );
        String checkupResult = "result";
        List<ReservationResponse.ReservationDetail.ImageContainer> beforeImages = Arrays.asList(
                ReservationResponse.ReservationDetail.ImageContainer.builder().id(1L).url("https://example.com/before1.jpg").build(),
                ReservationResponse.ReservationDetail.ImageContainer.builder().id(2L).url("https://example.com/before2.jpg").build()
        );
        List<ReservationResponse.ReservationDetail.ImageContainer> afterImages = Arrays.asList(
                ReservationResponse.ReservationDetail.ImageContainer.builder().id(3L).url("https://example.com/after1.jpg").build(),
                ReservationResponse.ReservationDetail.ImageContainer.builder().id(4L).url("https://example.com/after2.jpg").build()
        );
        String managerPhoneNumber = "987-654-3210";
        String imageUrl = "https://example.com/image.jpg";

        ReservationResponse.ReservationDetail reservationDetail = ReservationResponse.ReservationDetail.builder()
                .reservationId(reservationId)
                .customerId(customerId)
                .customerName(customerName)
                .couponSerialNumber(couponSerialNumber)
                .repairShop(repairShop)
                .repairShopAddress(repairShopAddress)
                .from(from)
                .to(to)
                .contactNumber(contactNumber)
                .carSellName(carSellName)
                .carPlateNumber(carPlateNumber)
                .serviceType(serviceType)
                .customerRequest(customerRequest)
                .progressStage(progressStage)
                .checkupResult(checkupResult)
                .beforeImages(beforeImages)
                .afterImages(afterImages)
                .managerPhoneNumber(managerPhoneNumber)
                .imageUrl(imageUrl)
                .build();

        assertNotNull(reservationDetail);
        assertEquals(reservationId, reservationDetail.getReservationId());
        assertEquals(customerId, reservationDetail.getCustomerId());
        assertEquals(customerName, reservationDetail.getCustomerName());
        assertEquals(couponSerialNumber, reservationDetail.getCouponSerialNumber());
        assertEquals(repairShop, reservationDetail.getRepairShop());
        assertEquals(repairShopAddress, reservationDetail.getRepairShopAddress());
        assertEquals(from, reservationDetail.getFrom());
        assertEquals(to, reservationDetail.getTo());
        assertEquals(contactNumber, reservationDetail.getContactNumber());
        assertEquals(carSellName, reservationDetail.getCarSellName());
        assertEquals(carPlateNumber, reservationDetail.getCarPlateNumber());
        assertEquals(serviceType, reservationDetail.getServiceType());
        assertEquals(customerRequest, reservationDetail.getCustomerRequest());
        assertEquals(progressStage, reservationDetail.getProgressStage());
        assertEquals(checkupResult, reservationDetail.getCheckupResult());
        assertEquals(beforeImages, reservationDetail.getBeforeImages());
        assertEquals(afterImages, reservationDetail.getAfterImages());
        assertEquals(managerPhoneNumber, reservationDetail.getManagerPhoneNumber());
        assertEquals(imageUrl, reservationDetail.getImageUrl());
    }

    @Test
    void createProgressId_shouldCreateInstanceWithCorrectValue() {
        Long stepId = 123L;

        ReservationResponse.ProgressId progressId = ReservationResponse.ProgressId.builder()
                .stepId(stepId)
                .build();

        assertNotNull(progressId);
        assertEquals(stepId, progressId.getStepId());
    }

    @Test
    void createHasReservation_shouldCreateInstanceWithCorrectValue() {
        boolean hasReservation = true;

        ReservationResponse.hasReservation hasReservationObj = ReservationResponse.hasReservation.builder()
                .hasReservation(hasReservation)
                .build();

        assertNotNull(hasReservationObj);
        assertEquals(hasReservation, hasReservationObj.isHasReservation());
    }
}
