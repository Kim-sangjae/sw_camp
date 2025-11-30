package com.ohgiraffers.springsecurity.auth.controller;

import com.ohgiraffers.springsecurity.auth.dto.LoginRequest;
import com.ohgiraffers.springsecurity.auth.dto.TokenResponse;
import com.ohgiraffers.springsecurity.auth.service.AuthService;
import com.ohgiraffers.springsecurity.common.ApiResponse;
import com.ohgiraffers.springsecurity.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    /*
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
    ) {
        authService.logout(userDetails.getUsername());
        return ResponseEntity.ok(ApiResponse.success(null));
    }
    */
    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;
    private static final String COOKIE_NAME = "refreshToken";

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<TokenResponse>> login(
            @RequestBody LoginRequest request
    ) {

        TokenResponse tokenResponse = authService.login(request);

        // refreshToken을 HttpOnly 쿠키로 설정
        ResponseCookie refreshCookie = ResponseCookie.from(COOKIE_NAME, tokenResponse.getRefreshToken())
                .httpOnly(true)
                .secure(false)   // 개발 환경에서는 false, 운영에서는 true
                .path("/")
                .maxAge(jwtTokenProvider.getRefreshExpiration() / 1000)
                .sameSite("Lax")
                .build();

        // body에는 accessToken만 내려줌
        TokenResponse body = TokenResponse.builder()
                .accessToken(tokenResponse.getAccessToken())
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .body(ApiResponse.success(body));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<TokenResponse>> refreshToken(
            @CookieValue(name = COOKIE_NAME, required = false) String refreshToken
    ) {
        if (refreshToken == null || refreshToken.isBlank()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.failure("AUTH-001", "Refresh token is missing"));
        }

        TokenResponse tokenResponse = authService.refreshToken(refreshToken);

        // 새 refreshToken을 쿠키에 다시 설정 (회전)
        ResponseCookie refreshCookie = ResponseCookie.from(COOKIE_NAME, tokenResponse.getRefreshToken())
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(jwtTokenProvider.getRefreshExpiration() / 1000)
                .sameSite("Lax")
                .build();

        // body에는 accessToken만
        TokenResponse body = TokenResponse.builder()
                .accessToken(tokenResponse.getAccessToken())
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .body(ApiResponse.success(body));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        authService.logout(userDetails.getUsername());

        // refreshToken 쿠키 삭제
        ResponseCookie deleteCookie = ResponseCookie.from(COOKIE_NAME, "")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(0)     // 즉시 만료
                .sameSite("Lax")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, deleteCookie.toString())
                .body(ApiResponse.success(null));
    }

}
