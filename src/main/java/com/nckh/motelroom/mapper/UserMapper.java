package com.nckh.motelroom.mapper;

import com.nckh.motelroom.dto.entity.UserDto;
import com.nckh.motelroom.dto.request.CreateUserRequest;
import com.nckh.motelroom.dto.request.UpdateUserRequest;
import com.nckh.motelroom.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "password",ignore = true)
    @Mapping(target = "role",ignore = true)
    User toUpdateUser(UpdateUserRequest userRequest);

    @Mapping(target = "password",ignore = true)
    @Mapping(target = "role",ignore = true)
    User toCreateUser(CreateUserRequest userRequest);

//    @Mapping(target = "role",ignore = true)
    UserDto toUserDto(User user);
}
