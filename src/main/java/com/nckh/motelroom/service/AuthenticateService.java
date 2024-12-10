package com.nckh.motelroom.service;

import com.nckh.motelroom.dto.request.*;
import com.nckh.motelroom.dto.response.LoginResponse;
import com.nckh.motelroom.dto.response.RegisterResponse;

public interface AuthenticateService {
    LoginResponse login(LoginRequest request);

    RegisterResponse register(RegisterRequest request);
    String verifyAccount(VerifyAccountRequest request);

    String regenerateOTP(RegenerateOtpRequest request);

    String forgotPassword(ForgotPasswordRequest request);
}
