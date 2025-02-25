package com.nckh.motelroom.service.impl;

import com.nckh.motelroom.dto.entity.UserDto;
import com.nckh.motelroom.dto.request.CreateUserRequest;
import com.nckh.motelroom.dto.request.UpdateUserRequest;
import com.nckh.motelroom.exception.DataExistException;
import com.nckh.motelroom.exception.MyCustomException;
import com.nckh.motelroom.mapper.UserMapper;
import com.nckh.motelroom.model.Role;
import com.nckh.motelroom.model.User;
import com.nckh.motelroom.repository.RoleRepository;
import com.nckh.motelroom.repository.UserRepository;
import com.nckh.motelroom.repository.custom.CustomUserQuery;
import com.nckh.motelroom.service.UserService;
import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Page<User> getAllUser(CustomUserQuery.UserFilterParam param, PageRequest pageRequest) {
        Specification<User> specification = CustomUserQuery.getFilterUser(param);
        return userRepository.findAll(specification, pageRequest);
    }

    @Override
    public UserDto selectUserByEmail(String email) {
        Optional<User> userOptional=userRepository.findByEmail(email);
        if(!userOptional.isPresent()){
            throw new DataExistException("Email không tồn tại");
        }
        User user=userOptional.get();
        return userMapper.toUserDto(user);
    }

    @Override
    public UserDto selectUserById(Long id) {
        Optional<User> userOptional=userRepository.findById(id);
        if(!userOptional.isPresent()){
            throw new DataExistException("Người dùng không tồn tại");
        }
        User user=userOptional.get();
        return userMapper.toUserDto(user);
    }

    @Override
    public void changeAvatar(String email, byte[] fileBytes) {
        Optional<User> userOptional=userRepository.findByEmail(email);
        if(!userOptional.isPresent()){
            throw new DataExistException("Email không tồn tại");
        }
        User user=userOptional.get();
        user.setB64(Base64.getEncoder().encodeToString(fileBytes));
        userRepository.saveAndFlush(user);
    }

    @Override
    public UserDto createUser(CreateUserRequest request) {
        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());
        if (userOptional.isPresent()) {
            throw new DataExistException("Email đã tồn tại");
        }
        System.out.println(1);
        User user = userMapper.toCreateUser(request);
        System.out.println(2);
        user.setRole(buildRole(request.getRole()));
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setBlock(false);
        System.out.println(3);
        return userMapper.toUserDto(userRepository.saveAndFlush(user));
    }

    @Override
    public UserDto updateUser(UpdateUserRequest request) {
        Optional<User> userOptional = userRepository.findById(request.getId());
        if (userOptional.isPresent()) {
            throw new DataExistException("Người dùng đã tồn tại");
        }
        User user = userMapper.toUpdateUser(request);
        user.setRole(buildRole(request.getRole()));
        user.setBlock(false);
        return userMapper.toUserDto(userRepository.saveAndFlush(user));
    }

    @Override
    public void deleteUser(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()) {
            throw new DataExistException("Người dùng không tồn tại");
        }
        userRepository.deleteById(id);
    }

    private Role buildRole(String roleId) {
        return roleRepository.findById(roleId).orElseThrow(() -> new RuntimeException("Role không tồn tại!"));
    }
}
