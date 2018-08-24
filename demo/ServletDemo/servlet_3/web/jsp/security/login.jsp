<%@ page import="java.util.Enumeration" %><%--
  Created by IntelliJ IDEA.
  User: 01369674
  Date: 2018/6/1
  Time: 17:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录</title>
</head>
<body>
<form action="/servlet/login" method="post">
    <table border="1" width="800">
        <tr>
            <td>用户名:</td>
            <td><input type="text" name="username" /></td>
        </tr>
        <tr>
            <td>密码:</td>
            <td><input type="password" name="password" /></td>
        </tr>
        <tr>
            <td></td>
            <td><input type="submit" value="提交" /></td>
        </tr>
    </table>
</form>
<div>
    <%
        String meg = (String)request.getAttribute("message");
        if(meg!=null){
            out.println(meg);
        }
    %>
</div>
</body>
</html>
