package com.genesisairport.reservation.entity;

import com.genesisairport.reservation.entity.Admin;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AdminTest {

    @Test
    void createAdmin_shouldCreateInstanceWithCorrectValues() {
        Long id = 1L;
        String adminId = "admin123";
        String adminName = "Admin";
        String adminPassword = "password123";
        String phoneNumber = "123-456-7890";
        LocalDateTime createDatetime = LocalDateTime.now();
        LocalDateTime updateDatetime = LocalDateTime.now();

        Admin admin = Admin.builder()
                .id(id)
                .adminId(adminId)
                .adminName(adminName)
                .adminPassword(adminPassword)
                .phoneNumber(phoneNumber)
                .createDatetime(createDatetime)
                .updateDatetime(updateDatetime)
                .build();

        assertNotNull(admin);
        assertEquals(id, admin.getId());
        assertEquals(adminId, admin.getAdminId());
        assertEquals(adminName, admin.getAdminName());
        assertEquals(adminPassword, admin.getAdminPassword());
        assertEquals(phoneNumber, admin.getPhoneNumber());
        assertEquals(createDatetime, admin.getCreateDatetime());
        assertEquals(updateDatetime, admin.getUpdateDatetime());
    }
}
