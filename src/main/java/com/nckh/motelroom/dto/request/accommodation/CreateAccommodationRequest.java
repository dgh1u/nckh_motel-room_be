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
    private Boolean internet;

    // Có chỗ để xe không? (bắt buộc)
    private Boolean parking;

    // Có điều hòa không (bắt buộc)
    private Boolean airConditioner;

    // Có bình nóng lạnh? (bắt buộc)
    private Boolean heater;

    // Dạng Toilet? Khép kín hay chung? (bắt buộc)
    private Boolean toilet;

    // Giá trọ (phải lớn hơn 0)
    @DecimalMin(value = "0.0", inclusive = false, message = "Giá phải lớn hơn 0")
    private BigDecimal price;

    // Loại hình nhà trọ (bắt buộc)
    @NotNull(message = "Thông tin về loại hình là bắt buộc")
    private String motel;

    // Có nội thất không? (bắt buộc)
    private Boolean interior;

    // Chủ nhà có ở cùng không?
    private Boolean owner;

    // Giờ giấc tự do?
    private Boolean time;

    // Giới tính ưu tiên (true: Nam, false: Nữ, null: Không yêu cầu)
    private Boolean gender;



    // Mã Quận (không được để trống)
    @NotNull(message = "Mã quận là bắt buộc")
    private Long idDistrict;

    private Boolean kitchen;

    private Boolean security;

    // Giờ mở cửa
    private String openHours;

    // Loại cơ sở lưu trú thứ hai
    private String secondMotel;

    // Có giao hàng không?
    private Boolean delivery;

    // Có dịch vụ ăn tại chỗ không?
    private Boolean dineIn;

    // Có dịch vụ mang đi không?
    private Boolean takeAway;

    // Có không gian rộng không?
    private Boolean bigSpace;

    //link ShopeeFood
    private String linkShopeeFood;

    //Chuyên ngành
    private String major;

    //Link tham khảo
    private String referenceUrl;


}
