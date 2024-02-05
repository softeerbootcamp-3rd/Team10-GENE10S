package com.genesisairport.reservation.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "reservation")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime from;

    @Column(nullable = false)
    private LocalDateTime to;

    @Column(name = "contact_number", nullable = false, length = 20)
    private String contactNumber;

    @Column(name = "sell_name", nullable = false, length = 20)
    private String sellName;

    @Column(name = "plate_number", nullable = false, length = 20)
    private String plateNumber;

    @Column(name = "service_type", nullable = false, length = 200)
    private String serviceType;

    @Column(name = "customer_request", columnDefinition = "TEXT")
    private String customerRequest;

    @Column(name = "progress_stage", nullable = false, length = 20)
    private String progressStage;

    @Column(name = "inspection_result", columnDefinition = "TEXT")
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

    @OneToOne
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;
}