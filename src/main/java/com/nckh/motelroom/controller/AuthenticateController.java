package com.nckh.motelroom.controller;

import com.nckh.motelroom.dto.request.LoginRequest;
import com.nckh.motelroom.dto.request.RegisterRequest;
import com.nckh.motelroom.dto.response.BaseResponse;
import com.nckh.motelroom.dto.response.LoginResponse;
import com.nckh.motelroom.dto.response.RegisterResponse;
import com.nckh.motelroom.service.AuthenticateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticateController {
    private final AuthenticateService authenticateService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request){
        LoginResponse response=authenticateService.login(request);
        return BaseResponse.successData(response);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request){
        RegisterResponse response=authenticateService.register(request);
        return BaseResponse.successData(response);
    }
}
