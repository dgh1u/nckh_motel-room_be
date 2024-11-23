package com.nckh.motelroom.dto.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * DTO for {@link com.nckh.motelroom.model.District}
 */
@Data
public class DistrictDto implements Serializable {
    private long id;

    private String name;

    private double xCoordinate;

    private double yCoordinate;
}