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

        Optional<RepairShop> repairShop = repairShopRepository.findRepairShopByShopName(requestBody.getShopName());
        if (repairShop.isEmpty()) {
            throw new GeneralException(ResponseCode.NOT_FOUND, "존재하지 않는 지점명입니다.");
        }


        // 날짜 형식 변환
        LocalDateTime fromDateTime = CommonDateFormat.localDatetime(requestBody.getDepartureTime());
        LocalDateTime toDateTime = CommonDateFormat.localDatetime(requestBody.getArrivalTime());

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


        reservationRepository.save(reservation);
        coupon.get().setIsUsed(true);
        couponRepository.save(coupon.get());

        // 초기 진행 단계 설정
        Step step = Step.builder()
                .reservation(reservation)
                .stage(ProgressStage.RESERVATION_APPROVED.getName())
                .date(LocalDateTime.now())
                .detail("")
                .createDatetime(LocalDateTime.now())
                .updateDatetime(LocalDateTime.now())
                .build();

        String fromDateTimeKey = concurrencyManager.createDateKey(repairShop.get(), fromDateTime);
        String toDateTimeKey = concurrencyManager.createDateKey(repairShop.get(), toDateTime);

        // 캐시에서 먼저 읽어서 없으면
        Optional<Integer> existFromCache = concurrencyManager.get(RedisKey.RESERVATION, fromDateTimeKey);
        if (existFromCache.isEmpty()) {
            Integer existCount = reservationRepository.countByDepartureTimeAndRepairShop(fromDateTime, repairShop.get());
            concurrencyManager.setNx(RedisKey.RESERVATION, fromDateTimeKey, existCount);
        }

        // INCR
        Integer fromCount = concurrencyManager.increase(RedisKey.RESERVATION, fromDateTimeKey);

        // 예약 가능 인원 수(5)를 넘으면 다시 줄이고 return false
        if (fromCount > repairShop.get().getCapacityPerTime()) {
            concurrencyManager.decrease(RedisKey.RESERVATION, fromDateTimeKey);
            throw new GeneralException(ResponseCode.CONFLICT, "예약 가능 인원을 초과하였습니다.");
        }

        // ---- 여기까지가 from을 체크하는 부분

        Optional<Integer> existToCache = concurrencyManager.get(RedisKey.RESERVATION, toDateTimeKey);
        if (existToCache.isEmpty()) {
            Integer existCount = reservationRepository.countByDepartureTimeAndRepairShop(fromDateTime, repairShop.get());
            concurrencyManager.setNx(RedisKey.RESERVATION, toDateTimeKey, existCount);
        }

        // INCR
        Integer toCount = concurrencyManager.increase(RedisKey.RESERVATION, toDateTimeKey);

        // 5가 넘으면 (변수에서 갖고와서) 다시 줄이고 return false
        if (toCount > repairShop.get().getCapacityPerTime()) {
            concurrencyManager.decrease(RedisKey.RESERVATION, fromDateTimeKey);
            concurrencyManager.decrease(RedisKey.RESERVATION, toDateTimeKey);
            throw new GeneralException(ResponseCode.CONFLICT, "예약 가능 인원을 초과하였습니다.");
        }

        stepRepository.save(step);

        return ReservationResponse.ReservationPostResponse.builder()
                .reservationStatus(true)
                .repairShopAddress(repairShop.get().getAddress())
                .customerName(customer.get().getName())
                .build();
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
        String fromString = CommonDateFormat.localDatetime(reservation.get().getDepartureTime());
        String toString = CommonDateFormat.localDatetime(reservation.get().getArrivalTime());

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
                .managerPhoneNumber(reservation.get().getContactNumber())
                .imageUrl(imageUrl)
                .build());
    }
}
