package com.nckh.motelroom.service.impl;

import com.nckh.motelroom.config.JwtConfig;
import com.nckh.motelroom.dto.request.LoginRequest;
import com.nckh.motelroom.dto.request.RegisterRequest;
import com.nckh.motelroom.dto.response.LoginResponse;
import com.nckh.motelroom.dto.response.RegisterResponse;
import com.nckh.motelroom.exception.AuthenticateException;
import com.nckh.motelroom.exception.DataExistException;
import com.nckh.motelroom.model.Role;
import com.nckh.motelroom.model.User;
import com.nckh.motelroom.repository.RoleRepository;
import com.nckh.motelroom.repository.UserRepository;
import com.nckh.motelroom.service.AuthenticateService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthenticateServiceImp implements AuthenticateService {
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtConfig jwtConfig;

    private final AuthenticationManager authenticationManager;
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
            if(!user.getBlock()){
                throw new AuthenticateException("Email hoặc mật khẩu không chính xác");
            }
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            return LoginResponse.builder()
                    .fullName(user.getFullName())
                    .token(jwtConfig.generateToken(user))
                    .build();
        }

    @Override
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

        Set<Role> roles=new HashSet<>();
        roles.add(role);
        user.setRoles(roles);

        userRepository.save(user);

        return RegisterResponse.builder()
                .email(user.getEmail())
                .fullName(user.getFullName())
                .address(user.getAddress())
                .phone(user.getPhone())
                .build();
    }
}
