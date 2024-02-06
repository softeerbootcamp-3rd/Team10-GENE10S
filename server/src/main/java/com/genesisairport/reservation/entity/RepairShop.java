package com.genesisairport.reservation.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "repair_shop")
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    @OneToMany(mappedBy = "repairShop", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Reservation> reservation = new ArrayList<>();

    @OneToMany(mappedBy = "repairShop", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<AvailableTime> availableTime = new ArrayList<>();
}
