package com.genesisairport.reservation.repository;

import com.genesisairport.reservation.common.util.CommonDateFormat;
import com.genesisairport.reservation.response.AdminResponse;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.*;

import static com.genesisairport.reservation.entity.QAvailableTime.availableTime;
import static com.genesisairport.reservation.entity.QRepairShop.repairShop;

@RequiredArgsConstructor
public class AvailableTimeRepositoryImpl implements AvailableTimeRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<AdminResponse.AvailableTime> findAvailableTimeList(String shopName, LocalDate dateFrom, LocalDate dateTo) {

        List<Tuple> queryResult = jpaQueryFactory
                .select(availableTime.reservationDate,
                        availableTime.reservationTime)
                .from(availableTime)
                .innerJoin(availableTime).on(availableTime.repairShop.eq(repairShop))
                .where(availableTime.reservationDate.between(Date.valueOf(dateFrom), Date.valueOf(dateTo))
                        .and(repairShop.shopName.eq(shopName)))
                .orderBy(availableTime.reservationTime.asc())
                .fetch();

        return convert(queryResult);
    }

    private List<AdminResponse.AvailableTime> convert(List<Tuple> tuples) {
        List<AdminResponse.AvailableTime> result = new ArrayList<>();
        Map<String, List<String>> dateRange = new HashMap<>();

        for(Tuple tuple : tuples) {
            Date date = tuple.get(0, Date.class);
            Time time = tuple.get(1, Time.class);
            String formattedDate = CommonDateFormat.date(date.toLocalDate());
            String formattedTime = CommonDateFormat.time(time.toLocalTime());

            List<String> times = dateRange.getOrDefault(formattedDate, new ArrayList<>());
            times.add(formattedTime);
            dateRange.put(formattedDate, times);
        }

        List<String> keySet = new ArrayList<>(dateRange.keySet());
        Collections.sort(keySet);

        for(String data : keySet) {
            result.add(new AdminResponse.AvailableTime(data, dateRange.get(data)));
        }

        return result;
    }
}
