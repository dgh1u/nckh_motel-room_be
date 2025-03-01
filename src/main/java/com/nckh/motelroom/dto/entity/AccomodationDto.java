package com.nckh.motelroom.dto.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.nckh.motelroom.model.enums.ToiletName;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link com.nckh.motelroom.model.Accomodation}
 */
@Data
public class AccomodationDto implements Serializable {
    private long id;
    // diện tích
    private double acreage;
    // địa chỉ
    private String address;
    // giá điện
    private BigDecimal electricPrice;
    // giá nước
    private BigDecimal waterPrice;
    // có mạng hay không?
    private Boolean internet;
    // có chỗ để xe không?
    private Boolean parking;
    // có điều hòa không
    private Boolean airConditioner;
    // lò sưởi?
    private Boolean heater;
    // Truyền hình cáp?
    private Boolean cableTV;
    // có TV không?
    private Boolean tv;
    // Dạng Toilet? Khép kín hay chung?
    private ToiletName toilet;
    // giá trọ
    private BigDecimal price;
    // trạng thái
    private Boolean status;
    // là nhà trọ?
    private Boolean motel;
    // Tọa độ X
    private Double xCoordinate;
    // Tọa độ Y
    private Double yCoordinate;
    // Mã Quận?
    private Long idDistrict;
}