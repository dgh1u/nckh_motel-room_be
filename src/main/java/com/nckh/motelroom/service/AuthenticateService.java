package com.nckh.motelroom.service;

import com.nckh.motelroom.dto.request.LoginRequest;
import com.nckh.motelroom.dto.request.RegisterRequest;
import com.nckh.motelroom.dto.response.LoginResponse;
import com.nckh.motelroom.dto.response.RegisterResponse;

public interface AuthenticateService {
    LoginResponse login(LoginRequest request);

    RegisterResponse register(RegisterRequest request);
}
