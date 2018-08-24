<%--
  Created by IntelliJ IDEA.
  User: 01369674
  Date: 2018/5/28
  Time: 10:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- 获取根路径 --%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html>
<head>
    <title>图片上传与下载</title>
    <%-- http 请求头 --%>
    <!-- 设置不接受缓存资源 -->
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="private, max-age=0, no-cache">
    <meta http-equiv="expires" content="0">
</head>
    <!-- 上传文件 -->
    <%-- EL表达式 ${path} --%>
    <div>文件储存路径：${path}</div>
    </br>
    <form enctype="multipart/form-data" method="post" action="/servlet/upload">
        选择图片：<input type="file" name="uploadFile"/>
        <input type="submit" value="提交">
    </form>
    </br>
    <div>下载：
        <a href="/servlet/download?filename=test.png"> test.png</a>
    </div>

</body>
</html>
