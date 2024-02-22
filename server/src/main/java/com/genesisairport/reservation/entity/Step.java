package com.genesisairport.reservation.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "step")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert
public class Step {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Column(nullable = false, length = 20)
    @Getter
    private String stage;

    @Column(nullable = false)
    @Getter
    private LocalDateTime date;

    @Column(length = 2000)
    @Getter
    private String detail;

    @Column(name = "create_datetime")
    private LocalDateTime createDatetime;

    @Column(name = "update_datetime")
    private LocalDateTime updateDatetime;

    @ManyToOne
    @JoinColumn(name = "reservation_id")
    @Getter
    private Reservation reservation;
}
