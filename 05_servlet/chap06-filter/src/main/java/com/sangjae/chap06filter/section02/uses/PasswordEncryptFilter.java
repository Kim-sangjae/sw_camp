package com.sangjae.chap06filter.section02.uses;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

@WebFilter("/member/*")
public class PasswordEncryptFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        // Member와 관련 된 기능은 해당 필터를 거치게 되고,
        // ServletRequest 를 감싸는 Wrapper 객체로 변경 후 Request 객체를 전달 받게 된다
        // Wrapper 클래스에 재정의 해놓은 메서드로 적용시키기 위한 작업
        System.out.println("request 래퍼클래스로 감싸서 전달");
        RequestWrapper requestWrapper = new RequestWrapper((HttpServletRequest) servletRequest);

        filterChain.doFilter(requestWrapper, servletResponse);
    }
}
