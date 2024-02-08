package com.genesisairport.reservation.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "reservation")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime departureTime;

    @Column(nullable = false)
    private LocalDateTime arrivalTime;

    @Column(name = "contact_number", nullable = false, length = 20)
    private String contactNumber;

    @Column(name = "sell_name", nullable = false, length = 20)
    private String sellName;

    @Column(name = "plate_number", nullable = false, length = 20)
    private String plateNumber;

    @Column(name = "service_type", nullable = false, length = 500)
    private String serviceType;

    @Column(name = "customer_request", columnDefinition = "TEXT")
    private String customerRequest;

    @Column(name = "progress_stage", nullable = false, length = 20)
    private String progressStage;

    @Column(name = "inspection_result", length = 2000)
    private String inspectionResult;

    @Column(name = "create_datetime", nullable = false)
    private LocalDateTime createDateTime;

    @Column(name = "update_datetime", nullable = false)
    private LocalDateTime updateDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "reservation", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<MaintenanceImage> maintenanceImage = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "repair_shop_id")
    private RepairShop repairShop;
}