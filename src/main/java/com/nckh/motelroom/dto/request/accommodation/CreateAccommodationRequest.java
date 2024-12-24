package com.nckh.motelroom.dto.request.accommodation;

import lombok.Data;

@Data
public class CreateAccommodationRequest {
    private float acreage;
    private String address;
    private Boolean airConditioner;
    private Double cabletv;
    private Double electricPrice;
    private Boolean heater;
    private Boolean internet;
    private Boolean motel;
    private Boolean parking;
    private Double price;
    private Boolean status;
    private String toilet;
    private Boolean tv;
    private Double waterPrice;
    private Double xCoordinate;
    private Double yCoordinate;
}
