package com.genesisairport.reservation.service;

import com.genesisairport.reservation.entity.*;
import com.genesisairport.reservation.request.ReservationRequest;
import com.genesisairport.reservation.response.ReservationResponse;
import com.genesisairport.reservation.respository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
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
        Coupon coupon = couponRepository.findCouponBySerialNumber(serialNumber);
        return coupon != null && !coupon.getIsUsed() && !coupon.getExpiredDate().isBefore(LocalDate.now());
    }


    public List<ReservationResponse.CarInfo> getCarList(Long userId) {
        return carRepository.findCarsByCustomer(userId);
    }

    public ReservationResponse.DateInfo getAvailableDates(String shopName) {
        return repairShopRepository.findAvailableDates(shopName);
    }

    public ReservationResponse.TimeList getAvailableTimes(String shopName, LocalDate date) {
        return ReservationResponse.TimeList.builder()
                .timeSlots(repairShopRepository.findAvailableTimes(shopName, date))
                .build();
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
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


        if (!Strings.isEmpty(couponSerialNumber)) {
            Coupon c = couponRepository.findCouponBySerialNumber(couponSerialNumber);
            if (c != null && !c.getIsUsed()) {
                reservationStatus = true;
                reservationRepository.save(reservation);
                c.setIsUsed(true);
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

    public Optional<ReservationResponse.ReservationDetail> getReservationDetail(Long reservationId) {
        Reservation reservation = reservationRepository.findReservationById(reservationId);
        if (reservation == null) {
            return Optional.empty();
        }

        Customer customer = reservation.getCustomer();
        Coupon coupon = reservation.getCoupon();
        RepairShop repairShop = reservation.getRepairShop();

        // date (to, from)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String fromString = reservation.getDepartureTime().format(formatter);
        String toString = reservation.getArrivalTime().format(formatter);

        // serviceType
        Map<String, Boolean> serviceType = new HashMap<>();
        String serviceTypeString = reservation.getServiceType();
        String substring = serviceTypeString.substring(1, serviceTypeString.length() - 1);
        for(String typeString : substring.split(", ")) {
            String[] split = typeString.split("=");
            serviceType.put(split[0], split[1].equals("true"));
        }

        // maintenanceImage
        List<String> beforeImages = new ArrayList<>();
        List<String> afterImages = new ArrayList<>();
        List<MaintenanceImage> maintenanceImage = reservation.getMaintenanceImage();
        for (MaintenanceImage image : maintenanceImage) {
            if (image.getStatus() == 1) {
                afterImages.add(image.getImageUrl());
            } else {
                beforeImages.add(image.getImageUrl());
            }
        }

        // progressStage
        List<ReservationResponse.ReservationDetail.ProgressStage> progressStages = new ArrayList<>();
        List<Step> steps = reservation.getStep();
        for(Step step : steps) {
            progressStages.add(ReservationResponse.ReservationDetail.ProgressStage.builder()
                    .step(step.getStage())
                    .date(step.getDate().format(DateTimeFormatter.ofPattern("yyyy년 MM월 d일 a hh:mm")))
                    .detail(step.getDetail())
                    .build());
        }

        return Optional.of(ReservationResponse.ReservationDetail.builder()
                .reservationId(reservation.getId())
                .customerId(customer.getId())
                .couponSerialNumber(coupon.getSerialNumber())
                .repairShop(repairShop.getShopName())
                .repairShopAddress(repairShop.getAddress())
                .from(fromString)
                .to(toString)
                .contactNumber(reservation.getContactNumber())
                .carSellName(reservation.getSellName())
                .carPlateNumber(reservation.getPlateNumber())
                .serviceType(serviceType)
                .customerRequest(reservation.getCustomerRequest())
                .progressStage(progressStages)
                .checkupResult(reservation.getInspectionResult())
                .beforeImages(beforeImages)
                .afterImages(afterImages)
                .managerPhoneNumber(reservation.getContactNumber())
                .build());
    }
}
