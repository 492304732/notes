<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">
    <title>Servlet的生命周期</title>
</head>

<body>
<h1>Servlet的生命周期</h1><hr />
<a href = "/servlet/lifeCircle">以get方式请求</a>
</body>

