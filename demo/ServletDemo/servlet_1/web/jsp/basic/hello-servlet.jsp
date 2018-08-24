<%--
  Created by IntelliJ IDEA.
  User: 01369674
  Date: 2018/5/22
  Time: 10:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Hello</title>
</head>
<body>
    <h1>第一个Servlet小例子</h1>
    <a href="/servlet/hello">get方式请求HelloServlet</a><br/><br/>
    <form action="/servlet/hello" method="post">
        <input type="submit" value="post方式请求HelloServlet"/>
    </form>
</body>
</html>
