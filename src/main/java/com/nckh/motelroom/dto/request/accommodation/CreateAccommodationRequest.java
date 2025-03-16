package com.nckh.motelroom.dto.request.accommodation;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateAccommodationRequest {
    // Diện tích (phải lớn hơn 0)
    @Min(value = 0, message = "Diện tích phải lớn hơn hoặc bằng 0")
    private double acreage;

    // Địa chỉ (không được để trống)
    @NotBlank(message = "Địa chỉ không thể để trống")
    private String address;

    // Giá điện (phải lớn hơn 0)
    @DecimalMin(value = "0.0", inclusive = false, message = "Giá điện phải lớn hơn 0")
    private BigDecimal electricPrice;

    // Giá nước (phải lớn hơn 0)
    @DecimalMin(value = "0.0", inclusive = false, message = "Giá nước phải lớn hơn 0")
    private BigDecimal waterPrice;

    // Có mạng hay không? (bắt buộc)
    @NotNull(message = "Thông tin về internet là bắt buộc")
    private Boolean internet;

    // Có chỗ để xe không? (bắt buộc)
    @NotNull(message = "Thông tin về chỗ để xe là bắt buộc")
    private Boolean parking;

    // Có điều hòa không (bắt buộc)
    @NotNull(message = "Thông tin về điều hòa là bắt buộc")
    private Boolean airConditioner;

    // Lò sưởi? (bắt buộc)
    @NotNull(message = "Thông tin về lò sưởi là bắt buộc")
    private Boolean heater;

    // Dạng Toilet? Khép kín hay chung? (bắt buộc)
    @NotNull(message = "Thông tin về loại toilet là bắt buộc")
    private Boolean toilet;

    // Giá trọ (phải lớn hơn 0)
    @DecimalMin(value = "0.0", inclusive = false, message = "Giá phải lớn hơn 0")
    private BigDecimal price;

    // Loại hình nhà trọ (bắt buộc)
    @NotNull(message = "Thông tin về loại cơ sở lưu trú là bắt buộc")
    private String motel;

    // Có nội thất không? (bắt buộc)
    @NotNull(message = "Thông tin về nội thất là bắt buộc")
    private Boolean interior;

    // Chủ nhà có ở cùng không?
    @NotNull(message = "Thông tin về chủ nhà ở cùng là bắt buộc")
    private Boolean owner;

    // Giờ giấc tự do?
    @NotNull(message = "Thông tin về giờ giấc là bắt buộc")
    private Boolean time;

    // Giới tính ưu tiên (true: Nam, false: Nữ, null: Không yêu cầu)
    private Boolean gender;

    // Tọa độ X (phải lớn hơn 0)
    @Min(value = 0, message = "Tọa độ X phải lớn hơn hoặc bằng 0")
    private double xCoordinate;

    // Tọa độ Y (phải lớn hơn 0)
    @Min(value = 0, message = "Tọa độ Y phải lớn hơn hoặc bằng 0")
    private double yCoordinate;

    // Mã Quận (không được để trống)
    @NotNull(message = "Mã quận là bắt buộc")
    private Long idDistrict;

    @NotNull(message = "kitchen là bắt buộc")
    private Boolean kitchen;

    @NotNull(message = "security là bắt buộc")
    private Boolean security;
}
