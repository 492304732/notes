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
    hello,
    <%
        out.println(session.getAttribute("username"));
    %>

    <br/>

    <form action="/servlet/logout" method="post">
        <tr>
            <td></td>
            <td><input type="submit" value="登出"/></td>
        </tr>
    </form>
</body>
</html>
