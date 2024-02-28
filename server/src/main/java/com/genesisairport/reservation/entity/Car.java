package com.genesisairport.reservation.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Entity
@Table(name = "car")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@DynamicInsert
@DynamicUpdate
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

    @Column(name = "create_datetime")
    private LocalDateTime createDatetime;

    @Column(name = "update_datetime")
    private LocalDateTime updateDatetime;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
