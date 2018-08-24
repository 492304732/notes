<%--
  Created by IntelliJ IDEA.
  User: 01369674
  Date: 2018/8/14
  Time: 16:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>websocket 测试</title>
    <script type="text/javascript">
        var url = 'ws://'+window.location.host+'<%=request.getContextPath()%>/hello';
        var sock = new WebSocket(url);
        sock.onopen = function () {
            console.log('开启WebSocket连接！');
            sayHello();
        }
        sock.onmessage = function (e) {
            console.log('接收消息',e.data);
            setTimeout(function () {
                sayHello();
            },2000)
        }
        sock.onclose=function () {
            console.log('关闭WebSocket连接！');
        }
        function sayHello () {
            console.log('发送消息：hello world');
            sock.send('hello');
        }
    </script>
</head>
<body>
    hello world!
</body>
</html>
