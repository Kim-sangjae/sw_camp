<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>LifeCycleTest</title>
</head>
<body>
<h1>라이프 사이클 테스트</h1>

<a href="xml-lifecycle">XML 설정</a><br>
<a href="annotation-lifecycle">Annotation 설정</a>

<h1 style="border-top-color: black;">Service 메서드의 역할</h1>
<h2>a 태그</h2>
<a href="request-service">서비스 메서드 요청</a>

<h2>form 태그 (get)</h2>
<form action="request-service" method="get">
    <input type="submit" value="get 방식 요청">
</form>

<h2>form 태그 (post)</h2>
<form action="request-service" method="post">
    <input type="submit" value="post 방식 요청">
</form>

</body>
</html>