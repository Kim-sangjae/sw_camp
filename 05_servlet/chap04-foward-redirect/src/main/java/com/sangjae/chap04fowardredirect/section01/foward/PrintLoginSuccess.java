package com.sangjae.chap04fowardredirect.section01.foward;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(value = "/print")
public class PrintLoginSuccess extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // foward 된 servlet 에서도 요청 방식이 그대로 유지 post->post
        // 또한 전달 된 req,resp 의 모든 정보를 이용해 새로운 req,resp 를 깊은복사를 통해 만들어 전달
        // 즉 , 데이터가 그대로 보존

        String userName = (String) req.getAttribute("userName");

        resp.setContentType("text/html");
        PrintWriter printWriter = resp.getWriter();
        printWriter.println("<h1>" + userName + "님 환영합니다.</h1>");

    }
}
