package com.genesisairport.reservation.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "car")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vin", length = 50)
    private String vin;

    @Column(name = "sell_name", length = 20)
    private String sellName;

    @Column(name = "plate_number", length = 20)
    private String plateNumber;

    @Column(name = "create_datetime", nullable = false)
    private LocalDateTime createDatetime;

    @Column(name = "update_datetime", nullable = false)
    private LocalDateTime updateDatetime;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
