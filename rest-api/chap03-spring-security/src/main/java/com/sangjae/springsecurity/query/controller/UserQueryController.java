package com.sangjae.springsecurity.query.controller;

import com.sangjae.springsecurity.common.ApiResponse;
import com.sangjae.springsecurity.query.dto.UserDetailResponse;
import com.sangjae.springsecurity.query.dto.UserListResponse;
import com.sangjae.springsecurity.query.service.UserQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserQueryController {

    private final UserQueryService userQueryService;

    @GetMapping("/users/me")
    public ResponseEntity<ApiResponse<UserDetailResponse>> getUserDetail(
            // 인증 필터를 거치고 나면 Spring Security Context에 인증 객체가 저장되어있다.
            // 필요하다면 UserDetails <- Users <- CustomUser 의 형태로 상속 해서
            // id , pwd , authorities 외의 정보도 담아서 사용할 수 있다.
            @AuthenticationPrincipal UserDetails userDetails
            ){
        UserDetailResponse response = userQueryService.getUserDetail(userDetails.getUsername());
        return ResponseEntity.ok(ApiResponse.success(response));
    }
    // 간단한 URL 패턴보다 복잡한 비즈니스 로직이나 메서드 단위의 세밀한 보안 제어가 필요한 경우
    // @PreAuthorize() : 메서드 호출 전 조건 평가
    // @PostAuthorize() : 메서드 호출 후 변환결과에 기반한 처리 가능
    @GetMapping("/admin/users")
    public ResponseEntity<ApiResponse<UserListResponse>> getUsers(){
        UserListResponse response = userQueryService.getAllUsers();
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
