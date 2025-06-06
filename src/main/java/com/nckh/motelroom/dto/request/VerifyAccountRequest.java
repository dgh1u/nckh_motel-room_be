package com.nckh.motelroom.dto.request;

import com.nckh.motelroom.constant.Constant;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class VerifyAccountRequest {
    @Email(regexp = ".+[@].+[\\.].+",message = Constant.ErrMessageUserValidation.EMAIL_VALIDATE)
    @NotBlank(message = Constant.ErrMessageUserValidation.EMAIL_NOT_BLANK)
    private String email;

    @Size(min = 6,max=15, message = Constant.ErrMessageUserValidation.OTP_NOT_BLANK)
    private String otp;
}