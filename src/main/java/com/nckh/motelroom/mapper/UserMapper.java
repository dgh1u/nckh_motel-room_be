package com.nckh.motelroom.mapper;

import com.nckh.motelroom.dto.entity.UserDto;
import com.nckh.motelroom.dto.request.UserUpdateUser;
import com.nckh.motelroom.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "password",ignore = true)
    @Mapping(target = "roles",ignore = true)
    User updateUser(UserUpdateUser user);

    UserDto toUserDto(User user);
    User toUser(User user);
}
