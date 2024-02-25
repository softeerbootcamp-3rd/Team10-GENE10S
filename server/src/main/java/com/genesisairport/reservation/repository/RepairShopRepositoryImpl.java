package com.genesisairport.reservation.repository;

import com.genesisairport.reservation.response.ReservationResponse;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static com.genesisairport.reservation.entity.QAvailableTime.availableTime;
import static com.genesisairport.reservation.entity.QRepairShop.repairShop;

@RequiredArgsConstructor
public class RepairShopRepositoryImpl implements RepairShopRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public ReservationResponse.DateInfo findAvailableDates(String shopName) {
        LocalDate today = LocalDate.now(ZoneId.of("Asia/Seoul"));

        LocalDate twoWeeksLater = today.plusWeeks(2);
        LocalDate twoMonthsLater = today.plusMonths(2);

        List<Tuple> tuples = jpaQueryFactory
                .select(
                    availableTime.reservationDate,
                    availableTime.reservationTime,
                    availableTime.reservationCount,
                    availableTime.repairShop.capacityPerTime
                )
                .from(availableTime)
                .where(availableTime.repairShop.shopName.eq(shopName)
                        .and(availableTime.reservationDate.between(
                                twoWeeksLater, twoMonthsLater
                        )))
                .orderBy(availableTime.reservationTime.asc())
                .fetch();

        return convertToDateInfo(tuples);
    }

    private ReservationResponse.DateInfo convertToDateInfo(List<Tuple> tuples) {
        Set<String> set = new HashSet<>();

        for (Tuple t : tuples) {
            LocalDate date = t.get(0, LocalDate.class);
            String formattedDate = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).toString();
            int cnt = t.get(2, Integer.class);
            int capacity = t.get(3, Integer.class);

            Boolean available = cnt < capacity ? true : false;
            if (available)
                set.add(formattedDate);
        }

        List<String> availableDates = new ArrayList<>(set);
        Collections.reverse(availableDates);
        return ReservationResponse.DateInfo.builder()
                .availableDates(availableDates)
                .build();
    }

    @Override
    public List<ReservationResponse.TimeInfo> findAvailableTimes(String shopName, LocalDate date) {
        List<Tuple> list = jpaQueryFactory
                .select(availableTime.reservationTime,
                        new CaseBuilder()
                                .when(availableTime.reservationCount.lt(repairShop.capacityPerTime))
                                .then(Boolean.TRUE)
                                .otherwise(Boolean.FALSE)
                )
                .from(repairShop)
                .innerJoin(availableTime).on(availableTime.repairShop.eq(repairShop))
                .where(repairShop.shopName.eq(shopName)
                        .and(availableTime.reservationDate.eq(date)))
                .orderBy(availableTime.reservationTime.asc())
                .fetch();

        return list.stream()
                .map(t -> new ReservationResponse.TimeInfo(
                        t.get(0, Time.class).toLocalTime().getHour(),
                        t.get(1, Boolean.class)
                ))
                .collect(Collectors.toList());
    }





}
