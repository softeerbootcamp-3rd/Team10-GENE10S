package com.genesisairport.reservation.repository;

import com.genesisairport.reservation.common.util.CommonDateFormat;
import com.genesisairport.reservation.request.AdminRequest;
import com.genesisairport.reservation.response.AdminResponse;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.genesisairport.reservation.entity.QCustomer.customer;
import static com.genesisairport.reservation.entity.QRepairShop.repairShop;
import static com.genesisairport.reservation.entity.QReservation.reservation;

@RequiredArgsConstructor
@Slf4j
public class ReservationRepositoryImpl implements ReservationRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<AdminResponse.ReservationDetail> findReservations(
            AdminRequest.ReservationDetail reservationDetail, Integer pageSize, Integer pageNumber
    ) {

        BooleanBuilder builderForWhereClause = getBuilderForWhereClause(
                reservationDetail.getShopName(), reservationDetail.getStartPickupDateTime(),
                reservationDetail.getEndPickupDateTime(), reservationDetail.getStartReturnDateTime(),
                reservationDetail.getEndReturnDateTime(), reservationDetail.getCustomerName(),
                reservationDetail.getSellName(), reservationDetail.getStage()
        );

        OrderSpecifier<?> orderBySpecifier = getOrderBySpecifier(
                reservationDetail.getSortColumn(), reservationDetail.getSortDirection()
        );

        List<Tuple> tuples = jpaQueryFactory
                .select(
                        reservation.id,
                        repairShop.shopName,
                        customer.name,
                        reservation.sellName,
                        reservation.departureTime,
                        reservation.arrivalTime,
                        reservation.progressStage
                )
                .from(reservation)
                .innerJoin(customer).on(reservation.customer.eq(customer))
                .innerJoin(repairShop).on(reservation.repairShop.eq(repairShop))
                .where(builderForWhereClause)
                .orderBy(orderBySpecifier)
                .fetch();
        return convertToReservationDetailAdmin(tuples);
    }

    private List<AdminResponse.ReservationDetail> convertToReservationDetailAdmin(List<Tuple> tuples) {
        List<AdminResponse.ReservationDetail> reservationDetailAdmins = new ArrayList<>();
        for (Tuple t : tuples) {
            AdminResponse.ReservationDetail reservationDetailAdmin = AdminResponse.ReservationDetail.builder()
                    .reservationId(t.get(0, Long.class))
                    .shopName(t.get(1, String.class))
                    .customerName(t.get(2, String.class))
                    .sellName(t.get(3, String.class))
                    .departureTime(CommonDateFormat.localDatetime(t.get(4, LocalDateTime.class)))
                    .arrivalTime(CommonDateFormat.localDatetime(t.get(5, LocalDateTime.class)))
                    .stage(t.get(6, String.class))
                    .build();
            reservationDetailAdmins.add(reservationDetailAdmin);
        }
        return reservationDetailAdmins;
    }

    private BooleanBuilder getBuilderForWhereClause(String shopName, String startPickupDate, String endPickupDate, String startReturnDate, String endReturnDate, String customerName, String sellName, String stage) {
        BooleanBuilder builderForWhereClause = new BooleanBuilder();

        if (!Strings.isEmpty(shopName)) { // null 이거나 "" 가 아니면
            builderForWhereClause.and(repairShop.shopName.eq(shopName));
        }
        if (!Strings.isEmpty(startPickupDate) && !Strings.isEmpty(endPickupDate)) {
            builderForWhereClause.and(reservation.departureTime.between(CommonDateFormat.localDatetime(startPickupDate), CommonDateFormat.localDatetime(endPickupDate)));
        }
        if (!Strings.isEmpty(startReturnDate) && !Strings.isEmpty(endReturnDate)) {
            builderForWhereClause.and(reservation.arrivalTime.between(CommonDateFormat.localDatetime(startReturnDate), CommonDateFormat.localDatetime(endReturnDate)));
        }
        if (!Strings.isEmpty(customerName)) {
            builderForWhereClause.and(customer.name.eq(customerName));
        }
        if (!Strings.isEmpty(sellName)) {
            builderForWhereClause.and(reservation.sellName.eq(sellName));
        }
        if (!Strings.isEmpty(stage)) {
            builderForWhereClause.and(reservation.progressStage.eq(stage));
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
}
