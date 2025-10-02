package com.sangjae.chap06filter.section01.filter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(value = "/first/filter")
public class FirstFilterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("servlet do get 호출");
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.println("<h1>필터 확인용 서블리 </h1>");
    }
}
