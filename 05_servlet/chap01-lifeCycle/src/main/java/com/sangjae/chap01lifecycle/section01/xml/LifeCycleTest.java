package com.sangjae.chap01lifecycle.section01.xml;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class LifeCycleTest extends HttpServlet {

    public LifeCycleTest() {
        System.out.println("xml 매핑 생성자 호출");
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        System.out.println("xml 매핑 init 메소드 호출");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("xml 매핑 service 메소드 호출");
    }

    @Override
    public void destroy() {
        System.out.println("xml 매핑 destroy 메소드 호출");
    }
}
