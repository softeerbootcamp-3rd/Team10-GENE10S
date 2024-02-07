package com.genesisairport.reservation.service;



import com.genesisairport.reservation.response.ReservationListAbstract;
import com.genesisairport.reservation.response.ReservationPostResponse;
import com.genesisairport.reservation.response.ReservationResponse;
import com.genesisairport.reservation.response.ReservationDateResponse;
import com.genesisairport.reservation.respository.CouponRepository;

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

    public ReservationPostResponse reserve(Map<String, Object> requestBody) {
        // 요청 바디에서 필요한 정보 추출
        int customerId = (int) requestBody.get("customer_id");
        String repairShop = (String) requestBody.get("repair_shop");
        String from = (String) requestBody.get("from");
        String to = (String) requestBody.get("to");

        // 예약 상태 확인 (임의로 true로 설정)
        boolean reservationStatus = true;

        // 예약 ID 생성 (임의로 1로 설정)
        long reservationId = 1L;

        // 예약 정보 생성
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("reservation_status", reservationStatus);
        responseData.put("reservation_id", reservationId);

        // 인천 중구 용유서로172번길 56 블루핸즈 인천공항점 주소 설정
        String repairShopAddress = "인천 중구 용유서로172번길 56 블루핸즈 인천공항점";
        responseData.put("repair_shop_address", repairShopAddress);

        // 날짜 형식 변환
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy/MM/dd HH:mm:ss");
        LocalDateTime fromDateTime = LocalDateTime.parse(from, formatter);
        LocalDateTime toDateTime = LocalDateTime.parse(to, formatter);
        responseData.put("reservation_from", fromDateTime);
        responseData.put("reservation_to", toDateTime);

        return ReservationPostResponse.builder()
                .reservationStatus(reservationStatus)
                .reservationId(reservationId)
                .repairShopAddress(repairShopAddress)
                .from(fromDateTime)
                .to(toDateTime)
                .build();
    }

    public ReservationListAbstract getReservationList() {
        // 예약 내역 리스트 생성 (임의로 데이터 생성)
        List<ReservationListAbstract.ReservationAbstract> reservationList = new ArrayList<>();

        // 첫 번째 예약 내역
        ReservationListAbstract.ReservationAbstract reservation1 = ReservationListAbstract.ReservationAbstract.builder()
                .reservationId(1)
                .from("2024-03-01 14:00:00")
                .to("2024-03-07 20:00:00")
                .progressStage("예약")
                .carSellName("Genesis G90")
                .carPlateNumber("00ㅁ 0000")
                .repairShop("블루핸즈 인천공항점")
                .repairShopAddress("인천 중구 용유서로172번길 56 블루핸즈 인천공항점")
                .build();
        reservationList.add(reservation1);

        // 두 번째 예약 내역
        ReservationListAbstract.ReservationAbstract reservation2 = ReservationListAbstract.ReservationAbstract.builder()
                .reservationId(2)
                .from("2024-03-10 09:00:00")
                .to("2024-03-15 18:00:00")
                .progressStage("접수")
                .carSellName("Genesis G90")
                .carPlateNumber("00ㅂ 1111")
                .repairShop("블루핸즈 인천공항점")
                .repairShopAddress("인천 중구 용유서로172번길 56 블루핸즈 인천공항점")
                .build();
        reservationList.add(reservation2);

        // 세 번째 예약 내역
        ReservationListAbstract.ReservationAbstract reservation3 = ReservationListAbstract.ReservationAbstract.builder()
                .reservationId(3)
                .from("2024-03-20 13:30:00")
                .to("2024-03-25 16:00:00")
                .progressStage("점검중")
                .carSellName("Genesis G90")
                .carPlateNumber("00ㅇ 2222")
                .repairShop("블루핸즈 인천공항점")
                .repairShopAddress("인천 중구 용유서로172번길 56 블루핸즈 인천공항점")
                .build();
        reservationList.add(reservation3);

        return ReservationListAbstract.builder().reservationList(reservationList).build();
    }
}
