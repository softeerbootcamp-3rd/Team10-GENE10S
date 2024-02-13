package com.genesisairport.reservation.service;

import com.genesisairport.reservation.entity.CarImage;
import com.genesisairport.reservation.request.ReservationRequest;
import com.genesisairport.reservation.response.ReservationResponse;
import com.genesisairport.reservation.respository.*;
import com.genesisairport.reservation.entity.Coupon;
import com.genesisairport.reservation.entity.Reservation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final CustomerRepository customerRepository;
    private final RepairShopRepository repairShopRepository;
    private final CarRepository carRepository;
    private final CouponRepository couponRepository;
    private final CarImageRepository carImageRepository;

    public Boolean validateCoupon(String serialNumber) {
        return couponRepository.existsBySerialNumber(serialNumber);
    }


    public List<ReservationResponse.CarInfo> getCarList(Long userId) {
        List<ReservationResponse.CarInfo> carInfoList = carRepository.findCarsByCustomer(userId);
        return carInfoList.isEmpty() ? null : carInfoList;
    }

    public ReservationResponse.DateInfo getAvailableDates(String shopName) {
        ReservationResponse.DateInfo dateInfo = repairShopRepository.findAvailableDates(shopName);
        return dateInfo.getAvailableDates().isEmpty() ? null : dateInfo;
    }

    public ReservationResponse.TimeList getAvailableTimes(String shopName, LocalDate date) {
        ReservationResponse.TimeList timeList = ReservationResponse.TimeList.builder()
                .timeSlots(repairShopRepository.findAvailableTimes(shopName, date))
                .build();
        return timeList.getTimeSlots().isEmpty() ? null : timeList;
    }

    public ReservationResponse.ReservationPostResponse reserve(Long customerId, ReservationRequest.ReservationPost requestBody) {
        // 요청 바디에서 필요한 정보 추출
        String from = requestBody.getDepartureTime();
        String to = requestBody.getArrivalTime();
        String contactNumber = requestBody.getContactNumber();
        String sellName = requestBody.getCarSellName();
        String plateNumber = requestBody.getCarPlateNumber();
        String serviceType = requestBody.getServiceType().toString();
        String customerRequest = requestBody.getCustomerRequest();
        String couponSerialNumber = requestBody.getCouponSerialNumber();
        String repairShop = requestBody.getShopName();

        // 예약 상태 확인 (임의로 true로 설정)
        boolean reservationStatus = false;

        // 날짜 형식 변환
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy/MM/dd HH:mm:ss");
        LocalDateTime fromDateTime = LocalDateTime.parse(from, formatter);
        LocalDateTime toDateTime = LocalDateTime.parse(to, formatter);

        Reservation reservation = Reservation.builder()
                .departureTime(fromDateTime)
                .arrivalTime(toDateTime)
                .contactNumber(contactNumber)
                .sellName(sellName)
                .plateNumber(plateNumber)
                .serviceType(serviceType)
                .customerRequest(customerRequest)
                .progressStage("예약중")
                .customer(customerRepository.findCustomerById((long) customerId))
                .coupon(couponRepository.findCouponBySerialNumber(couponSerialNumber))
                .repairShop(repairShopRepository.findRepairShopByShopName(repairShop))
                .createDateTime(LocalDateTime.now())
                .updateDateTime(LocalDateTime.now())
                .build();



        if (!couponRepository.findCouponBySerialNumber(couponSerialNumber).getIsUsed()) {
            reservationRepository.save(reservation);

            reservationStatus = true;
            Coupon c = couponRepository.findCouponBySerialNumber(couponSerialNumber);
            c.setIsUsed(reservationStatus);
            couponRepository.save(c);
        }

        return ReservationResponse.ReservationPostResponse.builder()
                .reservationStatus(reservationStatus)
                .repairShopAddress(repairShopRepository.findRepairShopByShopName(repairShop).getAddress())
                .customerName(customerRepository.findCustomerById(customerId).getName())
                .build();
    }

    public List<ReservationResponse.ReservationInfoAbstract> getReservationList(Long customerId) {
        List<Reservation> reservationList = reservationRepository.findReservationsByCustomerId(customerId);
        List<ReservationResponse.ReservationInfoAbstract> reservationDTOs = new ArrayList<>();

        for (Reservation r : reservationList) {
            Optional<CarImage> carImageOptional = carImageRepository.findBySellName(r.getSellName());
            if (carImageOptional.isPresent()) {
                CarImage carImage = carImageOptional.get();
                ReservationResponse.ReservationInfoAbstract reservationDTO = new ReservationResponse.ReservationInfoAbstract(r.getId(), r.getDepartureTime().toString(), r.getArrivalTime().toString(), r.getProgressStage(), r.getSellName(), r.getRepairShop().getShopName(), carImage.getImageUrl());
                reservationDTOs.add(reservationDTO);
            }
        }

        return reservationDTOs;
    }
}
