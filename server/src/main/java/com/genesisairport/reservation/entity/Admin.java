package com.genesisairport.reservation.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;

@Entity
@Table(name = "admin")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@DynamicInsert
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "admin_id", nullable = false, length = 320)
    private String adminId;

    @Column(name = "admin_name", nullable = false, length = 20)
    private String adminName;

    @Column(name = "admin_password", nullable = false, columnDefinition = "TEXT")
    private String adminPassword;

    @Column(name = "phone_number", nullable = false, length = 20)
    private String phoneNumber;

    @Column(name = "create_datetime")
    private LocalDateTime createDatetime;

    @Column(name = "update_datetime")
    private LocalDateTime updateDatetime;

}
