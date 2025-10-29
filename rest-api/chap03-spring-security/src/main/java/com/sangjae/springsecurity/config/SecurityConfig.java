package com.sangjae.springsecurity.config;

import com.sangjae.springsecurity.jwt.JwtAuthenticationFilter;
import com.sangjae.springsecurity.jwt.JwtTokenProvider;
import com.sangjae.springsecurity.jwt.RestAccessDeniedHandler;
import com.sangjae.springsecurity.jwt.RestAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // 시큐리티 설정을 하기위한 어노테이션
@EnableMethodSecurity // 메서드 레벨의 인증 처리 활성화
@RequiredArgsConstructor
public class SecurityConfig {


    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;
    private final RestAccessDeniedHandler restAccessDeniedHandler;
    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // csrf 처리 비활성화(default가 활성화이므로 작성)
        // 활성화시 매번 요청시 csrf 공격을 받지않기위해
        // csrf 별도의 토큰을 계속 또 주고받아야한다
        http.csrf(AbstractHttpConfigurer::disable)
                // 세션 로그인 비활성 , 토큰 로그인 설정으로 진행
                .sessionManagement(
                        session->session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )
                // 요청 http method, url 기준으로 인증/인가 필요여부 설정
                .authorizeHttpRequests(
                        auth -> auth.requestMatchers(
                                        HttpMethod.POST,"/api/v1/users" ,"/api/v1/auth/login"
                                ).permitAll()
                                .requestMatchers(HttpMethod.GET,"/api/v1/users/me").hasAuthority("USER")
                                .requestMatchers(HttpMethod.GET,"/api/v1/admin/users").hasAuthority("ADMIN")
                )
                // 커스텀 인증 필터 추가(JWT 토큰 사용하여 확인)
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                // 인증 , 인가 실패 시 핸들링
                .exceptionHandling(
                        exception ->
                                // 인증 실패 핸들러
                                exception.authenticationEntryPoint(restAuthenticationEntryPoint)
                                        // 인가 실패 핸들러
                                        .accessDeniedHandler(restAccessDeniedHandler)
                );

        return http.build();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(){
        return new JwtAuthenticationFilter(jwtTokenProvider,userDetailsService);
    }




}

