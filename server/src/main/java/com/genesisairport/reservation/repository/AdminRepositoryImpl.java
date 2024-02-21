package com.genesisairport.reservation.repository;

import com.genesisairport.reservation.common.util.CommonDateFormat;
import com.genesisairport.reservation.entity.Admin;
import com.genesisairport.reservation.request.AdminRequest;
import com.genesisairport.reservation.response.AdminResponse;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.genesisairport.reservation.entity.QAdmin.admin;

public class AdminRepositoryImpl extends QuerydslRepositorySupport implements AdminRepositoryCustom {

    public AdminRepositoryImpl() {
        super(Admin.class);
    }

    @Override
    public Page<AdminResponse.AccountDetail> findAccounts(Pageable pageable, AdminRequest.AccountDetail accountDetail) {
        BooleanBuilder builderForWhereClause = getBuilderForWhereClause(
                accountDetail.getAdminId(),
                accountDetail.getAdminName(),
                accountDetail.getPhoneNumber()
        );

        OrderSpecifier<?> orderSpecifier = getOrderBySpecifier(
                accountDetail.getSortColumn(),
                accountDetail.getSortDirection()
        );

        JPQLQuery<Tuple> query = from(admin)
                .select(
                        admin.id,
                        admin.adminId,
                        admin.adminName,
                        admin.phoneNumber,
                        admin.createDatetime
                )
                .where(builderForWhereClause)
                .orderBy(orderSpecifier);

        List<Tuple> tuples = getQuerydsl().applyPagination(pageable, query).fetch();

        List<AdminResponse.AccountDetail> accountDetailList = convertToAccountDetail(tuples);

        return new PageImpl<>(accountDetailList, pageable, tuples.size());
    }

    private List<AdminResponse.AccountDetail> convertToAccountDetail(List<Tuple> tuples) {
        List<AdminResponse.AccountDetail> accountDetailList = new ArrayList<>();

        for (Tuple t: tuples) {
            AdminResponse.AccountDetail accountDetail = AdminResponse.AccountDetail.builder()
                    .id(t.get(0, Long.class))
                    .adminId(t.get(1, String.class))
                    .adminName(t.get(2, String.class))
                    .phoneNumber(t.get(3, String.class))
                    .createDateTime(CommonDateFormat.datetime(t.get(4, LocalDateTime.class)))
                    .build();
            accountDetailList.add(accountDetail);
        }
        return accountDetailList;
    }

    private BooleanBuilder getBuilderForWhereClause(String adminId, String adminName, String phoneNumber) {
        BooleanBuilder builderForWhereClause = new BooleanBuilder();

        if (!Strings.isEmpty(adminId)) { // null 이거나 "" 가 아니면
            builderForWhereClause.and(admin.adminId.eq(adminId));
        }
        if (!Strings.isEmpty(adminName)) {
            builderForWhereClause.and(admin.adminName.eq(adminName));
        }
        if (!Strings.isEmpty(phoneNumber)) {
            builderForWhereClause.and(admin.phoneNumber.eq(phoneNumber));
        }

        return builderForWhereClause;
    }

    private OrderSpecifier<?> getOrderBySpecifier(String sortColumn, String sortDirection) {
        if (Strings.isEmpty(sortColumn)) {
            return admin.id.asc();
        }

        OrderSpecifier<?> orderBySpecifier;

        switch (sortColumn) {
            case "id":
                orderBySpecifier = sortDirection.equals("asc") ? admin.id.asc() : admin.id.desc();
                break;
            case "createDatetime":
                orderBySpecifier = sortDirection.equals("asc") ? admin.createDatetime.asc() : admin.createDatetime.desc();
                break;
            default:
                orderBySpecifier = admin.id.asc();
                break;
        }
        return orderBySpecifier;
    }
}
