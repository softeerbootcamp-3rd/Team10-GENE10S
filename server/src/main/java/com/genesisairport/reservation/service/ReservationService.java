package com.genesisairport.reservation.service;

import com.genesisairport.reservation.common.enums.ProgressStage;
import com.genesisairport.reservation.common.enums.RedisKey;
import com.genesisairport.reservation.common.enums.ResponseCode;
import com.genesisairport.reservation.common.exception.GeneralException;
import com.genesisairport.reservation.common.util.CommonDateFormat;
import com.genesisairport.reservation.common.util.ConcurrencyManager;
import com.genesisairport.reservation.entity.*;
import com.genesisairport.reservation.repository.*;
import com.genesisairport.reservation.request.ReservationRequest;
import com.genesisairport.reservation.response.ReservationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationService {

    private final ConcurrencyManager concurrencyManager;

    private final ReservationRepository reservationRepository;
    private final AvailableTimeRepository availableTimeRepository;
    private final CustomerRepository customerRepository;
    private final RepairShopRepository repairShopRepository;
    private final CarRepository carRepository;
    private final CouponRepository couponRepository;
    private final CarImageRepository carImageRepository;
    private final StepRepository stepRepository;

    public Boolean validateCoupon(String serialNumber) {
        Optional<Coupon> coupon = couponRepository.findCouponBySerialNumber(serialNumber);
        return coupon.isPresent() && !coupon.get().getIsUsed() && !coupon.get().getExpiredDate().isBefore(LocalDate.now());
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
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (customer.isEmpty()) {
            throw new GeneralException(ResponseCode.NOT_FOUND, "존재하지 않는 고객 id입니다.");
        }
        Optional<Coupon> coupon = couponRepository.findCouponBySerialNumber(requestBody.getCouponSerialNumber());
        if (coupon.isEmpty()) {
            throw new GeneralException(ResponseCode.NOT_FOUND, "존재하지 않는 쿠폰입니다.");
        }
        if (coupon.get().getIsUsed()) {
            throw new GeneralException(ResponseCode.CONFLICT, "이미 사용된 쿠폰입니다.");
        }

        Optional<RepairShop> repairShop = repairShopRepository.findByShopName(requestBody.getShopName());
        if (repairShop.isEmpty()) {
            throw new GeneralException(ResponseCode.NOT_FOUND, "존재하지 않는 지점명입니다.");
        }


        // 날짜 형식 변환
        LocalDateTime fromDateTime = CommonDateFormat.localDateTime(requestBody.getDepartureTime());
        LocalDateTime toDateTime = CommonDateFormat.localDateTime(requestBody.getArrivalTime());

        Reservation reservation = Reservation.builder()
                .departureTime(fromDateTime)
                .arrivalTime(toDateTime)
                .contactNumber(requestBody.getContactNumber())
                .sellName(requestBody.getCarSellName())
                .plateNumber(requestBody.getCarPlateNumber())
                .serviceType(requestBody.getServiceType().toString())
                .customerRequest(requestBody.getCustomerRequest())
                .progressStage(ProgressStage.RESERVATION_APPROVED.getName())
                .customer(customer.get())
                .coupon(coupon.get())
                .repairShop(repairShop.get())
                .createDateTime(LocalDateTime.now())
                .updateDateTime(LocalDateTime.now())
                .build();

        checkConcurrency(repairShop.get(), fromDateTime, toDateTime);
        Reservation result = saveReservation(reservation);
        coupon.get().setIsUsed(true);
        couponRepository.save(coupon.get());

        // 초기 진행 단계 설정
        Step step = Step.builder()
                .reservation(result)
                .stage(ProgressStage.RESERVATION_APPROVED.getName())
                .date(LocalDateTime.now())
                .detail("")
                .createDatetime(LocalDateTime.now())
                .updateDatetime(LocalDateTime.now())
                .build();
        stepRepository.save(step);

        return ReservationResponse.ReservationPostResponse.builder()
                .reservationStatus(true)
                .repairShopAddress(repairShop.get().getAddress())
                .customerName(customer.get().getName())
                .build();
    }

    private void checkConcurrency(RepairShop repairShop, LocalDateTime fromDateTime, LocalDateTime toDateTime) {
        String fromDateTimeKey = concurrencyManager.createDateTimeKey(repairShop, fromDateTime);
        String toDateTimeKey = concurrencyManager.createDateTimeKey(repairShop, toDateTime);

        synchronizeCache(repairShop, fromDateTime, fromDateTimeKey);
        Integer fromCount = concurrencyManager.increase(RedisKey.RESERVATION, fromDateTimeKey);
        // 예약 가능 인원 수(5)를 넘으면 다시 줄이고 return false
        if (fromCount > repairShop.getCapacityPerTime()) {
            concurrencyManager.decrease(RedisKey.RESERVATION, fromDateTimeKey);
            throw new GeneralException(ResponseCode.CONFLICT, "예약 가능 인원을 초과하였습니다.");
        }

        synchronizeCache(repairShop, toDateTime, toDateTimeKey);
        Integer toCount = concurrencyManager.increase(RedisKey.RESERVATION, toDateTimeKey);
        // 5가 넘으면 (변수에서 갖고와서) 다시 줄이고 return false
        if (toCount > repairShop.getCapacityPerTime()) {
            concurrencyManager.decrease(RedisKey.RESERVATION, fromDateTimeKey);
            concurrencyManager.decrease(RedisKey.RESERVATION, toDateTimeKey);
            throw new GeneralException(ResponseCode.CONFLICT, "예약 가능 인원을 초과하였습니다.");
        }
    }

    public void synchronizeCache(RepairShop repairShop, LocalDateTime dateTime, String fromDateTimeKey) {
        Optional<Integer> existFromCache = concurrencyManager.get(RedisKey.RESERVATION, fromDateTimeKey);
        Date date = Date.valueOf(dateTime.toLocalDate());
        Time time = Time.valueOf(dateTime.toLocalTime());
        if (existFromCache.isEmpty()) {
            Optional<AvailableTime> availableTime = availableTimeRepository.findExactAvailableTime(
                    repairShop.getId(), date, time);
            if (availableTime.isEmpty()) {
                concurrencyManager.setNx(RedisKey.RESERVATION, fromDateTimeKey, 0);
            } else {
                concurrencyManager.setNx(RedisKey.RESERVATION, fromDateTimeKey, availableTime.get().getReservationCount());
            }
        }
    }

    private Reservation saveReservation(Reservation reservation) {
        Reservation result = reservationRepository.save(reservation);
        availableTimeRepository.increaseReservationCount(
                reservation.getRepairShop().getId(),
                Date.valueOf(reservation.getDepartureTime().toLocalDate()),
                Time.valueOf(reservation.getDepartureTime().toLocalTime()));
        availableTimeRepository.increaseReservationCount(
                reservation.getRepairShop().getId(),
                Date.valueOf(reservation.getArrivalTime().toLocalDate()),
                Time.valueOf(reservation.getArrivalTime().toLocalTime()));
        return result;
    }

    public List<ReservationResponse.ReservationInfoAbstract> getReservationList(Long customerId) {
        List<Reservation> reservationList = reservationRepository.findReservationsByCustomerId(customerId);
        List<ReservationResponse.ReservationInfoAbstract> reservationDTOs = new ArrayList<>();

        for (Reservation r : reservationList) {
            Optional<CarImage> carImageOptional = carImageRepository.findBySellName(r.getSellName());
            if (carImageOptional.isPresent()) {
                CarImage carImage = carImageOptional.get();

                // LocalDateTime 포메팅
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String departureDate = r.getDepartureTime().format(formatter);
                String arrivalDate = r.getArrivalTime().format(formatter);

                ReservationResponse.ReservationInfoAbstract reservationDTO = new ReservationResponse.ReservationInfoAbstract(r.getId(), departureDate, arrivalDate, r.getProgressStage(), r.getSellName(), r.getRepairShop().getShopName(), carImage.getImageUrl());
                reservationDTOs.add(reservationDTO);
            }
        }

        return reservationDTOs;
    }

    public Optional<ReservationResponse.ReservationDetail> getReservationDetail(Long reservationId) {
        Optional<Reservation> reservation = reservationRepository.findById(reservationId);
        if (reservation.isEmpty()) {
            throw new GeneralException(ResponseCode.NOT_FOUND, "예약 id를 찾을 수 없습니다.");
        }

        Customer customer = reservation.get().getCustomer();
        Coupon coupon = reservation.get().getCoupon();
        RepairShop repairShop = reservation.get().getRepairShop();

        // date (to, from)
        String fromString = CommonDateFormat.localDateTime(reservation.get().getDepartureTime());
        String toString = CommonDateFormat.localDateTime(reservation.get().getArrivalTime());

        // serviceType
        Map<String, Boolean> serviceType = new HashMap<>();
        String serviceTypeString = reservation.get().getServiceType();
        String substring = serviceTypeString.substring(1, serviceTypeString.length() - 1);
        for(String typeString : substring.split(", ")) {
            String[] split = typeString.split("=");
            serviceType.put(split[0], split[1].equals("true"));
        }

        // maintenanceImage
        List<ReservationResponse.ReservationDetail.ImageContainer> beforeImages = new ArrayList<>();
        List<ReservationResponse.ReservationDetail.ImageContainer> afterImages = new ArrayList<>();
        List<MaintenanceImage> maintenanceImage = reservation.get().getMaintenanceImage();
        for (MaintenanceImage image : maintenanceImage) {
            if (image.getStatus() == 1) {
                afterImages.add(ReservationResponse.ReservationDetail.ImageContainer.builder()
                        .id(image.getId())
                        .url(image.getImageUrl())
                        .build()
                );
            } else {
                beforeImages.add(ReservationResponse.ReservationDetail.ImageContainer.builder()
                        .id(image.getId())
                        .url(image.getImageUrl())
                        .build()
                );
            }
        }

        // progressStage
        List<ReservationResponse.ReservationDetail.ProgressStage> progressStages = new ArrayList<>();
        List<Step> steps = reservation.get().getStep();
        for(Step step : steps) {
            progressStages.add(ReservationResponse.ReservationDetail.ProgressStage.builder()
                    .id(step.getId())
                    .step(step.getStage())
                    .date(step.getDate().format(DateTimeFormatter.ofPattern("yyyy년 MM월 d일 a hh:mm")))
                    .detail(step.getDetail())
                    .build());
        }

        // car image
        Optional<CarImage> carImage = carImageRepository.findBySellName(reservation.get().getSellName());
        String imageUrl;
        if (carImage.isEmpty()) {
            return Optional.empty();
        } else {
            imageUrl = carImage.get().getImageUrl();
        }

        return Optional.of(ReservationResponse.ReservationDetail.builder()
                .reservationId(reservation.get().getId())
                .customerId(customer.getId())
                .customerName(customer.getName())
                .couponSerialNumber(coupon.getSerialNumber())
                .repairShop(repairShop.getShopName())
                .repairShopAddress(repairShop.getAddress())
                .from(fromString)
                .to(toString)
                .contactNumber(reservation.get().getContactNumber())
                .carSellName(reservation.get().getSellName())
                .carPlateNumber(reservation.get().getPlateNumber())
                .serviceType(serviceType)
                .customerRequest(reservation.get().getCustomerRequest())
                .progressStage(progressStages)
                .checkupResult(reservation.get().getInspectionResult())
                .beforeImages(beforeImages)
                .afterImages(afterImages)
                .inspectionResult(reservation.get().getInspectionResult())
                .managerPhoneNumber(reservation.get().getContactNumber())
                .imageUrl(imageUrl)
                .build());
    }
}
