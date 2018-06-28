> 讨论内容：针对 HTTP、Servlet、JSP 的基础知识回顾与开放性问题讨论
>
> 参会人：郭苏进，舒伟，范仕泖，杨林，王富金，柯瑞强
>
> 讨论时间：2018.6.6

# Question and Answer

**1、什么是 Servlet 的单实例多线程，为什么要设计成单实例和多线程的，实际代码中应该注意什么问题。**

​	单实例：每个 Servlet 类只创建一个对象，这样减少了资源的开销。

​	多线程：对于多个客户端同时发送 HTTP 请求，服务端会创建多个线程，每个请求对应一个线程。这样提高了执行效率，提高系统的并发量及响应时间。

​	写代码时注意的问题：单实例多线程有线程共享变量和线程独享变量之分，所以特别要注意线程安全。对于Servlet 的成员变量，属于该 Servlet 实例的所有线程共享，不是线程安全的。（1）请求要处理的变量尽量放在上下文中（HttpSession，ServletContext 等），不要用成员变量处理。（2）在程序中可能会有多个线程或进程同时操作同一个资源(如:多个线程或进程同时对一个文件进行写操作).此时也要注意同步问题。



**2、Servlet/JSP 的四个作用域是哪些，他们的具体作用范围是什么。**

四大作用域的描述如下：

​	ServletContext：作用范围是整个 Web 应用。当Web应用被加载进容器时创建代表整个web应用的ServletContext对象，当服务器关闭或Web应用被移除时，ServletContext对象跟着销毁。  ServletContext 可以实现 Servlet 的转发，获取相对路径，共享数据等。

​	Request 域 ：作用范围是整个请求链（请求转发也存在）。在service 方法调用前由服务器创建，传入service方法。整个请求结束，request生命结束。  

​	Session 域：作用范围是一次会话。在第一次调用 request.getSession() 方法时，服务器会检查是否已经有对应的session,如果没有就在内存中创建一个session并返回。当一段时间内session没有被使用（默认为30分钟），则服务器会销毁该session。

​	PageContext 域：作用域是整个JSP页面，是四大作用域中范围最小的。当对JSP的请求时开始，当响应结束时销毁。

Jsp 九大隐式对象的作用域如下表所示：

| 对象          | 描述      | 类型                                     | 作用域         |
| ----------- | ------- | -------------------------------------- | ----------- |
| request     | 请求对象    | javax.servlet.http.HttpServletRequest  | Request     |
| response    | 响应对象    | javax.servlet.http.HttpServletResponse | Page        |
| out         | 输出对象    | javax.servlet.jsp.JspWriter            | Page        |
| session     | 会话对象    | javax.servlet.http.HttpSession         | Session     |
| application | 应用程序对象  | javax.servlet.ServletContext           | Application |
| config      | 配置对象    | javax.servlet.ServletConfig            | Page        |
| pageContext | 页面上下文对象 | javax.servlet.jsp.PageContext          | Page        |
| page        | 页面对象    | javax.servlet.jsp.HttpJspPage          | Page        |
| exception   | 异常对象    | java.lang.Throwable                    | Page        |



**3、用页面写一个聊天软件，数据存储到哪个域？**

​	由于数据需要在几个客户端间共享（多个 session），所以需要存在 Application 域。当然，也可以写在其他应用共享的空间。



**4、转发和重定向有什么区别，实际业务中如何选择重定向和转发。**

​	Forward: servlet 将请求交给Web应用的另一部分，它们属于同一个访问请求和响应过程。浏览器以正常方式得到响应，把它显示给用户（在浏览器的地址栏上没有变化，用户不知道发生过请求分派）。

​	Redirect: servlet在响应上请求sendRedirect(aString)，HTTP响应有一个状态码“301”，和一个“Location”首部，这个首部值是一个URL。浏览器得到响应，发现“301”状态码，并寻找“Location”首部，由此建立一个新的请求（在浏览器的地址栏里URL改变了）。

区别：
1. 重定向跳转过程发生在客户端，客户端发送了两次请求。转发跳转过程发生在服务端，客户端不能察觉到转发过程。
2. 由于重定向要经过两次请求来回，所以转发速度更快，并且可以继续使用 request、response 对象。
3. 重定向可以将页面转到外网。

实际业务：

​	用重定向不用转发的情况：渠道管理系统中页面跳转用重定向，因为这样才能对 URL 权限进行过滤。权限过滤的业务定义在过滤器中，重定向会经过 Filter/Interceptor 的过滤，转发不会经过 Filter/Interceptor。

​	

**5、jsessionid 作为服务器识别 session 的唯一标志，在不同容器中的 key 值（名称）是否不一样。**

​	tomcat，jetty，jboss 中默认的名称均为 jsessionid，也可配置成其他名称，如 jsessionid1。

​	tomcat 修改 jsessionid 名称：https://my.oschina.net/eastwmt/blog/151596



**5、jsp曾经是主流的web技术，为什么现在渐渐不流行？**

1. jsp 技术在服务端进行视图渲染，现在逐渐被 js+ajax 等技术替代。
2. jsp 不能做到前后端分离。jsp 文件中同时存在 java 后端代码和 html 等前端代码，不利于前端开发人员和后端开发人员分别开发。
3. jsp 不能做到动静分离。为了提高获取静态文件的效率，目前许多项目将静态文件分离出来，放在 Nginx 等静态文件服务器中。而 jsp 文件依赖 java 语言和 web 容器，不能单独存放。
4. jsp 在服务端渲染视图，js+ajax 等技术在客户端渲染视图。在网页开发早期，客户端普遍性能低下，在浏览器进行视图渲染会拖慢网页速度。而在 pc 性能快速提高的今天已经不存在这样的问题，将渲染的任务从服务端转移到客户端分担，反而能提高效率。