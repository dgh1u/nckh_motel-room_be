package com.nckh.motelroom.mapper;

import com.nckh.motelroom.dto.request.UserUpdateUser;
import com.nckh.motelroom.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "password",ignore = true)
    @Mapping(target = "roles",ignore = true)
    User updateUser(UserUpdateUser user);


}
