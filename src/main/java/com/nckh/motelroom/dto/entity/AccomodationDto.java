package com.nckh.motelroom.dto.entity;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class AccomodationDto {
    private Long id;
    private Double acreage;
    private String address;
    private Boolean airConditioner;
    private Boolean interior;
    private BigDecimal electricPrice;
    private Boolean heater;
    private Boolean internet;
    private String motel;
    private Boolean parking;
    private BigDecimal price;
    private Boolean owner;
    private Boolean toilet;
    private Boolean time;
    private BigDecimal waterPrice;

    private Boolean gender;
    private DistrictDto district;  // Dùng district thay vì idDistrict
    private Boolean kitchen;
    private Boolean security;

    private String openHours;
    private String secondMotel;
    private Boolean delivery;
    private Boolean dineIn;
    private Boolean takeAway;
    private Boolean bigSpace;
    private String linkShopeeFood;
    private String major;
    private String referenceUrl;

}

