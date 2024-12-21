package com.nckh.motelroom.service.impl;

import com.nckh.motelroom.repository.UserRepository;
import com.nckh.motelroom.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {
    private final UserRepository userRepository;
}
