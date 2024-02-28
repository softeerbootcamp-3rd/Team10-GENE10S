package com.genesisairport.reservation.request;

import com.genesisairport.reservation.common.enums.ProgressStage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AdminRequestTest {

    @Test
    void createReservationDetail_shouldCreateInstanceWithCorrectValues() {
        String shopName = "Shop A";
        String startPickupDateTime = "2024-03-01 09:00:00";
        String endPickupDateTime = "2024-03-01 18:00:00";
        String startReturnDateTime = "2024-03-02 09:00:00";
        String endReturnDateTime = "2024-03-02 18:00:00";
        String customerName = "John Doe";
        String sellName = "Car A";
        String stage = "In Progress";
        String sortColumn = "customerName";
        String sortDirection = "ASC";

        AdminRequest.ReservationDetail reservationDetail = AdminRequest.ReservationDetail.builder()
                .shopName(shopName)
                .startPickupDateTime(startPickupDateTime)
                .endPickupDateTime(endPickupDateTime)
                .startReturnDateTime(startReturnDateTime)
                .endReturnDateTime(endReturnDateTime)
                .customerName(customerName)
                .sellName(sellName)
                .stage(stage)
                .sortColumn(sortColumn)
                .sortDirection(sortDirection)
                .build();

        assertNotNull(reservationDetail);
        assertEquals(shopName, reservationDetail.getShopName());
        assertEquals(startPickupDateTime, reservationDetail.getStartPickupDateTime());
        assertEquals(endPickupDateTime, reservationDetail.getEndPickupDateTime());
        assertEquals(startReturnDateTime, reservationDetail.getStartReturnDateTime());
        assertEquals(endReturnDateTime, reservationDetail.getEndReturnDateTime());
        assertEquals(customerName, reservationDetail.getCustomerName());
        assertEquals(sellName, reservationDetail.getSellName());
        assertEquals(stage, reservationDetail.getStage());
        assertEquals(sortColumn, reservationDetail.getSortColumn());
        assertEquals(sortDirection, reservationDetail.getSortDirection());
    }

    @Test
    void createStageInfo_shouldCreateInstanceWithCorrectValues() {
        Long reservationId = 123L;
        ProgressStage progress = ProgressStage.MAINTENANCE;
        String detail = "Detail of progress";

        AdminRequest.StageInfo stageInfo = AdminRequest.StageInfo.builder()
                .reservationId(reservationId)
                .progress(progress)
                .detail(detail)
                .build();

        assertNotNull(stageInfo);
        assertEquals(reservationId, stageInfo.getReservationId());
        assertEquals(progress, stageInfo.getProgress());
        assertEquals(detail, stageInfo.getDetail());
    }

    @Test
    void createCommentInfo_shouldCreateInstanceWithCorrectValues() {
        Long reservationId = 123L;
        String comment = "This is a comment";

        AdminRequest.CommentInfo commentInfo = AdminRequest.CommentInfo.builder()
                .reservationId(reservationId)
                .comment(comment)
                .build();

        assertNotNull(commentInfo);
        assertEquals(reservationId, commentInfo.getReservationId());
        assertEquals(comment, commentInfo.getComment());
    }

    @Test
    void createLogin_shouldCreateInstanceWithCorrectValues() {
        String adminId = "admin123";
        String adminPwd = "password123";

        AdminRequest.Login login = AdminRequest.Login.builder()
                .adminId(adminId)
                .adminPwd(adminPwd)
                .build();

        assertNotNull(login);
        assertEquals(adminId, login.getAdminId());
        assertEquals(adminPwd, login.getAdminPwd());
    }

    @Test
    void createAccountDetail_shouldCreateInstanceWithCorrectValues() {
        String adminId = "admin123";
        String adminName = "Admin Name";
        String phoneNumber = "1234567890";
        String sortColumn = "adminName";
        String sortDirection = "DESC";

        AdminRequest.AccountDetail accountDetail = AdminRequest.AccountDetail.builder()
                .adminId(adminId)
                .adminName(adminName)
                .phoneNumber(phoneNumber)
                .sortColumn(sortColumn)
                .sortDirection(sortDirection)
                .build();

        assertNotNull(accountDetail);
        assertEquals(adminId, accountDetail.getAdminId());
        assertEquals(adminName, accountDetail.getAdminName());
        assertEquals(phoneNumber, accountDetail.getPhoneNumber());
        assertEquals(sortColumn, accountDetail.getSortColumn());
        assertEquals(sortDirection, accountDetail.getSortDirection());
    }

    @Test
    void createReservationTime_shouldCreateInstanceWithCorrectValues() {
        String shopName = "Shop A";
        String businessDay = "Monday";

        AdminRequest.ReservationTime reservationTime = AdminRequest.ReservationTime.builder()
                .shopName(shopName)
                .businessDay(businessDay)
                .build();

        assertNotNull(reservationTime);
        assertEquals(shopName, reservationTime.getShopName());
        assertEquals(businessDay, reservationTime.getBusinessDay());
    }
}
