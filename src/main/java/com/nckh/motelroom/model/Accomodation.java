package com.nckh.motelroom.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nckh.motelroom.model.enums.ToiletName;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @Column(name = "cabletv")
    private Double cabletv;

    @Column(name = "electric_price")
    private Double electricPrice;

    @Column(name = "heater")
    private Boolean heater;

    @Column(name = "internet")
    private Boolean internet;

    @Column(name = "motel")
    private Boolean motel;

    @Column(name = "parking")
    private Boolean parking;

    @Column(name = "price")
    private Double price;

    @Column(name = "status")
    private Boolean status;

    @Enumerated(EnumType.STRING)
    @Column(name = "toilet")
    private ToiletName toilet;

    @Column(name = "tv")
    private Boolean tv;

    @Column(name = "water_price")
    private Double waterPrice;

    @Column(name = "x_coordinate")
    private Double xCoordinate;

    @Column(name = "y_coordinate")
    private Double yCoordinate;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "district_id")
    @JsonBackReference
    private District district;

    @OneToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    private Post post;
}