package com.genesisairport.reservation.service;

import com.genesisairport.reservation.response.ReservationPostResponse;
import com.genesisairport.reservation.response.ReservationResponse;
import com.genesisairport.reservation.response.ReservationDateResponse;
import com.genesisairport.reservation.respository.CarRepository;
import com.genesisairport.reservation.entity.Coupon;
import com.genesisairport.reservation.entity.Reservation;
import com.genesisairport.reservation.response.ReservationListAbstract;
import com.genesisairport.reservation.respository.CouponRepository;
import com.genesisairport.reservation.respository.CustomerRepository;
import com.genesisairport.reservation.respository.RepairShopRepository;
import com.genesisairport.reservation.respository.ReservationRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final CustomerRepository customerRepository;
    private final RepairShopRepository repairShopRepository;
    private final CarRepository carRepository;
    private final CouponRepository couponRepository;

    public Boolean validateCoupon(String serialNumber) {
        return couponRepository.existsBySerialNumber(serialNumber);
    }


    public List<ReservationResponse.CarInfo> getCarList(Long userId) {
        List<ReservationResponse.CarInfo> carInfoList = carRepository.findCarsByCustomer(userId);
        return carInfoList.isEmpty() ? null : carInfoList;
    }

    public Map<String, List<ReservationDateResponse>> getAvailableDates() {
        List<ReservationDateResponse> availableDates = new ArrayList<>();

        // 예시로 임의의 데이터를 생성
        LocalDate currentDate = LocalDate.now();
        for (int i = 0; i < 5; i++) {
            LocalDate date = currentDate.plusDays(i);
            ReservationDateResponse availableDate = ReservationDateResponse.builder()
                    .date(date.toString())
                    .timeSlots(generateTimeSlots())
                    .build();
            availableDates.add(availableDate);
        }

        Map<String, List<ReservationDateResponse>> map = new HashMap<>();
        map.put("available_reservation_lsit", availableDates);

        return map;
    }

    private List<ReservationDateResponse.TimeSlot> generateTimeSlots() {
        List<ReservationDateResponse.TimeSlot> timeSlots = new ArrayList<>();

        // 예시로 임의의 타임슬롯 정보를 생성
        for (int i = 9; i <= 18; i++) {
            ReservationDateResponse.TimeSlot timeSlot = ReservationDateResponse.TimeSlot.builder()
                    .time(i)
                    .available(i % 2 == 0) // 홀수 시간대는 false로, 짝수 시간대는 true로 설정 (임의의 데이터)
                    .build();
            timeSlots.add(timeSlot);
        }

        return timeSlots;
    }

    public ReservationPostResponse reserve(Map<String, Object> requestBody) {
        // 요청 바디에서 필요한 정보 추출
        String from = (String) requestBody.get("from");
        String to = (String) requestBody.get("to");
        String contactNumber = (String) requestBody.get("contact_number");
        String sellName = (String) requestBody.get("car_sell_name");
        String plateNumber = (String) requestBody.get("car_plate_number");
        String serviceType = requestBody.get("service_type").toString();
        String customerRequest = (String) requestBody.get("customer_request");
        Long customerId = (Long) requestBody.get("customer_id");
        String couponSerialNumber = (String) requestBody.get("coupon_serial_number");
        String repairShop = (String) requestBody.get("repair_shop");

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
                .customer(customerRepository.findCustomerById(customerId))
                .coupon(couponRepository.findCouponBySerialNumber(couponSerialNumber))
                .repairShop(repairShopRepository.findRepairShopByRepairShop(repairShop))
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

        return ReservationPostResponse.builder()
                .reservationStatus(reservationStatus)
                .reservationId(reservationRepository.findReservationByCustomerId(customerId))
                .repairShopAddress(repairShopRepository.findRepairShopByRepairShop(repairShop).getAddress())
                .from(fromDateTime)
                .to(toDateTime)
                .build();
    }

    public List<ReservationListAbstract> getReservationList(Long customerId) {
        List<Reservation> reservationList = reservationRepository.findReservationsByCustomerId(customerId);
        List<ReservationListAbstract> reservationDTOs = new ArrayList<>();

        for (Reservation r : reservationList) {
            ReservationListAbstract reservationDTO = new ReservationListAbstract(r.getId(), r.getDepartureTime().toString(), r.getArrivalTime().toString(), r.getProgressStage(), r.getSellName(), r.getRepairShop().getRepairShop());
            reservationDTOs.add(reservationDTO);
        }

        return reservationDTOs;
    }
}
