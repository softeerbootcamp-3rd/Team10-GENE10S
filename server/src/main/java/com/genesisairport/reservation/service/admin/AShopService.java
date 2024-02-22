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
        String shopName = requestBody.getShopName();
        String businessDay = requestBody.getBusinessDay();

        Date date = Date.valueOf(LocalDate.parse(businessDay.split(" ")[0]));
        Time time = Time.valueOf(LocalTime.parse(businessDay.split(" ")[1]));

        RepairShop repairShop = repairShopRepository.findRepairShopByShopName(shopName);
        if (repairShop == null) {
            throw new GeneralException(ResponseCode.INTERNAL_SERVER_ERROR, "존재하지 않는 지점명입니다.");
        }

        Optional<AvailableTime> exactAvailableTime = availableTimeRepository.findExactAvailableTime(repairShop.getId(), date, time);
        if (exactAvailableTime.isPresent()) {
            throw new GeneralException(ResponseCode.INTERNAL_SERVER_ERROR, "이미 추가된 시간입니다.");
        }

        AvailableTime availableTime = AvailableTime.builder()
                .repairShop(repairShop)
                .reservationDate(date)
                .reservationTime(time)
                .reservationCount(repairShop.getCapacityPerTime())
                .createDatetime(LocalDateTime.now())
                .updateDatetime(LocalDateTime.now())
                .build();

        availableTimeRepository.save(availableTime);

        return ResponseCode.OK;
    }

    public ResponseCode deleteAvailableTime(String shopName, String businessDay) {

        Date date = Date.valueOf(LocalDate.parse(businessDay.split(" ")[0]));
        Time time = Time.valueOf(LocalTime.parse(businessDay.split(" ")[1]));

        RepairShop repairShop = repairShopRepository.findRepairShopByShopName(shopName);
        if (repairShop == null) {
            throw new GeneralException(ResponseCode.INTERNAL_SERVER_ERROR, "유효하지 않은 지점명입니다.");
        }

        Optional<AvailableTime> exactAvailableTime = availableTimeRepository.findExactAvailableTime(repairShop.getId(), date, time);
        if (exactAvailableTime.isEmpty()) {
            throw new GeneralException(ResponseCode.INTERNAL_SERVER_ERROR, "이미 삭제된 시간입니다.");
        }

        availableTimeRepository.delete(exactAvailableTime.get());

        return ResponseCode.OK;
    }

    public List<AdminResponse.AvailableTime> getAvailableTime(String shopName, String businessDayFrom, String businessDayTo) {
        LocalDate dateFrom = LocalDate.parse(businessDayFrom);
        LocalDate dateTo = LocalDate.parse(businessDayTo);

        return availableTimeRepository.findAvailableTimeList(shopName, dateFrom, dateTo);
    }

    public ResponseCode deleteAvailableTimeWithReservation(String shopName, String businessTime, String message) {
        RepairShop repairShop;
        try {
            repairShop = repairShopRepository.findRepairShopByShopName(shopName);
        } catch (Exception e) {
            throw new GeneralException(ResponseCode.BAD_REQUEST, "존재하지 않는 지점명입니다.");
        }

        LocalDateTime datetime = CommonDateFormat.datetime(businessTime);
        Date date = Date.valueOf(LocalDate.parse(businessTime.split(" ")[0]));
        Time time = Time.valueOf(LocalTime.parse(businessTime.split(" ")[1]));

        try {
            List<Reservation> reservations = reservationRepository.findReservationsBy(repairShop.getId(), datetime);

            for (Reservation reservation : reservations) {
                AdminRequest.StageInfo stageInfo = new AdminRequest.StageInfo(reservation.getId(), ProgressStage.CANCEL, message);
                aReservationService.saveStage(stageInfo);
            }
        } catch (Exception e) {
            throw new GeneralException(ResponseCode.INTERNAL_SERVER_ERROR, "예약을 취소하는 중 문제가 발생했습니다.");
        }

        Optional<AvailableTime> exactAvailableTime = availableTimeRepository.findExactAvailableTime(repairShop.getId(), date, time);
        if (exactAvailableTime.isEmpty()) {
            throw new GeneralException(ResponseCode.BAD_REQUEST, "이미 삭제된 시간입니다.");
        }

        availableTimeRepository.delete(exactAvailableTime.get());

        return ResponseCode.OK;
    }
}
