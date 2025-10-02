package com.sangjae.chap04fowardredirect.section01.foward;

import com.sangjae.chap04fowardredirect.HelloServlet;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(value = "/forward")
public class ReceiveInformation extends HelloServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("userId");
        String password = req.getParameter("password");

        // id, pwd 에 해당하는 user의 정보를 조회 , 반환되는 비즈니스 로직이 수행도었다고
        // 가정하고 'xxx님 환영합니다' 와 같은 메세지를 출력 화면에 응답
        req.setAttribute("userName","홍길동");

        // 비즈니스 로직 수행과 응답화면 분리하기 위해 다른 서블릿으로 위임(foward)
        RequestDispatcher rd = req.getRequestDispatcher("print");
        rd.forward(req,resp);




    }
}
