package com.genesisairport.reservation.repository;

import com.genesisairport.reservation.common.util.CommonDateFormat;
import com.genesisairport.reservation.request.AdminRequest;
import com.genesisairport.reservation.response.AdminResponse;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.ComparableExpression;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.genesisairport.reservation.entity.QCustomer.customer;
import static com.genesisairport.reservation.entity.QRepairShop.repairShop;
import static com.genesisairport.reservation.entity.QReservation.reservation;

@Slf4j
@RequiredArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepositoryCustom {

    private final EntityManager entityManager;
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<AdminResponse.ReservationDetail> findReservations(
            AdminRequest.ReservationDetail reservationDetail, Pageable pageable,
            Long count, LocalDateTime startDateTime, LocalDateTime endDateTime
    ) {

        JPAQuery<AdminResponse.ReservationDetail> query = new JPAQuery<>(entityManager);

        BooleanBuilder builderForWhereClause = getBuilderForWhereClause(
                reservationDetail.getShopName(), reservationDetail.getStartPickupDateTime(),
                reservationDetail.getEndPickupDateTime(), reservationDetail.getStartReturnDateTime(),
                reservationDetail.getEndReturnDateTime(), reservationDetail.getCustomerName(),
                reservationDetail.getSellName(), reservationDetail.getStage(),
                pageable.getOffset(), pageable.getPageSize(),
                reservationDetail.getSortColumn(), reservationDetail.getSortDirection(),
                count, startDateTime, endDateTime
        );

        OrderSpecifier<?> orderBySpecifier = getOrderBySpecifier(
                reservationDetail.getSortColumn(), reservationDetail.getSortDirection()
        );

        List<Tuple> tuples = query.select(
                        reservation.id,
                        repairShop.shopName,
                        customer.name,
                        reservation.sellName,
                        reservation.departureTime,
                        reservation.arrivalTime,
                        reservation.progressStage
                )
                .from(reservation)
                .where(builderForWhereClause)
                .orderBy(orderBySpecifier)
                .limit(pageable.getPageSize())
                .fetch();

        List<AdminResponse.ReservationDetail> reservationDetailList = convertToReservationDetailAdmin(tuples);

        return new PageImpl<>(reservationDetailList, pageable, count);
    }

    private List<AdminResponse.ReservationDetail> convertToReservationDetailAdmin(List<Tuple> tuples) {
        List<AdminResponse.ReservationDetail> reservationDetailAdmins = new ArrayList<>();
        for (Tuple t : tuples) {
            AdminResponse.ReservationDetail reservationDetailAdmin = AdminResponse.ReservationDetail.builder()
                    .reservationId(t.get(0, Long.class))
                    .shopName(t.get(1, String.class))
                    .customerName(t.get(2, String.class))
                    .sellName(t.get(3, String.class))
                    .departureTime(CommonDateFormat.localDateTime(t.get(4, LocalDateTime.class)))
                    .arrivalTime(CommonDateFormat.localDateTime(t.get(5, LocalDateTime.class)))
                    .stage(t.get(6, String.class))
                    .build();
            reservationDetailAdmins.add(reservationDetailAdmin);
        }
        return reservationDetailAdmins;
    }

    private BooleanBuilder getBuilderForWhereClause(
            String shopName, String startPickupDate, String endPickupDate, String startReturnDate,
            String endReturnDate, String customerName, String sellName, String stage,
            long offset, int pageSize, String sortColumn, String sortDirection,
            Long count, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        BooleanBuilder builderForWhereClause = new BooleanBuilder();

        if (sortColumn == null && sortDirection == null) {
            builderForWhereClause.and(reservation.id.between(
                            offset,
                            offset + pageSize
                    ));
        } else if (sortColumn.equals("id")) {
            if (sortDirection.equals("asc")) {
                builderForWhereClause.and(reservation.id.between(
                        offset,
                        offset + pageSize
                ));
            }
            else {
                builderForWhereClause.and(reservation.id.between(
                        count - offset - pageSize,
                        count - offset
                ));
            }
        } else if (sortColumn.equals("departureTime")) {
            builderForWhereClause.and(reservation.departureTime.between(
                    startDateTime,
                    endDateTime
            ));
        } else if (sortColumn.equals("arrivalTime")) {
            builderForWhereClause.and(reservation.arrivalTime.between(
                    startDateTime,
                    endDateTime
            ));
        }

        if (!Strings.isEmpty(shopName)) { // null 이거나 "" 가 아니면
            builderForWhereClause.and(repairShop.shopName.contains(shopName));
        }
        if (!Strings.isEmpty(startPickupDate) && !Strings.isEmpty(endPickupDate)) {
            builderForWhereClause.and(reservation.departureTime.between(CommonDateFormat.localDateTime(startPickupDate), CommonDateFormat.localDateTime(endPickupDate)));
        }
        if (!Strings.isEmpty(startReturnDate) && !Strings.isEmpty(endReturnDate)) {
            builderForWhereClause.and(reservation.arrivalTime.between(CommonDateFormat.localDateTime(startReturnDate), CommonDateFormat.localDateTime(endReturnDate)));
        }
        if (!Strings.isEmpty(customerName)) {
            builderForWhereClause.and(customer.name.contains(customerName));
        }
        if (!Strings.isEmpty(sellName)) {
            builderForWhereClause.and(reservation.sellName.contains(sellName));
        }
        if (!Strings.isEmpty(stage)) {
            builderForWhereClause.and(reservation.progressStage.contains(stage));
        }

        return builderForWhereClause;
    }

    private OrderSpecifier<?> getOrderBySpecifier(String sortColumn, String sortDirection) {
        if (Strings.isEmpty(sortColumn)) {
            return reservation.id.asc();
        }

        OrderSpecifier<?> orderBySpecifier;

        switch (sortColumn) {
            case "id":
                orderBySpecifier = sortDirection.equals("asc") ? reservation.id.asc() : reservation.id.desc();
                break;
            case "departureTime":
                orderBySpecifier = sortDirection.equals("asc") ? reservation.departureTime.asc() : reservation.departureTime.desc();
                break;
            case "arrivalTime":
                orderBySpecifier = sortDirection.equals("asc") ? reservation.arrivalTime.asc() : reservation.arrivalTime.desc();
                break;
            default:
                orderBySpecifier = reservation.id.asc();
                break;
        }
        return orderBySpecifier;
    }

    @Override
    public LocalDateTime findNthByTime(boolean fastest, boolean byDepartureTime, long n) {
        ComparableExpression<LocalDateTime> timeExpression = byDepartureTime ? reservation.departureTime : reservation.arrivalTime;

        return jpaQueryFactory
                .select(timeExpression)
                .from(reservation)
                .orderBy(fastest ? timeExpression.asc() : timeExpression.desc())
                .offset(n)
                .limit(1)
                .fetchFirst();

    }


}
