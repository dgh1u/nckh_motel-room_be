package com.nckh.motelroom.service.impl;

import com.nckh.motelroom.config.JwtConfig;
import com.nckh.motelroom.config.MailConfig;
import com.nckh.motelroom.dto.request.*;
import com.nckh.motelroom.dto.response.LoginResponse;
import com.nckh.motelroom.dto.response.RegisterResponse;
import com.nckh.motelroom.exception.AuthenticateException;
import com.nckh.motelroom.exception.DataExistException;
import com.nckh.motelroom.exception.DataNotFoundException;
import com.nckh.motelroom.model.Role;
import com.nckh.motelroom.model.User;
import com.nckh.motelroom.repository.RoleRepository;
import com.nckh.motelroom.repository.UserRepository;
import com.nckh.motelroom.service.AuthenticateService;
import com.nckh.motelroom.utils.MailUtil;
import com.nckh.motelroom.utils.OtpUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticateServiceImp implements AuthenticateService {
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtConfig jwtConfig;

    private final AuthenticationManager authenticationManager;

    private final MailUtil mailUtil;
    @Override
        public LoginResponse login(LoginRequest request) {
            Optional<User> userOptional=userRepository.findByEmail(request.getEmail());
            if(!userOptional.isPresent()){
                throw new AuthenticateException("Email hoặc mật khẩu không chính xác");
            }
            User user=userOptional.get();
            if(!passwordEncoder.matches(request.getPassword(),user.getPassword())){
                throw new AuthenticateException("Email hoặc mật khẩu không chính xác");
            }
            if(user.getBlock()){
                throw new AuthenticateException("Xác thực người dùng");
            }
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            Set<String> roles=user.getRoles().stream().map(role -> {
                return role.getRoleName();
            }).collect(Collectors.toSet());

            return LoginResponse.builder()
                    .fullName(user.getFullName())
                    .token(jwtConfig.generateToken(user))
                    .roles(roles)
                    .build();
        }

    @Override
    @Transactional
    public RegisterResponse register(RegisterRequest request) {
        Optional<User> userOptional=userRepository.findByEmail(request.getEmail());
        if(userOptional.isPresent()){
            throw new DataExistException("Email đã tồn tại");
        }
        User user=new User();

        Optional<Role> roleOptional=roleRepository.findByRoleName("User");
        Role role;
        if(!roleOptional.isPresent()){
            role=new Role();
            role.setRoleName("User");
            roleRepository.save(role);
        }else{
            role=roleOptional.get();
        }
        user.setAddress(request.getAddress());
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setBlock(true);
        user.setPhone(request.getPhone());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        String otp = OtpUtil.generateOtp();
        user.setOtp(otp);
        user.setOtpGeneratedTime(Instant.now());

        Set<Role> roles=new HashSet<>();
        roles.add(role);
        user.setRoles(roles);

        mailUtil.sendOtpEmail(user.getEmail(),otp);

        userRepository.save(user);

        return RegisterResponse.builder()
                .email(user.getEmail())
                .fullName(user.getFullName())
                .address(user.getAddress())
                .phone(user.getPhone())
                .build();
    }

    @Override
    public String verifyAccount(VerifyAccountRequest request) {
        String email=request.getEmail();
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (!userOptional.isPresent()) {
            throw new DataNotFoundException("Không tồn tại người dùng có email là: " + email);
        }
        User user = userOptional.get();
        if (user.getOtp().equals(request.getOtp()) &&
                Duration.between(user.getOtpGeneratedTime(), Instant.now()).getSeconds() < (2 * 60)) {
            user.setBlock(false);
            userRepository.save(user);
            return "OTP đã xác thực thành công.";
        } else {
            throw new RuntimeException("Hãy tạo lại OTP và thử lại.");
        }
    }

    @Override
    public String regenerateOTP(RegenerateOtpRequest request) {
        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());
        if (!userOptional.isPresent()) {
            throw new DataNotFoundException("Không tồn tại người dùng có email là: " + request.getEmail());
        }
        String otp = OtpUtil.generateOtp();
        User user = userOptional.get();
        user.setOtp(otp);
        user.setOtpGeneratedTime(Instant.now());
        userRepository.save(user);
        mailUtil.sendOtpEmail(request.getEmail(),otp);
        return "Email đã gửi... Hãy xác thực trong 2 phút";
    }

    @Override
    public String forgotPassword(ForgotPasswordRequest request) {
        String email=request.getEmail();
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (!userOptional.isPresent()) {
            throw new DataNotFoundException("Không tồn tại người dùng có email là: " + email);
        }
        User user = userOptional.get();
        if (user.getOtp().equals(request.getOtp()) &&
                Duration.between(user.getOtpGeneratedTime(), Instant.now()).getSeconds() < (1 * 60)) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));

            userRepository.save(user);
            return "OTP đã xác thực thành công.";
        } else {
            throw new RuntimeException("Hãy tạo lại OTP và thử lại.");
        }
    }
}
