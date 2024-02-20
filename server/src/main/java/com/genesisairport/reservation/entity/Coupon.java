package com.genesisairport.reservation.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "coupon")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "serial_number", nullable = false, length = 30)
    private String serialNumber;

    @Column(name = "expired_date")
    private LocalDate expiredDate;

    @Column(name = "is_used", nullable = false)
    @Setter
    private Boolean isUsed;

    @Column(name = "create_datetime", nullable = false)
    private LocalDateTime createDatetime;

    @Column(name = "update_datetime", nullable = false)
    private LocalDateTime updateDatetime;

    @OneToOne(mappedBy = "coupon", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Reservation reservation;
}
