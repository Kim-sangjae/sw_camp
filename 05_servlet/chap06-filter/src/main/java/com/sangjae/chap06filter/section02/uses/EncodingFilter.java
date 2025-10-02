package com.sangjae.chap06filter.section02.uses;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;


// 어노테이션으로 간단하게 관리 할 수 도 있지만
// 여기서는 web.xml 를 통해 설정해본다
public class EncodingFilter implements Filter {
    private String encodingType;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        encodingType = filterConfig.getInitParameter("encoding-type");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // POST 방식의 요청이 오면 문자셋을 설정 해주는 전처리 작업
        HttpServletRequest hsr = (HttpServletRequest) servletRequest;
        if("POST".equals(hsr.getMethod())){
            System.out.println("POST 방식의 요청 문자셋 전처리 동작");
            servletRequest.setCharacterEncoding(encodingType);
        }

        // 전처리 된 request 객체를 다음 필터 , 서블릿으로 넘긴다
        filterChain.doFilter(servletRequest,servletResponse);

    }


}
