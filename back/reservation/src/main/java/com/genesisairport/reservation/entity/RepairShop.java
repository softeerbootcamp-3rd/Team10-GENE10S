package com.genesisairport.reservation.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class RepairShop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "repair_shop", nullable = false, length = 20)
    private String repairShop;

    @Column(name = "capacity_per_time")
    private Integer capacityPerTime;

    @Column(length = 255)
    private String address;

    @Column(name = "create_datetime", nullable = false)
    private LocalDateTime createDatetime;

    @Column(name = "update_datetime", nullable = false)
    private LocalDateTime updateDatetime;
}
