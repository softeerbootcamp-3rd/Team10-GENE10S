package com.genesisairport.reservation.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "maintenance_image")
public class MaintenanceImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer status;

    @Column(name = "image_url", nullable = false, length = 2048)
    private String imageUrl;

    @Column(name = "create_datetime", nullable = false)
    private LocalDateTime createDatetime;

    @Column(name = "update_datetime", nullable = false)
    private LocalDateTime updateDatetime;
}
