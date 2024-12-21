package com.nckh.motelroom.dto.entity;

import com.nckh.motelroom.model.Criteria;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;

/**
 * DTO for {@link Criteria}
 */
@Data
public class CriteriaDto implements Serializable {
    private Long id;

    private boolean motel;

    private double priceStart;

    private double priceEnd;

    private double acreageStart;

    private double acreageEnd;

    private DistrictDto districtDTO;

    private boolean stop;

    private Instant createAt;
}