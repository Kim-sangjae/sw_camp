package com.sangjae.chap05cookiesession.section01.cookie;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(value = "/cookie-redirect")
public class CookieRedirect extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // redirect 시 이전 req 정보는 존재하지 않고 새로운 req정보가 넘어온다
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");

        System.out.println("firstName :" + firstName + "lastName :" + lastName); // null


        // 저장했던 쿠키 값을 꺼내서 확인
        Cookie[] cookies = req.getCookies();
        for (Cookie cookie : cookies) {
            if(cookie.getName().equals("firstName")){
                firstName = cookie.getValue();
            }else if(cookie.getName().equals("lastName")){
                lastName = cookie.getValue();
            }
        }

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        out.println("<h1 style='margin:0;'>your firstName = " + firstName +"</h1> <br> <h1 style='margin:0;'> lastName = " + lastName + "</h1>");
    }
}
