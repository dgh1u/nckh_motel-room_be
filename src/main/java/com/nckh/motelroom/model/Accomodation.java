package com.nckh.motelroom.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;

@ToString
@Getter
@Setter
@Entity
@Table(name = "accomodation")
public class Accomodation {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "acreage")
    private Double acreage;

    @Column(name = "address")
    private String address;

    @Column(name = "air_conditioner")
    private Boolean airConditioner;

    @Column(name = "interior")
    private Boolean interior;

    @Column(name = "electric_price")
    private BigDecimal electricPrice;

    @Column(name = "heater")
    private Boolean heater;

    @Column(name = "internet")
    private Boolean internet;

    @Column(name = "motel")
    private String motel;

    @Column(name = "parking")
    private Boolean parking;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "owner")
    private Boolean owner;

    @Column(name = "toilet")
    private Boolean toilet;

    @Column(name = "time")
    private Boolean time;

    @Column(name = "water_price")
    private BigDecimal waterPrice;



    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "district_id")
    @JsonBackReference
    private District district;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    private Post post;

    @Column(name = "gender")
    private Boolean gender;

    @Column(name = "kitchen")
    private Boolean kitchen;

    @Column(name = "security")
    private Boolean security;

    @Column(name="second_motel")
    private String secondMotel;

    @Column(name = "open_hours")
    private String openHours;

    @Column(name="delivery")
    private Boolean delivery;

    @Column(name="dine_in")
    private Boolean dineIn;

    @Column(name="take_away")
    private Boolean takeAway;

    @Column(name="big_space")
    private Boolean bigSpace;

    @Column(name="link_shopee_food")
    private String linkShopeeFood;
}