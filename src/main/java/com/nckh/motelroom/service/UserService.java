package com.nckh.motelroom.service;

import com.nckh.motelroom.dto.entity.UserDto;
import com.nckh.motelroom.dto.request.CreateUserRequest;
import com.nckh.motelroom.dto.request.UpdateUserRequest;
import com.nckh.motelroom.model.User;
import com.nckh.motelroom.repository.custom.CustomUserQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface UserService {
    Page<User> getAllUser(CustomUserQuery.UserFilterParam param, PageRequest pageRequest);
    UserDto selectUserByEmail(String email);
    UserDto selectUserById(Long id);
    void changeAvatar(String email, byte[] fileBytes);

    UserDto createUser(CreateUserRequest request);
    UserDto updateUser(UpdateUserRequest request);

    void deleteUser(Long id);
}
