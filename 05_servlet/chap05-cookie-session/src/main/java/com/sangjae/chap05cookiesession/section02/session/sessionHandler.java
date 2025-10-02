package com.sangjae.chap05cookiesession.section02.session;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(value = "/session")
public class sessionHandler extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 서버 쪽에서 관리할 수 있는 세션이라는 인스턴스를 통해 상태 유지
        HttpSession session = req.getSession();

        // 세션은 브라우저 당 한개의 고유 아이디 인스턴스를 가진다
        // 브라우저가 종료되면 세션또한 종료되고 이후 다른 브라우저 요청이 들어오면 세션값도 다르다
        // 세션 아이디는 쿠키정보에 JSESSIONID 라는 값에 키값으로 저장되어있음
        System.out.println(session.getId());

        // 세션의 기본 유지 시간은 30분 , 필요에따라 값 변경 가능
        System.out.println(session.getMaxInactiveInterval());
        session.setMaxInactiveInterval(60*10); // 초단위 10분


        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");

        session.setAttribute("firstName",firstName);
        session.setAttribute("lastName",lastName);

        resp.sendRedirect("session-redirect");



    }
}
