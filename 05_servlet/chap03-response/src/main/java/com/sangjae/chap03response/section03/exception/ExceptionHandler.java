package com.sangjae.chap03response.section03.exception;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

// Custom Error Page 응답하는 서블릿
@WebServlet(value = "/showErrorPage")
public class ExceptionHandler extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html");

        Enumeration<String> attrs = req.getAttributeNames();
        while(attrs.hasMoreElements()) {
            String attr = attrs.nextElement();
            System.out.println(attr + " : " + req.getAttribute(attr));
        }

        int statusCode = (int)req.getAttribute("jakarta.servlet.error.status_code");
        String message = (String)req.getAttribute("jakarta.servlet.error.message");
        String servletName = (String)req.getAttribute("jakarta.servlet.error.servlet_name");


        StringBuilder sb = new StringBuilder();
        sb.append("<html>")
                .append("<head>")
                .append("<title>에러페이지</title>")
                .append("</head>")
                .append("<body>")
                .append("<h1>")
                .append(statusCode)
                .append("<h1></h1>")
                .append(message)
                .append("<h1></h1>")
                .append(servletName)
                .append("</h1>")
                .append("</body>")
                .append("</html>");

        PrintWriter printWriter = resp.getWriter();
        printWriter.println(sb);
    }
}
