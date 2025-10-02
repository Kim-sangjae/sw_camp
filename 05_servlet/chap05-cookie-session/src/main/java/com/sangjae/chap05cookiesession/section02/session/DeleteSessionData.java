package com.sangjae.chap05cookiesession.section02.session;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Enumeration;

@WebServlet(value = "/session-delete")
public class DeleteSessionData extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Enumeration<String> keys = session.getAttributeNames();

        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            System.out.println(key + " " + session.getAttribute(key));
        }

        // 세션 데이터 제거방법
        // 1. 설정만료시간이 지나면 세션 자동제거
        // 2. removeAttribute() 로 속성제거
        // 3. invalidate() 로 세션의 모든 데이터 제거(세션 무효화,강제 만료)

        System.out.println("-------------------------------------");

        session.removeAttribute("firstName");

        keys = session.getAttributeNames();

        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            System.out.println(key + " " + session.getAttribute(key));
        }


        System.out.println("-------------------------------------");

        session.invalidate();
        keys = session.getAttributeNames();

        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            System.out.println(key + " " + session.getAttribute(key));
        }

    }

}
