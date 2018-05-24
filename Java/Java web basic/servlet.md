参考博客：https://blog.csdn.net/qq_26676207/article/details/51023327

参考资料：https://www.w3cschool.cn/servlet/servlet-intro.html

参考资料：《Servlet & Jsp &Spring MVC 初学指南》

## 一、Servlet

### 1、编写 Servlet 流程

编写一个Servlet程序大体上需要3个步骤：继承HttpServlet-->重写doGet（）或者doPost（）方法-->在web.xml中注册Servlet。

新语法中，可以用 @WebServlet(name = "MyServlet", urlPatterns = { "/my" }) 这样的注解来注册servlet。

### 2、Servlet 生命周期

- Servlet 通过调用 **init ()** 方法进行初始化。初始化时，会创建ServletConfig对象，传入Servlet的成员变量。
- Servlet 调用 **service()** 方法来处理客户端的请求。service() 方法检查 HTTP 请求类型（GET、POST、PUT、DELETE 等），并调用 doGet、doPost、doPut，doDelete 等方法。
- Servlet 通过调用 **destroy()** 方法终止（结束）。

![](http://images.cnitblog.com/blog2015/698228/201503/241521080678432.jpg)



### 3、Servlet 单实例多线程

**初始化：**

- 每个Servlet 只会实例化一次
- 容器启动时实例化：在 web.xml 中通过<load-on-startup>标签进行设定，参数为数字，表示了启动的顺序，数字越小优先级越高。
- 第一次访问时实例化：默认不配置该参数的情况下，Servlet 只有在被访问时才会实例化。

**多线程：**

- 每次服务器接收到一个 Servlet 请求时，服务器会产生一个新的线程并调用服务。

![](https://img.w3cschool.cn/attachments/day_160820/201608201303222781.jpg)



### 4、Servlet 继承关系

- **ServletConfig  **Servlet的配置信息，常用来在Servlet初始化时进行信息传递
- **Servlet** 定义Servlet生命周期调用的方法：init()、service()、destroy()


- **GenericServlet  **与协议无关的Servlet，实现了Servlet和ServletConfig接口


- **HttpServlet  **基于HTTP协议的实现类

![](https://images2017.cnblogs.com/blog/1007017/201709/1007017-20170901134328562-261318272.png)



### 5、转发和重定向

网页跳转有两种方式重定向和服务器内部转发。Forward 和 Redirect 的区别。

### 6、相对路径和绝对路径

URL的写法也有2种方式：绝对路径和相对路径。相对路径使用..即可，而绝对路径重定向需要依赖request.getContextPath()方法取得上下文环境，而服务器内部转发中的斜线就表示项目的根目录。

### 7、Servlet 自定义初始化

Servlet 的自定义初始化可以通过配置 web.xml 文件，或者重写Servlet的init()方法实现。

### 8、ServletConfig

GenericServlet 实现了ServletConfig 接口，HttpServlet 持有一个 ServletConfig 对象。

当Servlet容器初始化Servlet时，Servlet容器会给Servlet的init方法传入一个ServletConfig。ServletConfig中存放着Servlet的初始化信息，可通过@WebServlet或web.xml配置。

```java
//示例
@WebServlet(name = "ServletConfigDemoServlet",
  urlPatterns = { "/servletConfigDemo" },
  initParams = {
    @WebInitParam(name="admin", value="Harry Taciak"),
    @WebInitParam(name="email", value="admin@example.com")
  }
)
```

| 方法名                     | 说明             |
| ----------------------- | -------------- |
| getInitParameter()      | 根据参数名获取指定初始化参数 |
| getInitParameterNames() | 获取所有初始化参数名     |

### 9、ServletContext

ServletContext表示Servlet应用程序。每个Web应用程序只有一个上下文。在将一个应用程序同时部署到多个容器的分布式环境中，每台Java虚拟机上的Web应用都会有一个ServletContext对象。

通过在ServletConfig中调用getServletContext方法，可以获得ServletContext。ServletContext可以共享从应用程序中访问到的信息，并且可以动态注册Web对象。

```java
java.lang.Object getAttribute(java.lang.String name)
java.util.Enumeration<java.lang.String> getAttributeNames()
void setAttribute(java.lang.String name, java.lang.Object object)
void removeAttribute(java.lang.String name)
```



## 二、Request & Response

### 1、ServletRequest

对于每一个HTTP请求，Servlet容器都会创建一个 ServletRequest 实例，并将它传给Servlet的Service方法。
ServletRequest 封装了关于这个请求的信息。

getParameter() 是最常用的方法，可以获取表单(form)的参数值。

| 方法名                       | 说明                               |
| ------------------------- | -------------------------------- |
| getContentLength()        | 返回请求主体的字节数。如果不知道字节长度，这个方法就会返回−1。 |
| getContentType()          | 返回请求主体的MIME类型，如果不知道类型，则返回null    |
| getParameter(String name) | 返回指定请求参数的值。                      |
| getProtocol()             | 返回这个HTTP请求的协议名称和版本。              |

### 2、HttpServletRequest

HttpServletRequest 继承了 ServletRequest

| 方法名                        | 说明                                       |
| -------------------------- | ---------------------------------------- |
| getContextPath()           | 返回表示请求上下文的请求URI部分                        |
| getCookies()               | 返回一个Cookie对象数组                           |
| getMethod()                | 返回生成这个请求的HTTP方法名称。                       |
| getQueryString()           | 返回请求URL中的查询字符串                           |
| getSession()               | 返回与这个请求相关的会话对象。如果没有，将创建一个新的会话对象。         |
| getSession(boolean create) | 返回与这个请求相关的会话对象。如果有，并且create参数为True，将创建一个新的会话对象。 |

### 3、ServletResponse

ServletResponse接口表示一个Servlet响应。在调用Servlet的Service方法前，Servlet容器首先创建一个ServletResponse，并将它作为第二个参数传给Service方法。ServletResponse隐藏了向浏览器发送响应
的复杂过程。

| 方法名             | 说明                    |
| --------------- | --------------------- |
| getWriter       | 获取字符输出流               |
| setContentType  | 设置方法体内容格式，如：text/html |
| getOutputStream | 获取二进制输出流              |

### 4、HttpServletResponse

HttpServletResponse 继承了 ServletResponse

| 方法名                                  | 说明                   |
| ------------------------------------ | -------------------- |
| addCookie(Cookie cookie)             | 给这个响应对象添加一个cookie    |
| addHeader(String name, String value) | 给这个响应对象添加一个header    |
| sendRedirect(String location)        | 发送一条响应码，将浏览器跳转到指定的位置 |



## 三、Session

Http 是无状态的协议，要保存客户端的状态（如当用户输入了相应的用户名和密码后，应用不应该再次提示需要用户登录，应用需要记录用户登录的状态），就需要用到 Session 技术。

### 1、Cookie

1. Cookie 可以实现多个页面传递信息
2. 没有直接的方法来删除cookie，只能创建一个同名的cookie，并将maxAge属性设置为0。

```java
Cookie cookie = new Cookie(name, value);
httpServletResponse.addCookie(cookie);
```

```java
//Cookie的属性
private String name;	// NAME= ... "$Name" style is reserved
private String value;	// value of NAME

private String comment;	// ;Comment=VALUE ... describes cookie's use
// ;Discard ... implied by maxAge < 0
private String domain;	// ;Domain=VALUE ... domain that sees cookie
private int maxAge = -1;	// ;Max-Age=VALUE ... cookies auto-expire
private String path;	// ;Path=VALUE ... URLs that see the cookie
private boolean secure;	// ;Secure ... e.g. use SSL
private int version = 0;	// ;Version=1 ... means RFC 2109++ style
private boolean isHttpOnly = false;
```

其中 domain 是 cookie 的有效域，maxAge 是 cookie 有效期，isHttpOnly=true 禁止 javascript 操作cookie。

案例：通过 cookie 设置用户偏好，如字体、每页显示多少条数据等。

### 2、HttpSession

**session 保存参数**

```java
public void setAttribute(String name,Object value)
public Enumeration<java.lang.String> getAttributeNames()
public Object getAttribute(String name);
public void removeAttribute(String name);
//setValue、getValueNames、getValue、removeValue 对于Version2.2被标记为过时
```

放入到HttpSession 的值，是存储在内存中的，因此，不要往HttpSession放入太多对象或大对象。

放入 HttpSession 中的 Object 最好实现 java.io.Serializable，因为Servlet容器认为必要时会将这些对象放入文件或数据库中，尤其在内存不够用的时候，如果将没有实现序列化的对象放入 HttpSession，会在这些时候报序列化错误。

**session 唯一标志：JSESSIONID**

Servlet容器为每个HttpSession 生成唯一的标识，并将该标识发送给浏览器，或创建一个名为JSESSIONID的cookie，或者在URL后附加一个名为jsessionid 的参数。在后续的请求中，浏览器会将标识提交给服务端，这样服务器就可以识别该请求是由哪个用户发起的。Servlet容器会自动选择一种方式传递会话标识，无须开发人员介入。

```java
public String getId();
```

**session 过期**

```java
public void setMaxInactiveInterval(int interval); //设置超时时间
public int getMaxInactiveInterval(); //查看超时时间,单位为秒
public void invalidate(); //强制会话过期，并清空其保存的对象。
```

默认session过期时间可通过 web.xml 配置，如果没有配置，则取决于Servlet容器设置的默认值。

大部分情况下，你应该主动销毁无用的HttpSession，以便释放相应的内存。

若设置为0，则该HttpSession 永不过期。通常这不是一个好的选择。

**案例**

购物车的浏览、添加和查看。

Q：京东和淘宝的购物车是不会过期的，如何进行持久化操作。存入数据库还是持久化 session。



## 四、JSP 技术

### 1、概论

在 jsp 出现前，Servlet 必须在 Writer 中拼接 html，使得控制层和显示层混在一起。jsp 则实现了控制层和显示层的分离，让Servlet只关注业务逻辑，jsp控制页面展示。

**JSP 的生命周期：**

JSP页面本质上是一个Servlet。当一个JSP页面第一次被请求时，Servlet/JSP容器主要做以下两件事情：

1. 转换JSP页面到JSP页面实现类，该实现类是一个实现javax.servlet.jsp.JspPage接口或子接口javax.servlet.jsp.HttpJspPage的Java类。JspPage是javax.servlet.Servlet的子接口，这使得每一个JSP页面都是一个Servlet。该实现类的类名由Servlet/JSP容器生成。如果出现转换错误，则相关错误信息将被发送到客户端。

2. 如果转换成功，Servlet/JSP容器随后编译该Servlet类，并装载和实例化该类，像其他正常的Servlet一样执行生命周期操作。

JSP 页面也可以被配置为启动时加载。当JSP文件修改时，容器会自动重新编译，无需重启tomcat。

**注释：**

java 注释，不会发送给客户端。

```jsp
<%-- retrieve products to display --%>
```

html 注释，会发送给客户端

```jsp
<!-- [comments here] -->
```

### 2、隐式对象

Servlet容器会传递几个对象给它运行的Servlet。例如，可以通过Servlet的service方法拿到HttpServletRequest和HttpServletResponse对象，以及可以通过init方法访问到ServletConfig对象。此外，可以通过调用HttpServletRequest对象的getSession方法访问到HttpSession对象。

在JSP中，可以通过使用隐式对象来访问上述对象。表3.1所示为JSP隐式对象。

| 对象          | 类型                                     |
| ----------- | -------------------------------------- |
| request     | javax.servlet.http.HttpServletRequest  |
| response    | javax.servlet.http.HttpServletResponse |
| out         | javax.servlet.jsp.JspWriter            |
| session     | javax.servlet.http.HttpSession         |
| application | javax.servlet.ServletContext           |
| config      | javax.servlet.ServletConfig            |
| pageContext | javax.servlet.jsp.PageContext          |
| page        | javax.servlet.jsp.HttpJspPage          |
| exception   | java.lang.Throwable                    |

可以直接在JSP内嵌java语句中使用这些对象，如：

```jsp
<% String userName = request.getParameter("userName"); %>
```

**PageContext：**

PageContext 有设置和获取属性的方法。属性值可被存储在4个范围之一：页面、请求、会话和应用程序（PAGE_SCOPE、REQUEST_ SCOPE、SESSION_SCOPE和APPLICATION_SCOPE）。页面范围是最小范围，这里存储的属性只在同一个JSP页面可用。请求范围是指当前的ServletRequest中。会话范围指当前的HttpSession中。应用程序范围指应用的ServletContext中。

```java
public abstract void setAttribute(String name, Object value, int scope)
```

案例：jsp 调用隐式对象页面

### 3、JSP指令

指令是JSP语法元素的第一种类型。它们指示JSP转换器如何翻译JSP页面为Servlet。

#### （1）page指令：

```jsp
<%@page attribute1="value1" attribute2="value2" ... %>
```

page 指令有如下属性：

| 属性                              | 说明                                       |
| ------------------------------- | ---------------------------------------- |
| **import**                      | **定义一个或多个本页面中将被导入和使用的java类型。**           |
| session                         | 本页面是否加入会话管理。默认值为True。                    |
| buffer                          | 定义隐式对象out的缓冲大小。必须以KB后缀结尾。默认大小为8KB或更大（取决于JSP容器）。该值可以为none，这意味着没有缓冲，所有数据将直接写入PrintWriter。 |
| autoFlush                       | 默认值为True。若值为True，则当输出缓冲满时会自写入输出流。而值为False，则仅当调用隐式对象的flush方法时，才会写入输出流。因此，若缓冲溢出，则会抛出异常。 |
| isThreadSafe                    | 定义该页面的线程安全级别。不推荐使用 JSP 参数，因为使用该参数后，会生成一些 Servlet容器已过期的代码。 |
| info                            | 返回调用容器生成的 Servlet 类的 getServletInfo 方法的结果 |
| errorPage                       | 定义当出错时用来处理错误的页面。                         |
| isErrorPage                     | 标识本页是一个错误处理页面。                           |
| **contentType**                 | **定义本页面隐式对象response的内容类型，默认是text/html。** |
| **pageEncoding**                | **定义本页面的字符编码，默认是ISO-8859-1。**            |
| isELIgnored                     | 配置是否忽略EL表达式。EL是Expression Language的缩写。   |
| language                        | 定义本页面的脚本语言类型，默认是 Java，这在 JSP 2.2 中是唯一的合法值。 |
| extends                         | 定义JSP实现类要继承的父类。这个属性的使用场景非常罕见，仅在特殊理由下使用。  |
| deferredSyntax AllowedAsLiteral | 定义是否解析字符串中出现“#{”符号，默认是False。“{# ”是一个表达式语言的起始符号。 |
| trimDirective Whitespaces       | 定义是否不输出多余的空格/空行，默认是False。                |

#### （2）include 指令

include 指令的语法如下：

```jsp
<%@include file="url"%>
```

include 指令引入 copyright.jspf 文件，include所在的行被copyright.jspf 文件内容代替。按照惯例，以JSPF为扩展名的文件代表JSP fragement 或 JSP segment。include指令也可以包含静态HTML文件。

```jsp
<html>
  <head><title>Including a file</title></head>
  <body>
    This is the included content: <hr/>
    <%@ include file="copyright.jspf"%>
  </body>
</html>
```

### 4、脚本元素

一个脚本程序是一个Java代码块，以<%符号开始，以%>符号结束。

#### （1）表达式

每个表达式都会被JSP容器执行，并使用隐式对象out的打印方法输出结果。表达式以“<%=”开始，并以“%>”结束。

```jsp
Today is <%=java.util.Calendar.getInstance().getTime()%>
<!-- 上下两段代码等效 -->
Today is <% out.print(java.util.Calendar.getInstance().getTime()); %>
```

#### （2）声明

可以声明能在JSP页面中使用的变量和方法。声明以“<%！”开始，并以“%>”结束。

```jsp
<%!
public String getTodaysDate() {
return new java.util.Date();
}%>
<html>
  <head><title>Declarations</title></head>
  <body>
  Today is <%=getTodaysDate()%>
  </body>
</html>
```

可以使用声明来重写JSP页面，实现类的init和destroy方法。通过声明jspInit方法，来重写init方法。通过声明jspDestroy方法，来重写destory方法。在创建和销毁JSP页面时会分别调用jspInit和jspDestroy方法。

#### （3）禁用脚本元素

随着JSP 2.0对表达式语言的加强，推荐的实践是：在JSP页面中用EL访问服务器端对象且不写Java代码。因此，从JSP 2.0起，可以通过在部署描述符中的<jspproperty-group>定义一个scripting-invalid元素，来禁用脚本元素。

```jsp
<jsp-property-group>
  <url-pattern>*.jsp</url-pattern>
  <scripting-invalid>true</scripting-invalid>
</jsp-property-group>
```

### 5、动作

动作是第三种类型的语法元素，它们被转换成Java代码来执行操作，如访问一个Java对象或调用方法。下面讨论所有JSP容器支持的标准动作。除标准外，还可以创建自定义标签执行某些操作。

#### （1）useBean

useBean将创建一个关联Java对象的脚本变量。这是早期分离的表示层和业务逻辑的手段。随着其他技术的发展，如自定义标签和表达语言，现在很少使用useBean方式。

它创建一个java.util.Date实例，并赋值给名为today的脚本变量，然后在表达式中使用。访问这个页面会显示系统当前时间：

```jsp
<jsp:useBean id="today" class="java.util.Date"/>
<%=today%>
```

#### （2）setProperty 和 getProperty

setProperty动作可对一个Java对象设置属性，而getProperty则会输出Java对象的一个属性。

```java
//Employee 实体
public class Employee {
  private String id;
  private String firstName;
  private String lastName;
  ... getter and setter 函数 ...
}
```

```jsp
<!-- 用setProperty设置实体属性，getProperty获取实体属性 -->
<body>	
  <jsp:useBean id="employee" class="app03a.Employee"/>
  <jsp:setProperty name="employee" property="firstName"value="Abigail"/>
  First Name: <jsp:getProperty name="employee" property="firstName"/>
</body>
```

#### （3）include

include动作用来动态地引入另一个资源。可以引入另一个JSP页面，也可以引入一个Servlet或一个静态的HTML页面。

```jsp
<body>
  <jsp:include page="jspf/menu.jsp">
  	<jsp:param name="text" value="How are you?"/>
  </jsp:include>
</body>
```

include 指令和 include 动作的不同：

1. 对于include指令，资源引入发生在 JSP 容器将页面转换为生成的 Servlet 时。而对于include动作，资源引入发生在请求页面时。因此，使用include动作是可以传递参数的，而include指令不支持。
2. 第二个不同是，include指令对引入的文件扩展名不做特殊要求。但对于include动作，若引入的文件需以JSP页面处理，则其文件扩展名必须是JSP。若使用.jspf为扩展名，则该页面被当作静态文件。

#### （4）forward

forward将当前页面转向到其他资源。下面代码将从当前页转向到 login.jsp 页面：

```jsp
<jsp:forward page="jspf/login.jsp">
  <jsp:param name="text" value="Please login"/>
</jsp:forward>
```

### 6、错误处理

当 jsp 页面抛出异常时，用户将看到一个精心设计的网页解释发生了什么，而不是一个用户无法理解的错误信息。

page指令定义错误页面：

```jsp
<%@page isErrorPage="true"%>
<html>
<head><title>Error</title></head>
  <body>
  An error has occurred. <br/>
  Error message:
  <% out.println(exception.toString()); %>
  </body>
</html>
```

其他页面指向错误页面，也用page指令定义：

```jsp
<%@page errorPage="errorHandler.jsp"%>
Deliberately throw an exception
<% Integer.parseInt("Throw me"); %>
```



### 五、JSP：表达式语言

#### （1）语法

JSP 2.0最重要的特性之一就是表达式语言（EL），JSP用户可以用它来访问应用程序数据。

**表达式格式：**

EL表达式以 ${ 开头，并以 } 结束。EL表达式的结构如下：

```jsp
格式：${expression}
如：${x+y}
```

**获取对象属性：**

为了获取对象属性，可以用下列两种方式：

```jsp
${object["propertyName"]}
${object.propertyName}
```

嵌套使用的情况：

```jsp
${pageContext["request"]["servletPath"]}
${pageContext.request["servletPath"]}
${pageContext.request.servletPath}
${pageContext["request"].servletPath}
```

#### （2）EL隐式对象

在JSP页面中，可以利用JSP脚本来访问JSP隐式对象。但是，在免脚本的JSP页面中，则不可能访问这些隐式对象。EL允许通过提供一组它自己的隐式对象来访问不同的对象。EL隐式对象如下表所示：

| 对象               | 描述                                       |
| ---------------- | ---------------------------------------- |
| pageContext      | 这是当前JSP的javax.servlet.jsp.PageContext    |
| initParam        | 这是一个包含所有环境初始化参数，并用参数名作为key的Map           |
| param            | 这是一个Map，可以获取请求提交的参数，如登录时的 username、password。 |
| paramValues      | 和param类似，但Map中key对应的value是一个字符串数组，可返回同一个param名称对应的多个属性。 |
| header           | 这是一个Map，可以获取请求头的属性，如accept、connect。      |
| headerValues     | 和header类似，但Map中key对应的value是一个字符串数组，可返回同一个header名称对应的多个属性。 |
| cookie           | 这是一个包含了当前请求对象中所有Cookie对象的Map。Cookie名称就是key名称，并且每个key都映射到一个Cookie对象 |
| applicationScope | 这是一个包含了ServletContext对象中所有属性的Map，并用属性名称作为key |
| sessionScope     | 这是一个包含了HttpSession对象中所有属性的Map，并用属性名称作为key |
| requestScope     | 这是一个Map，其中包含了当前HttpServletRequest对象中的所有属性，并用属性名称作为key |
| pageScope        | 这是一个Map，其中包含了全页面范围内的所有属性。属性名称就是Map的key   |

在servlet/JSP编程中，有界对象是指在以下对象中作为属性的对象：PageContext、ServletRequest、HttpSession或者ServletContext。隐式对象sessionScope、requestScope和pageScope与applicationScope相似。但是，其范围分别为session、request和page。

#### （4）配置 EL

有了EL、JavaBeans和定制标签，就可以编写免脚本的JSP页面了。JSP 2.0及其更高的版本中还提供了一个开关，可以使所有的JSP页面都禁用脚本。

```jsp
<jsp-config>
  <jsp-property-group>
    <url-pattern>*.jsp</url-pattern>
    <scripting-invalid>true</scripting-invalid>
  </jsp-property-group>
</jsp-config>
```

另一方面，在有些情况下，可能还会需要在应用程序中取消EL。例如，正在使用与JSP 2.0兼容的容器，却尚未准备升级到JSP 2.0，那么就需要这么做。在这种情况下，可以关闭EL表达式的计算。

```jsp
<jsp-config>
  <jsp-property-group>
    <url-pattern>*.jsp</url-pattern>
    <el-ignored>true</el-ignored>
  </jsp-property-group>
</jsp-config>
```



### 六、JSTL

JSP 标准标签库（JavaServer Pages Standard Tag Library，JSTL）是一个定制标签库的集合，用来解决像遍历Map或集合、条件测试、XML处理，甚至数据库访问和数据操作等常见的问题。

