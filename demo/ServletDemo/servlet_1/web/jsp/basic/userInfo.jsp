<%--
  Created by IntelliJ IDEA.
  User: 01369674
  Date: 2018/5/22
  Time: 11:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.demo1.entity.User" import="java.text.SimpleDateFormat"%>
<html>
<head>
    <title>userInfo</title>
    <style type="text/css">
        .title{
            width: 30%;
            background-color: #CCC;
            font-weight: bold;
        }
        .content{
            width:70%;
            background-color: #CBCFE5;
        }
    </style>
</head>
<body>
<h1>用户信息</h1>
<hr>
    <jsp:useBean id="regUser" class="com.demo1.entity.User" scope="session"/>
    <table width="600" cellpadding="0" cellspacing="0" border="1">
        <tr>
            <td class="title">用户名：</td>
            <td class="content">&nbsp;<jsp:getProperty name="regUser" property="username"/></td>
        </tr>
        <tr>
            <td class="title">密码：</td>
            <td class="content">&nbsp;<jsp:getProperty name="regUser" property="password"/></td>
        </tr>
        <tr>
            <td class="title">性别：</td>
            <td class="content">&nbsp;<jsp:getProperty name="regUser" property="sex"/></td>
        </tr>
        <tr>
            <td class="title">E-mail：</td>
            <td class="content">&nbsp;<jsp:getProperty name="regUser" property="email"/></td>
        </tr>
        <tr>
            <td class="title">出生日期：</td>
            <td class="content">&nbsp;
                <%
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
                    String date = sdf.format(regUser.getBirthday());
                %>
                <%=date%>
            </td>
        </tr>
        <tr>
            <td class="title">爱好：</td>
            <td class="content">&nbsp;
                <%
                    String[] favorites = regUser.getFavorites();
                    if(favorites!=null){
                        for(String f:favorites)
                        {
                %>
                <%=f%> &nbsp;&nbsp;
                <%
                        }
                    }
                %>
            </td>
        </tr>
        <tr>
            <td class="title">自我介绍：</td>
            <td class="content">&nbsp;<jsp:getProperty name="regUser" property="introduce"/></td>
        </tr>
        <tr>
            <td class="title">是否介绍协议：</td>
            <td class="content">&nbsp;<jsp:getProperty name="regUser" property="flag"/></td>
        </tr>
    </table>
</body>
</html>
