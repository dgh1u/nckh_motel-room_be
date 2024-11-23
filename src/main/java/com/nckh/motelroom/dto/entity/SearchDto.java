package com.nckh.motelroom.dto.entity;

import lombok.Data;

@Data
public class SearchDto {
    private double acreageStart;

    private double acreageEnd;

    private double priceStart;

    private double priceEnd;

    private int motel;

    private long idDistrict;

    private double xCoordinate;

    private double yCoordinate;

    private double radius;
}
