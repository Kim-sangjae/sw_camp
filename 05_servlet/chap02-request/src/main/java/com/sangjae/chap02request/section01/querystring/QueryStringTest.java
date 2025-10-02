package com.sangjae.chap02request.section01.querystring;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;

@WebServlet(value = "/querystring")
public class QueryStringTest extends HttpServlet {
    // 서블릿이 매핑 된 주소로 GET방식 요청이 발생하면 doGet 메서드가 호출된다
    // doGet 메서드를 오버라이드 해서 사용


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        System.out.println("do get");
        // 요청에 넘어온 데이터는 hashmap으로 관리된다
        // key값을 통해 name을 전달하면 value 값을 반환 받을수있다
        // 반환 타입은 String
        String name = req.getParameter("name");
        int age = Integer.parseInt(req.getParameter("age"));
        java.sql.Date birthday = java.sql.Date.valueOf(req.getParameter("birthday"));
        String gender = req.getParameter("gender");
        String national = req.getParameter("national");

        String[] hobbies = req.getParameterValues("hobbies");

        System.out.println(name);
        System.out.println(age);
        System.out.println(birthday);
        System.out.println(gender);
        System.out.println(national);
        System.out.println(Arrays.toString(hobbies));
    }
}
