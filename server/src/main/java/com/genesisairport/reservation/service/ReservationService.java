package com.genesisairport.reservation.service;

import com.genesisairport.reservation.respository.CouponRepository;
import com.genesisairport.reservation.response.ReservationResponse;
import com.genesisairport.reservation.response.ReservationDateResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationService {

    private final CouponRepository couponRepository;

    public Boolean validateCoupon(String serialNumber) {
        return couponRepository.existsBySerialNumber(serialNumber);
    }

    public List<ReservationResponse.CarInfo> getCarList() {
        List<ReservationResponse.CarInfo> carInfoList = new ArrayList<>();


        ReservationResponse.CarInfo carInfo = ReservationResponse.CarInfo.builder()
                .sellName("Sonata")
                .plateNumber("222라 2222")
                .build();
        ReservationResponse.CarInfo carInfo1 = ReservationResponse.CarInfo.builder()
                .sellName("Sonata")
                .plateNumber("111마 1111")
                .build();

        carInfoList.add(carInfo);
        carInfoList.add(carInfo1);

        return carInfoList;
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
}
