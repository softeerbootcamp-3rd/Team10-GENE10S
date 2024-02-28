package com.genesisairport.reservation.service.admin;

import com.genesisairport.reservation.common.enums.ProgressStage;
import com.genesisairport.reservation.common.enums.ResponseCode;
import com.genesisairport.reservation.common.exception.GeneralException;
import com.genesisairport.reservation.common.util.CommonDateFormat;
import com.genesisairport.reservation.entity.AvailableTime;
import com.genesisairport.reservation.entity.RepairShop;
import com.genesisairport.reservation.entity.Reservation;
import com.genesisairport.reservation.repository.AvailableTimeRepository;
import com.genesisairport.reservation.repository.RepairShopRepository;
import com.genesisairport.reservation.repository.ReservationRepository;
import com.genesisairport.reservation.request.AdminRequest;
import com.genesisairport.reservation.response.AdminResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AShopService {
    private final RepairShopRepository repairShopRepository;
    private final AvailableTimeRepository availableTimeRepository;
    private final ReservationRepository reservationRepository;
    private final AReservationService aReservationService;

    public ResponseCode addAvailableTime(AdminRequest.ReservationTime requestBody) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(requestBody.getBusinessDay(), formatter);

        LocalDate date = localDateTime.toLocalDate();
        LocalTime time = localDateTime.toLocalTime();

        Optional<RepairShop> repairShop = repairShopRepository.findByShopName(requestBody.getShopName());
        if (repairShop.isEmpty()) {
            throw new GeneralException(ResponseCode.NOT_FOUND, "존재하지 않는 지점명입니다.");
        }

        Optional<AvailableTime> exactAvailableTime = availableTimeRepository.findExactAvailableTime(repairShop.get().getId(), date, time);
        if (exactAvailableTime.isPresent()) {
            throw new GeneralException(ResponseCode.CONFLICT, "이미 추가된 시간입니다.");
        }

        AvailableTime availableTime = AvailableTime.builder()
                .repairShop(repairShop.get())
                .reservationDate(date)
                .reservationTime(time)
                .reservationCount(0)
                .createDatetime(LocalDateTime.now())
                .updateDatetime(LocalDateTime.now())
                .build();

        availableTimeRepository.save(availableTime);

        return ResponseCode.OK;
    }

    public ResponseCode deleteAvailableTime(String shopName, String businessDay) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(businessDay, formatter);

        LocalDate date = localDateTime.toLocalDate();
        LocalTime time = localDateTime.toLocalTime();

        Optional<RepairShop> repairShop = repairShopRepository.findByShopName(shopName);
        if (repairShop.isEmpty()) {
            throw new GeneralException(ResponseCode.NOT_FOUND, "유효하지 않은 지점명입니다.");
        }

        Optional<AvailableTime> exactAvailableTime = availableTimeRepository.findExactAvailableTime(repairShop.get().getId(), date, time);
        if (exactAvailableTime.isEmpty()) {
            throw new GeneralException(ResponseCode.BAD_REQUEST, "시간 정보가 존재하지 않습니다.");
        }
        availableTimeRepository.delete(exactAvailableTime.get());
        return ResponseCode.OK;
    }

    public List<AdminResponse.AvailableTime> getAvailableTime(String shopName, String businessDayFrom, String businessDayTo) {
        LocalDate dateFrom = CommonDateFormat.localDate(businessDayFrom);
        LocalDate dateTo = CommonDateFormat.localDate(businessDayTo);

        return availableTimeRepository.findAvailableTimeList(shopName, dateFrom, dateTo);
    }

    public ResponseCode deleteAvailableTimeWithReservation(String shopName, String businessTime, String message) {
        Optional<RepairShop> repairShop = repairShopRepository.findByShopName((shopName));
        if (repairShop.isEmpty()) {
            throw new GeneralException(ResponseCode.BAD_REQUEST, "존재하지 않는 지점명입니다.");
        }

        LocalDateTime datetime = CommonDateFormat.localDateTime(businessTime);
        LocalDate date = LocalDate.parse(businessTime.split(" ")[0]);
        LocalTime time = LocalTime.parse(businessTime.split(" ")[1]);

        try {
            List<Reservation> reservations = reservationRepository.findReservationsBy(repairShop.get().getId(), datetime);

            for (Reservation reservation : reservations) {
                AdminRequest.StageInfo stageInfo = new AdminRequest.StageInfo(reservation.getId(), ProgressStage.CANCELED, message);
                aReservationService.saveStage(stageInfo);
            }
        } catch (Exception e) {
            throw new GeneralException(ResponseCode.INTERNAL_SERVER_ERROR, "예약을 취소하는 중 문제가 발생했습니다.");
        }

        Optional<AvailableTime> exactAvailableTime = availableTimeRepository.findExactAvailableTime(repairShop.get().getId(), date, time);
        if (exactAvailableTime.isEmpty()) {
            throw new GeneralException(ResponseCode.BAD_REQUEST, "이미 삭제된 시간입니다.");
        }

        availableTimeRepository.delete(exactAvailableTime.get());

        return ResponseCode.OK;
    }
}
