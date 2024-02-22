package com.genesisairport.reservation.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;

@Entity
@Table(name = "available_time")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert
public class AvailableTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reservation_date", nullable = false)
    private Date reservationDate;

    @Column(name = "reservation_time", nullable = false)
    private Time reservationTime;

    @Column(name = "reservation_count", nullable = false)
    @Getter
    private Integer reservationCount;

    @Column(name = "create_datetime")
    private LocalDateTime createDatetime;

    @Column(name = "update_datetime")
    private LocalDateTime updateDatetime;

    @ManyToOne
    @JoinColumn(name = "repair_shop_id")
    private RepairShop repairShop;
}
