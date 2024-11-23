package com.nckh.motelroom.dto.entity;

import com.nckh.motelroom.model.enums.ToiletName;
import lombok.Data;
import java.io.Serializable;

/**
 * DTO for {@link com.nckh.motelroom.model.Accomodation}
 */
@Data
public class AccomodationDto implements Serializable {
    private long id;

    private double acreage;

    private String address;

    private double electricPrice;

    private double waterPrice;

    private boolean internet;

    private boolean parking;

    private boolean airConditioner;

    private boolean heater;

    private boolean cableTV;

    private boolean tv;

    private ToiletName toilet;

    private double price;

    private boolean status;

    private boolean motel;

    private double xCoordinate;

    private double yCoordinate;

    private PostDto postDTO;

    private Long idDistrict;
}