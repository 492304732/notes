## 体系结构

#### 基本流程

从接受请求到返回响应，Spring MVC 框架的众多组件通力合作，各司其职，有条不紊的完成分配的工作。

在整个框架中，DispatcherServlet 处于核心位置，它负责协调和组织不同的组件以完成请求处理并返回响应的工作。Spring MVC 通过位于前端的 DispatcherServlet 接受所有的请求，并将具体工作委托给其他组件进行处理。

![Spring MVC 框架模型.png](https://upload-images.jianshu.io/upload_images/1341067-813aa6309c04cf3a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)



1.  整个过程始于客户端发出的请求，web 应用服务器接收到这个请求，如果匹配 DispatcherServlet 的请求映射路径（在 web.xml 中指定），则 web 容器将该请求转交给 DispatcherServlet 处理。
2.  DispatcherServlet 接收到这个请求以后，将根据请求信息及 HandlerMapping 的配置找到处理请求的处理器（Handler）。实际上SpringMVC没有一个handler接口，任何Object都可以成为handler。
3.  当 DispatcherServlet 根据 HandleMapping 得到一个当前请求的 Handler 以后，通过 HandleAdapter 对Handle 进行封装，再以统一的适配器接口调用 Handler。
4.  处理完业务逻辑的处理以后，将返回一个 ModelAndView  给 DispatcherServlet。
5.  ModelAndView 中包含的逻辑视图并非真正的视图对象，DispatcherServlet 借助 ViewResolve 完成逻辑视图到真实视图对象的解析工作。
6.  当得到真实的视图对象以后，将使用 view 对象对这个 ModelAndView 进行数据模型视图渲染。
7.  最终客户端得到的可能是一个普通的 HTML 页面，也可能是一个 XML 或者 JSON 串，甚至是一张图片或一个PDF 文档等不同的媒体格式。

#### 运行用户注册模块

**界面展示：**

下图是用户注册的界面。访问注册页面，填写表单，点击提交按钮，浏览器会向服务器发送一个/user/create的post请求，带有用户注册信息的数据。浏览器返回注册成功的页面 createSuccess.jsp，并展示用户信息。

![image.png](https://upload-images.jianshu.io/upload_images/1341067-7cb32efd9055fe20.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

**流程分析：**

对于使用 Spring MVC 的开发者，如何代码实现上述例子应该非常清楚。下面看看 Spring MVC 在获取请求到返回视图，中间经历了什么样的流程。

![运行用户注册.png](https://upload-images.jianshu.io/upload_images/1341067-5518ea61406f43b3.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

1. DispatcherServlet 接收到客户端的 /user/create 的请求
2. DispatcherServlet 使用 DefaultAnnotationHandlerMapping 查找负责处理该请求的处理器。
3. DispatcherServlet 将请求分发给 /user/create 对应的的 UserController 处理器
4. 处理器完成业务处理后，返回 ModelAndView 对象，其中 View 的逻辑名为 /user/createSuccess，模型包含一个键为user的 User 对象
5. DispatcherServlet 调用 InternalResourceViewResolver 组件对 ModelAndView 中的逻辑视图名进行解析，得到真实的 /WEB-INF/view/user/createSuccess.jsp 视图对象
6. DispatcherServlet 使用 /WEB-INF/view/user/createSuccess.jsp 对模型中的 user 模型对象进行渲染
7. 返回响应页面给客户端

## DispatcherServlet

配置 web.xml:

- 配置spring上下文xml文件路径。
- 配置 ContextLoaderListener
- 配置DispatcherServlet

DispatcherServlet 内部逻辑：

- DispatcherServlet 将上下文中的 Spring MVC 组件装配到其中，具体可查看 initStrategies 代码
- DispatcherServlet 装配的默认组件可在jar包中的 DispatcherServlet.xml 查看
- 先扫描自定义组件，如没有配置则使用默认装配的组件



## 注解驱动的控制器

控制器的注解有以下几种功能：

1. URL映射：主要使用 @RequestMapping，通过URL找到相应的方法。
2. 请求处理方法签名：在控制器入参上标记的注解，会影响如何从请求中提取入参。
3. 处理模型数据：在控制器入参上标记注解，影响入参如何放入不同的 web 上下文。

![思维导图.png](https://upload-images.jianshu.io/upload_images/1341067-d0d0e3801b651355.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## HttpMessageConverter

#### HttpMessageConverter

HttpMessageConverter<T> 是Spring一个重要接口，它负责将请求信息转换成一个对象，和将对象输出为响应。

使用 HttpMessageConverter 进行信息转换的方法：

- 使用 @RequestBody/@ResponseBody 对处理方法进行标注。
- 使用 HttpEntity<T>/ResponseEntity<T> 作为处理方法的入参或返回值。

**当控制器处理方法使用 @RequestBody/@ResponseBody 或 HttpEntity<T>/ResponseEntity<T> 时，Spring 首先根据请求头或响应头的 Accept属性选择匹配的 HttpMessageConverter，然后根据参数类型或泛型类型的过滤得到匹配的 HttpMessageConverter，如果找不到可用的 HTtpMessageConverter 则报错。**

#### RequestMappingHandlerAdapter

DispatcherServlet 默认已经安装了 RequestMappingHandlerAdapter 作为 HandlerAdapter 的组件实现类，HttpMessageConverter 即由 RequestMappingHandlerAdapter 使用，将请求转换成对象，或者将对象转换为响应信息。

RequestMappingHandlerAdapter 默认已经装配了以下 HttpMessageConverter：

- StringHttpMessageConverter
- ByteArrayHttpMessageConverter
- SourceHttpMessageConverter
- AllEncompassingFormHttpMessageConverter

RestTemplate 也装配了 HttpMessageConverter，用于请求响应对象转换。

> 代码示例：
>
> 见 spring.demo 工程下的 demo.mvc.messageConverter 包中代码



## 数据绑定流程

Spring MVC 通过反射机制对目标处理方法的签名进行分析，将请求消息绑定到处理方法的入参中。数据绑定的核心组件是DataBinder，其运行机制描述如图所示。

![数据绑定.png](https://upload-images.jianshu.io/upload_images/1341067-27db5169b237bae8.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

Spring MVC 主框架将 ServletRequest 对象及处理方法的入参对象实例传递给 DataBinder。

DataBinder 首先调用装配在 Spring Web 上下文中的 ConversionService 组件进行数据类型转换、数据格式化等工作，将 ServletRequest 中的消息填充到入参对象中。

然后调用 Validator 组件对已经绑定了请求消息的入参对象进行数据合法性校验，最后生成数据绑定结果 BindingResult 对象。

BindingResult 包含了已完成数据绑定的入参对象，还包含相应的校验错误对象。Srping MVC 抽取 BindingResult 中的入参对象及校验错误对象，将它们赋给处理方法的响应入参。



## 数据转换

数据转换指两个 Java 对象之间相互转换。Java 标准的 PropertyEditor 可将一个字符串转换成一个 Java 对象。

Spring 在核心模块中有一个通用的类型转换模块，类型转换模块位于 org.springframework.core.convert 包中。

#### ConversionService

ConversionService 是 Spring 类型转换体系的核心接口。

可利用 ConversionServiceFactoryBean 在Spring 上下文定义一个 ConversionService。该 FactoryBean 注册了 String对象转换为各种基础对象的转换器，还有 String、Number、Array、Collection、Map、Properties 及 Object 之间的转换器。

#### 自定义转换器

**ConversionService：**

可通过 ConversionServiceFactoryBean 的 converters 属性注册自定义的类型转换器。

自定义转换器可以继承下列几种接口：

- Converter<S,T>
- GenericConverter
- ConverterFactory

**@InitBinder：**

在控制器中定义方法，添加@InitBinder注解，这个方法会在控制器初始化的时候调用。

在方法中动态注册自定义编辑器，编辑器UserEditor实现了PropertyEditor接口

```java
@InitBinder
public void initBinder(WebDataBinder binder){
  binder.registerCustomEditor(User.class,new UserEditor());
}
```

**WebBindingInitializer 装配：**

类似@InitBinder动态注册自定义编辑器，只不过@InitBinder是控制器内有效，WebBindingInitializer 是全局有效。

编码方式：实现WebBindingInitializer接口，并在配置文件中注册bean

**优先级：**

- 查询通过@InitBinder装配的自定义编辑器
- 查询通过ConversionService装配的自定义转换器
- 查询通过WebBidingInitializer 装配的自定义编辑器。

> 代码示例：
>
> mvc 工程的 demo.mvc.converter 包中代码



## 数据格式化

String使用转换器进行源类型对象到目标对象的转换，String的转换器并不提供输入及输出信息格式化的工作。如果需要转换的元类型数据是从客户端界面中传递过来的，为了方便使用者观看，这些数据往往具有一定的格式。举例来说，想日期，时间，数字，货币等数据都是具有一定格式的，在不同的本地化环境中，同一类型的数据还会相应地呈现不同的显示格式。

如何从格式化的数据中获取真正的数据以完成数据绑定，并将处理完成的数据输出位格式化的数据，是Spring格式化框架要解决的问题。Spring的格式化框架位于 org.spirngframwork.format包中。

#### Formatter

接口定义的 Printer<T> 负责对象的格式化输出。

接口定义的 Parser<T> 负责对象的格式化输入。

可以通过注解的方式驱动对象格式化。

> 代码示例：
>
> mvc 工程的 demo.mvc.formatter 包中代码

## 数据校验

注解校验：

- 用 JSR-303 注解在实体类中定义校验规则
- 在入参添加 @Valid 注解
- 用 BindingResult 接收校验结果

自定义校验规则：

控制器中添加 @InitBinder 方法，初始化时动态注册自定义校验规则类。

> 代码示例：
>
> mvc 工程的 demo.mvc.validator 包中代码



## 视图和视图解析器

请求处理方法执行完成后，最终返回一个 ModleAndView 对象。对于那些返回 String、View或ModelMap等类型的处理方法，Spring MVC 也会在内部将它们装配成一个 ModleAndView 对象，该对象包含了视图逻辑名和模型对象信息。

Spring MVC 借助视图解析器（ViewResolver）得到最终的视图对象（View），这可能使我们常见的 JSP 视图，也可能基于一个 FreeMarker，Velocity 模板技术的视图，还可能是 PDF、Excel、XML、JSON 等各种格式的视图。

视图解析器实现了 Ordered 接口并开放出一个 OrderNo 属性，可以通过该属性指定解析器的优先顺序。

视图解析步骤：

- 根据优先级调用视图解析器，视图解析器根据视图名称找到对应的视图对象（View）
- 视图对象将视图和模型解析成最终返回的视图，如 jsp 解析完成的 html 页面。

> 代码示例：
>
> mvc 工程的 demo.mvc.viewResolve



## 混合使用多种视图技术

- 使用 ContentNegotiatingViewResolver，这是一个特殊的视图解析器，它像一个仲裁机构或代理人，根据请求信息从上下文中选择一个合适的视图解析器负责解析。
- 对比使用 @ResponseBody 结合 HttpMessageConverter 对返回内容做格式封装，和ContenteNegotiatingViewResolver 返回同一个资源的不同视图。



## 本地化解析

在默认情况下，Spring MVC 根据 Accept-Language 参数判断客户端的本地化类型。此外，它还提供了多种指定客户端本地化类型的方式，如通过 Cookie、Session 指定。

#### 本地化解析器

事实上，当收到请求时，Spring MVC 在上下文中寻找一个本地化解析器（LocaleResolver），找到后使用它获取请求对应的本地化类型信息。

- AcceptHeaderLocaleResolver：根据HTTP报文头的Accept-Language参数确定本地化类型。如果没有显示指定本地化解析器，则默认使用 AcceptHeaderLocaleResolver
- CookieLocaleResolver：根据指定的 Cookie 值确定本地化类型。
- SessionLocaleResolver：根据Session中特定的属性值确定本地化类型。

#### 本地化拦截器

除此之外，Spring MVC 还允许装配一个动态更改本地化类型的拦截器，这样通过制定一个请求参数就可以控制单个请求的本地化类型。

- LocaleChangeInterceptor：从请求参数中获取本次请求对应的本地化类型。

请求参数如下格式，参数名可配置：

```url
www.xxx.com?locale=en
```

本地化解析器和拦截器都定义在 org.springframework.web.servlet.i18n 包中，用户可以再 DispatcherServlet 上下文中配置它们。

> 实战经验：
>
> 在实际的 web 应用中，一般将用户个性化配置信息（包括本地化类型信息）保存在数据库中。当用户登录系统是，先加载这些个性化信息并保存在特定的媒介中（如 Cookie 或 Session），这样用户的个性化设置就可以永远生效而不会随着 Cookie 或 Session 的清除或过期而消失。



## 文件上传

Spring MVC 为文件上传提供了直接支持，这种支持是通过即插即用的 MultipartResolver 实现的。

- 注册bean：CommonMultipartResolver

```xml
<bean id="multipartResolver"
      class="org.springframework.web.multipart.commons.CommonsMultipartResolver"
      p:defaultEncoding="UTF-8"
      p:maxUploadSize="5242550"
      p:uploadTempDir="file:/d/temp"/>
```

- 引入文件上传的jar包

```xml
<dependency>
  <groupId>commons-fileupload</groupId>
  <artifactId>commons-fileupload</artifactId>
  <version>1.3.3</version>
</dependency>
<dependency>
  <groupId>commons-io</groupId>
  <artifactId>commons-io</artifactId>
  <version>2.6</version>
</dependency>
```

- 用 MultipartFile 入参接收文件

> 代码示例：
>
> mvc—demo.mvc.controller.FileController



## WebSocket 支持

WebSocket 解决浏览器与服务器全双工通信的问题，服务器和客户端都可以发送消息和接收消息。

> 代码示例：
>
> mvc—demo.mvc.websocket



## 静态资源处理

#### 采用 `<mvc:default-servlet-handler/>`

添加配置后，会在 Spring MVC 上下文中定义一个 DefaultServletHttpRequestHandler，它将充当一个检查员的角色，对进入 DispatcherServlet 的URL进行筛查。如果发现是静态资源的请求，就将该请求由Web应用服务器默认的Servlet处理，如果不是静态资源，则由DispatcherServlet处理。

#### 采用`<mvc:resources/>`

两者的不同：

- `<mvc:default-servlet-handler/>`将静态文件的处理经由Spring MVC 框架交回Web应用服务器。而`<mvc:resources/>` 由Spring MVC框架自己处理静态资源，并添加一些有用的附加功能。
- `<mvc:default-servlet-handler/>`只能将静态资源放在Web容器的根路径下。而`<mvc:resources/>`可将静态资源放在Spring资源服务可以访问到的路径，包括classpath和jar包。
- `<mvc:default-servlet-handler/>` 不需要配置资源文件路径。`<mvc:resources/>` 需要在Spring配置文件中添加资源文件夹的路径。

`<mvc:resources/>` 路径配置：

- Ant 通配符定义路径
- 可以将多个物理路径映射为一个逻辑路径
- 可以设置静态资源的缓存有效时间（会根据配置自动设置好响应报文头的 Expires 和 Cache-Control 值）。接收静态资源的获取请求时，会检查请求头的 LastModified值，只是客户端使用浏览器缓存的数据，而非将静态资源的内容输出到客户端，节省带宽。

> 实战经验：
>
> 发布新版本应用时，服务器端的静态资源文件已经发生变化，但是由于客户端浏览器本身缓存管理机制的问题，客户端并不会从服务器端下载新的资源。一个好的解决办法是：网页中应用静态资源的路径添加应用的发布版本号，这样在发布新的部署版本时，由于版本号的变更造成网页中静态资源路径发生更改，从而这些静态资源成为“新的资源”，客户端浏览器就会下载这个“新的资源”，而不会用缓存中的数据。
>
> - 定义一个Bean的初始化方法 init，容器自动加载Bean。
> - 实现 ServletContextAware 获取 ServletContext，向 ServletContext 动态注册版本号属性。
> - 在配置文件中用 Spring EL 表达式动态引用版本号属性，作为静态资源的逻辑路径。
> - jsp 中也动态引用逻辑路径。



## 装配拦截器

当收到请求时，DispatcherServlet 将请求交给处理器映射（HandlerMapping），让它找出对应该请求的 HandlerExecutionChain 对象。HandlerExecutionChan 是一个执行链，包含处理该请求的处理器 Handler，同事包含若干个对该请求实施拦截的拦截器。当HandlerMapping 返回 HandlerExecutionChain  后，DispatcherServlet 将请求交给定义在 HandlerExecutionChain  中的拦截器和处理器一并处理。

#### 拦截器和处理器的处理流程

如图，可以定义前处理方法和后处理方法，分别在 handle 执行的前后执行。

![拦截器.png](https://upload-images.jianshu.io/upload_images/1341067-3f8fe63324a19d1e.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)



## 异常处理

- DefaultHandlerExceptionResolver：处理系统异常
- AnnotationMethodHandlerExceptionResolver ：标注 @ExceptionHandler 注解，处理同一个 controller 其他方法的异常
- SimpleMappingExceptionResolver：全局异常统一处理
- 自定义异常处理器

