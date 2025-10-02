package com.sangjae.chap01lifecycle.section02.annotation;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.sangjae.chap01lifecycle.HelloServlet;

import java.io.IOException;
// 서블릿을 서블릿 컨테이너에 등록
// value : 등록 된 서블릿을 주소와 매핑
@WebServlet(value = "/annotation-lifecycle", loadOnStartup = 1)
public class LifeCycleTest extends HelloServlet {
    public LifeCycleTest() {
        System.out.println("annotation 매핑 생성자 호출");
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        System.out.println("annotation 매핑 init 메소드 호출");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("annotation 매핑 service 메소드 호출");
    }

    @Override
    public void destroy() {
        System.out.println("annotation 매핑 destroy 메소드 호출");
    }
}
