package com.nckh.motelroom.service;

import com.nckh.motelroom.model.User;
import com.nckh.motelroom.repository.custom.CustomUserQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface UserService {
    public Page<User> getAllUser(CustomUserQuery.UserFilterParam param, PageRequest pageRequest);
}
