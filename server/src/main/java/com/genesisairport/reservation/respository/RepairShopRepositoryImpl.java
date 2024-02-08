package com.genesisairport.reservation.respository;

import com.genesisairport.reservation.response.ReservationResponse;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import static com.genesisairport.reservation.entity.QRepairShop.repairShop;
import static com.genesisairport.reservation.entity.QAvailableTime.availableTime;

@RequiredArgsConstructor
public class RepairShopRepositoryImpl implements RepairShopRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public ReservationResponse.DateInfo findAvailableTimes(String shopName) {
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

    private ReservationResponse.DateInfo convertToDto(List<Tuple> tuples) {
        Map<String, Boolean> map = new HashMap<>();

        for (Tuple t : tuples) {
            Date date = t.get(0, Date.class);
            String formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
            int cnt = t.get(2, Integer.class);
            int capacity = t.get(3, Integer.class);

            Boolean available = cnt < capacity ? true : false;

            map.computeIfAbsent(formattedDate, k -> available);
        }


        List<String> availableDates = new ArrayList<>();

        for (Map.Entry<String, Boolean> entry : map.entrySet()) {
            if (entry.getValue()) {
                availableDates.add(entry.getKey());
            }
        }

        Collections.reverse(availableDates);
        return ReservationResponse.DateInfo.builder()
                .availableDates(availableDates)
                .build();
    }
}
