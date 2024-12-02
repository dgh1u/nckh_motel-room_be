package com.nckh.motelroom.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "criteria")
public class Criterion {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "district_id")
    private District district;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "motel")
    private Boolean motel;

    @Column(name = "stop")
    private Boolean stop;

    @Column(name = "price_start")
    private Double priceStart;

    @Column(name = "price_end")
    private Double priceEnd;

    @Column(name = "acreage_start")
    private Double acreageStart;

    @Column(name = "acreage_end")
    private Double acreageEnd;

    @Column(name = "create_at")
    private Instant createAt;

}