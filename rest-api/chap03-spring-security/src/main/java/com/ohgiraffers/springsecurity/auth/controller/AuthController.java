package com.ohgiraffers.springsecurity.auth.controller;

import com.ohgiraffers.springsecurity.auth.dto.LoginRequest;
import com.ohgiraffers.springsecurity.auth.dto.RefreshTokenRequest;
import com.ohgiraffers.springsecurity.auth.dto.TokenResponse;
import com.ohgiraffers.springsecurity.auth.entity.RefreshToken;
import com.ohgiraffers.springsecurity.auth.service.AuthService;
import com.ohgiraffers.springsecurity.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.Token;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<TokenResponse>> login(@RequestBody LoginRequest request) {
        TokenResponse response = authService.login(request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<TokenResponse>> refreshToken(
            @RequestBody RefreshTokenRequest request
    ) {
        TokenResponse response = authService.refreshToken(request.getRefreshToken());
        return ResponseEntity.ok(ApiResponse.success(response));
    }


    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(
            @AuthenticationPrincipal UserDetails userDetails
            ){
        authService.logout(userDetails.getUsername());
        return ResponseEntity.ok(ApiResponse.success(null));
    }







}
