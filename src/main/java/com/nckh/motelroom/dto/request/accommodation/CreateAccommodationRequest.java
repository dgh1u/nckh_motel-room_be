package com.nckh.motelroom.dto.request.accommodation;

import com.nckh.motelroom.model.enums.ToiletName;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateAccommodationRequest {
    // diện tích
    private double acreage;
    // địa chỉ
    private String address;
    // giá điện
    private BigDecimal electricPrice;
    // giá nước
    private BigDecimal waterPrice;
    // có mạng hay không?
    private boolean internet;
    // có chỗ để xe không?
    private boolean parking;
    // có điều hòa không
    private boolean airConditioner;
    // lò sưởi?
    private boolean heater;
    // Truyền hình cáp?
    private boolean cableTV;
    // có TV không?
    private boolean tv;
    // Dạng Toilet? Khép kín hay chung?
    private ToiletName toilet;
    // giá trọ
    private BigDecimal price;
    // trạng thái
    private boolean status;
    // là nhà trọ?
    private boolean motel;
    // Tọa độ X
    private double xCoordinate;
    // Tọa độ Y
    private double yCoordinate;
    // Mã Quận?
    private Long idDistrict;
}
