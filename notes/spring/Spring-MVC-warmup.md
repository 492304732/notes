## Web 基础

#### 提问环节

一起来回顾下 Web 基础的问题

1. 写一个 web 工程的 hello world 需要哪些步骤？（继承HttpServlet-->重写doGet（）或者doPost（）方法-->在web.xml中注册Servlet ）
2. 浏览器发送一个post请求到接收到结果，系统后台做了什么事情。
3. 为什么启动一个 Web 工程需要 tomcat 呢？tomcat 的作用是什么
4. 如何理解 Servlet 的单实例多线程？生产中对 Servlet 多线程场景要注意什么问题
5. http 协议报文包括哪些内容
6. 转发和重定向的区别是什么
7. cookie 和 session 的作用是什么，他们有什么区别和联系
8. Filter 的作用是什么，如何制定Filter的执行顺序



#### 知识点介绍

- 介绍 Servlet 的生命周期：init()、service()、destroy()
- Servlet 的几个上下文作用域（application、session、request ）
- Servlet 的事件监听：对应几个域的监听（ServletContext、Session、Request ），对应几种监听器（生命周期（初始化和销毁）、上下文属性的变更（添加修改删除））




## Spring MVC

#### 提问环节

1. Spring MVC 如何写 hello world 工程？相较原生 Servlet 有什么优点？
2. Spring 容器是什么？Spring 容器和 Spring MVC 是如何协同作用的？
3. 什么是 MVC 模式？相较于非 MVC 的优点是什么？


#### 知识点介绍


- Spring MVC 在 Web 项目中是如何启动 Spring 容器的？
- 介绍 Spring MVC  是如何实现 modle、view、controller 分离的，处理流程是什么？
- 介绍 @RequestBody/@ResponseBody 与 HttpMessageConverter 组件

