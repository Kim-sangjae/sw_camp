package com.sangjae.chap03response.section01.response;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(value = "/response")
public class ResponseTest extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /* 1. 요청을 받아서 처리한다 (ex. req.getParameter)
         * 2. 비즈니스 로직을 호출 (ex. DB연동 및 CRUD 작업)
         * 3. 응답을 처리한다
         *   :resp 객체를 이용 , 동적인 웹(HTML문서) 페이지를 만들고 스트림을 이용해 내보낸다 */

        // 문자열을 이용해 사용자에게 내보내질 페이지 작성
        StringBuilder sb = new StringBuilder();
        sb.append("<html>")
                .append("<head>")
                .append("<title>응답페이지</title>")
                .append("</head>")
                .append("<body>")
                .append("<h1>안녕 Servlet Response</h1>")
                .append("</body>")
                .append("</html>");

        resp.setContentType("text/html"); // 응답 데이터의 Mime type 설정
        resp.setCharacterEncoding("UTF-8"); // 응답 데이터의 문자셋

        PrintWriter printWriter = resp.getWriter();
        printWriter.println(sb);
    }
}
