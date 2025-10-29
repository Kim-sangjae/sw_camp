package com.sangjae.springsecurity.command.service;

import com.sangjae.springsecurity.command.dto.UserCreateRequest;
import com.sangjae.springsecurity.command.entity.User;
import com.sangjae.springsecurity.command.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserCommandService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Transactional
    public void registerUser(UserCreateRequest request) {
        // 중복 회원 체크 로직 등 필요 로직
        User user = modelMapper.map(request,User.class);
        user.setEncodedPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);

    }






}//service
