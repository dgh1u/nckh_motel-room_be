package com.nckh.motelroom.dto.request;

import com.nckh.motelroom.constant.Constant;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserUpdateUser {
    @NotNull
    private Long id;

    private String fullName;

    private String address;

    private String phone;

    private boolean block;

    private String b64;

    private String fileType;
}
