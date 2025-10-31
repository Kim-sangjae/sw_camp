package com.ohgiraffers.springsecurity.auth.service;

import com.ohgiraffers.springsecurity.auth.dto.LoginRequest;
import com.ohgiraffers.springsecurity.auth.dto.TokenResponse;
import com.ohgiraffers.springsecurity.auth.entity.RefreshToken;
import com.ohgiraffers.springsecurity.auth.repository.RefreshTokenRepository;
import com.ohgiraffers.springsecurity.command.entity.User;
import com.ohgiraffers.springsecurity.command.repository.UserRepository;
import com.ohgiraffers.springsecurity.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public TokenResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new BadCredentialsException("올바르지 않은 아이디 혹은 비밀번호"));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("올바르지 않은 아이디 혹은 비밀번호");
        }

        // 로그인 성공 시 token 발급
        String accessToken = jwtTokenProvider.createAccessToken(user.getUsername(), user.getRole().name());
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getUsername(), user.getRole().name());

        // refresh token은 서버 측에서 관리 되어야 하는 데이터이고 성능상 추천 되는 방식은 Redis
        // RDBMS에 저장해서 관리하는 코드로 작성
        RefreshToken tokenEntity = RefreshToken.builder()
                .username(user.getUsername())
                .token(refreshToken)
                .expiryDate(
                        new Date(System.currentTimeMillis()
                        + jwtTokenProvider.getRefreshExpiration())
                )
                .build();

        refreshTokenRepository.save(tokenEntity);

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Transactional
    public TokenResponse refreshToken(String refreshToken) {
        // 리프레시 토큰 유효성 검사
        jwtTokenProvider.validateToken(refreshToken);
        String username = jwtTokenProvider.getUsernameFromJWT(refreshToken);

        // 서버에서 관리하고 있는 refresh token과의 일치성 조회
        RefreshToken storedToken = refreshTokenRepository.findById(username)
                .orElseThrow(()-> new RuntimeException("해당 유저로 조회 되는 리프레시 토큰 없음"));
        if(!storedToken.getToken().equals(refreshToken)) {
            throw new RuntimeException("리프레시 토큰 일치하지 않음");
        }

        // 새로운 토큰 재발급
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("해당 유저 찾지 못함"));

        String newAccessToken = jwtTokenProvider.createAccessToken(user.getUsername(), user.getRole().name());
        String newRefreshToken = jwtTokenProvider.createRefreshToken(user.getUsername(), user.getRole().name());

        RefreshToken tokenEntity = RefreshToken.builder()
                .username(user.getUsername())
                .token(newRefreshToken)
                .expiryDate(
                        new Date(System.currentTimeMillis()
                                + jwtTokenProvider.getRefreshExpiration())
                )
                .build();

        refreshTokenRepository.save(tokenEntity);

        return TokenResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();

    }

    @Transactional
    public void logout(String username) {
        // 서버 측에서 관리하는 refresh token 제거
        refreshTokenRepository.deleteById(username);
    }
}
