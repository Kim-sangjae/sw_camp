package com.sangjae.chap06filter.section02.uses;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class RequestWrapper extends HttpServletRequestWrapper {

    public RequestWrapper(HttpServletRequest request) {
        super(request);
    }

    // 비밀번호 관련된 속성 파라미터를 꺼낼 때 암호화를 적용시킬 메서드
    @Override
    public String getParameter(String name) {
        String value = "";
        if("password".equals(name)){
            // BCrypto : 스프링 시큐리티에서 제공하는 암호화 모듈 -> 디펜던시 추가
            // 시큐리티 쓰기위해 의존 관계에있는 commons logging 도 추가해주기
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            value = passwordEncoder.encode(super.getParameter(name));
        }else {
            value = super.getParameter(name);
        }
        return value;
    }


}
