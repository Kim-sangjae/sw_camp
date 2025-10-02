package com.sangjae.chap06filter.section01.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;

import java.io.IOException;

// @WebFilter : 서블릿 컨테이너에 필터라고 알려준다
// default 값은 value 이고 value에 작성된 url pattern 에 필터가 동작
@WebFilter("/first/*")
public class FirstFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("filter init 출력");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 서블릿 호출 전 req 가공 등의 전처리
        System.out.println("filter doFilter 메서드 호출");


        // 현재 필터에서 다음 필터 또는 서블릿을 호출하는 코드
        filterChain.doFilter(servletRequest,servletResponse);


        // 서블릿 동작 완료 후 resp 가공 등의 후처리
        System.out.println("filter doFilter 메서드 종료");
    }

    @Override
    public void destroy() {
        System.out.println("filter destroy 출력");
    }

}
