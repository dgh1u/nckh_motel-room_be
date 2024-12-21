package com.nckh.motelroom.controller;

import com.nckh.motelroom.dto.entity.UserDto;
import com.nckh.motelroom.dto.request.GetUserRequest;
import com.nckh.motelroom.dto.response.BaseResponse;
import com.nckh.motelroom.mapper.UserMapper;
import com.nckh.motelroom.model.User;
import com.nckh.motelroom.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    private final UserMapper userMapper;
    @GetMapping("")
    public ResponseEntity<?> getAllUser(@Valid @ModelAttribute GetUserRequest request) {
        Page<User> page = userService.getAllUser(request, PageRequest.of(request.getStart(), request.getLimit()));

        return BaseResponse.successListData(page.getContent().stream()
                .map(userMapper::toUserDto)
                .collect(Collectors.toList()), (int) page.getTotalElements());
    }
}
