package com.sangjae.springsecurity.command.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class UserCreateRequest {

    private final String username;
    private final String password;
    // 추가로 회원 가입 시 필요한 데이터

}
