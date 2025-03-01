package com.nckh.motelroom.controller;

import com.nckh.motelroom.config.JwtConfig;
import com.nckh.motelroom.dto.entity.UserDto;
import com.nckh.motelroom.dto.request.GetUserRequest;
import com.nckh.motelroom.dto.response.BaseResponse;
import com.nckh.motelroom.mapper.UserMapper;
import com.nckh.motelroom.model.User;
import com.nckh.motelroom.service.UserService;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.stream.Collectors;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    private final UserMapper userMapper;

    private final JwtConfig jwtConfig;

    @ApiOperation(value = "Lấy thông tin nhiêu tài khoản")
    @GetMapping("")
    public ResponseEntity<?> getAllUser(@Valid @ModelAttribute GetUserRequest request) {
        Page<User> page = userService.getAllUser(request, PageRequest.of(request.getStart(), request.getLimit()));

        return BaseResponse.successListData(page.getContent().stream()
                .map(userMapper::toUserDto)
                .collect(Collectors.toList()), (int) page.getTotalElements());
    }

    @ApiOperation(value = "Lấy avatar của một tài khoản")
    @GetMapping("/{id}/avatar")
    @PreAuthorize("#oauth2.hasAnyScope('read')") // for authenticated request (logged)
    public ResponseEntity<?> getAvatar(@PathVariable("id") Long id) {
        return ResponseEntity.ok(new AbstractMap.SimpleEntry<>("data", userService.selectUserById(id).getB64()));
    }

    @ApiOperation(value = "Upload avatar một tài khoản")
    @PostMapping("/{id}/avatar")
//    @PreAuthorize("#oauth2.hasAnyScope('read')") // for authenticated request (logged)
    public ResponseEntity<?> uploadAvatar(@PathVariable("id") Long id,
                                          @RequestParam("avatar") MultipartFile file) throws IOException {
//        String token = header.substring(7);
//        String email = jwtConfig.getUserIdFromJWT(token);

        UserDto userDto=userService.selectUserById(id);

        userService.changeAvatar(userDto.getEmail(), file.getBytes());
        return ResponseEntity.ok(getAvatar(id));
    }
}
