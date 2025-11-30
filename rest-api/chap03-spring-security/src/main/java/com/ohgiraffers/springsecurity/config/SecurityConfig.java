package com.ohgiraffers.springsecurity.config;

import com.ohgiraffers.springsecurity.jwt.JwtAuthenticationFilter;
import com.ohgiraffers.springsecurity.jwt.JwtTokenProvider;
import com.ohgiraffers.springsecurity.jwt.RestAccessDeniedHandler;
import com.ohgiraffers.springsecurity.jwt.RestAuthenticationEntryPoint;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity       // 메소드 레벨의 인증 처리 활성화
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;
    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    private final RestAccessDeniedHandler restAccessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
        // CORS 설정 활성화
        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
        // CSRF 처리 비활성화 (default가 활성화이므로 작성)
        .csrf(AbstractHttpConfigurer::disable)
        // 세션 로그인 X -> 토큰 로그인 설정으로 진행
        .sessionManagement(
                session -> session.sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS
                )
        )
        // 요청 http method, url 기준으로 인증/인가 필요 여부 설정
        .authorizeHttpRequests(
                auth -> auth.requestMatchers(
                        HttpMethod.POST, "/api/v1/users", "/api/v1/auth/login", "/api/v1/auth/refresh"
                ).permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/users/me").hasAuthority("USER")
//                .requestMatchers(HttpMethod.GET, "/api/v1/admin/users").hasAuthority("ADMIN")
                // authorizeHttpRequests를 한 번이라도 쓰면, 스프링 시큐리티는 명시한 매처들만 규칙을 갖고
                // 나머지(매치 안 된 모든 요청) 에 대해서는 기본값이 denyAll(거부) 되기 때문에 마지막에 추가해야 함
                .anyRequest().authenticated()
        )
        // 커스텀 인증 필터 추가 (JWT 토큰 사용하여 확인)
        .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
        // 인증, 인가 실패 시 핸들링
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
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtTokenProvider, userDetailsService);
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        // Vue 개발 서버 origin
        config.setAllowedOrigins(List.of("http://localhost:5173"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        config.setAllowCredentials(true);       // 쿠키(RefreshToken) 허용
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // 모든 경로에 위 설정 적용
        source.registerCorsConfiguration("/**", config);
        return source;
    }


}
