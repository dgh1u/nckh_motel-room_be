package com.nckh.motelroom.dto.request;

import com.nckh.motelroom.constant.Constant;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserUpdateUser {
    @NotNull
    private Long id;
    @NotBlank(message = "Họ tên không được để trống")
    private String fullName;
    @NotBlank(message = "Địa chỉ không được để trống")
    private String address;
    @NotBlank(message = "Số điện thoại không được để trống")
    private String phone;

    private boolean block;

    private String b64;

    private String fileType;
}
