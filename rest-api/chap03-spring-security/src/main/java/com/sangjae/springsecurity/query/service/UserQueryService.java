package com.sangjae.springsecurity.query.service;

import com.sangjae.springsecurity.query.dto.UserDTO;
import com.sangjae.springsecurity.query.dto.UserDetailResponse;
import com.sangjae.springsecurity.query.dto.UserListResponse;
import com.sangjae.springsecurity.query.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserQueryService {

    private final UserMapper userMapper;

    public UserDetailResponse getUserDetail(String username) {

        UserDTO user = Optional.ofNullable(
                userMapper.findUserByUsername(username)
        ).orElseThrow(()-> new RuntimeException("유저정보 없음"));

        return UserDetailResponse.builder()
                .user(user)
                .build();
    }

    public UserListResponse getAllUsers() {

        List<UserDTO> users = userMapper.findAllUsers();
        return UserListResponse.builder().users(users).build();

    }
}
