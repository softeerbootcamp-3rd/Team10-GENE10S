package com.genesisairport.reservation.respository;

import com.genesisairport.reservation.response.ReservationResponse;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

import static com.genesisairport.reservation.entity.QRepairShop.repairShop;
import static com.genesisairport.reservation.entity.QAvailableTime.availableTime;

@RequiredArgsConstructor
public class RepairShopRepositoryImpl implements RepairShopRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ReservationResponse.AvailableDate> findAvailableTimes(String shopName) {
        LocalDate today = LocalDate.now(ZoneId.of("Asia/Seoul"));
        LocalDate twoWeeksLater = today.plusWeeks(2);
        LocalDate twoMonthsLater = today.plusMonths(2);

        List<Tuple> tuples = jpaQueryFactory
                .select(
                    availableTime.reservationDate,
                    availableTime.reservationTime,
                    availableTime.reservationCount,
                    repairShop.capacityPerTime
                )
                .from(repairShop)
                .innerJoin(availableTime).on(availableTime.repairShop.eq(repairShop))
                .where(repairShop.shopName.eq(shopName)
                        .and(availableTime.reservationDate
                                .between(Date.valueOf(twoWeeksLater), Date.valueOf(twoMonthsLater))))
                .orderBy(availableTime.reservationTime.asc())
                .fetch();

        return convertToDto(tuples);
    }

    private List<ReservationResponse.AvailableDate> convertToDto(List<Tuple> tuples) {
        Map<String, List<ReservationResponse.TimeInfo>> map = new HashMap<>();

        for (Tuple t : tuples) {
            Date date = t.get(0, Date.class);
            String formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
            Integer hour = t.get(1, Time.class).toLocalTime().getHour();
            int cnt = t.get(2, Integer.class);
            int capacity = t.get(3, Integer.class);

            ReservationResponse.TimeInfo timeInfo = ReservationResponse.TimeInfo.builder()
                    .time(hour)
                    .available(cnt < capacity)
                    .build();

            map.computeIfAbsent(formattedDate, k -> new ArrayList<>()).add(timeInfo);
        }

        List<ReservationResponse.AvailableDate> availableDates = map.entrySet().stream()
                .map(entry -> ReservationResponse.AvailableDate.builder()
                        .date(entry.getKey())
                        .timeSlots(entry.getValue())
                        .build())
                .collect(Collectors.toList());

        Collections.reverse(availableDates);
        return availableDates;
    }
}
