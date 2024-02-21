package com.genesisairport.reservation.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;

@Entity
@Table(name = "maintenance_image")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert
public class MaintenanceImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Column(nullable = false)
    @Getter
    private Integer status;

    @Column(name = "image_url", nullable = false, length = 2048)
    @Getter
    private String imageUrl;

    @Column(name = "create_datetime")
    private LocalDateTime createDatetime;

    @Column(name = "update_datetime")
    private LocalDateTime updateDatetime;

    @ManyToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;
}
