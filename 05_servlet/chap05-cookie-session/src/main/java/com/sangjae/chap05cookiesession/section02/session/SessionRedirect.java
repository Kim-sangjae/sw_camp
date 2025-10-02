package com.sangjae.chap05cookiesession.section02.session;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(value = "/session-redirect")
public class SessionRedirect extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // redirect 시에도 sessionId 가 같은지 확인
        HttpSession session = req.getSession();
        System.out.println("redirect session :" + session.getId());

        String firstName = (String)session.getAttribute("firstName");
        String lastName = (String)session.getAttribute("lastName");

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        out.println("<h1 style='margin:0;'>your firstName = " + firstName +"</h1> <br> <h1 style='margin:0;'> lastName = " + lastName + "</h1>");
    }
}
