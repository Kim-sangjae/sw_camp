package com.sangjae.chap06filter.section02.uses;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;

@WebServlet(value = "/member/regist")
public class RegistMemberServlet extends HttpServlet {


    // 1. Post 전송 시 한글값 문자 셋 미설정으로 인한 깨짐 현상(tomcat 9 이전)
    // 2. 비밀번호 암호화
    // 위의 두가지를 필터로 작성해서 다른서블릿에서도 공통 적용 되게 설정
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("userId");
        String password = req.getParameter("password");
        String name = req.getParameter("name");

        System.out.println("userId : " + userId +"\npassword : " + password + "\nname : " + name);

        // 암호화 된 패스워드는 매번 달라진다
        // 입력 값 동일여부 확인은 matches 메서드를 통해 확인한다

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println("비밀번호가 1234 맞는가? :" + passwordEncoder.matches("1234" , password));

        System.out.println("비밀번호가 4321 맞는가? :" + passwordEncoder.matches("4321" , password));

    }
}
