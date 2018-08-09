## 基本模块

![](https://images2015.cnblogs.com/blog/947629/201608/947629-20160825181651335-590863117.png)

- SpringCore 是Spring框架的核心模块，提供spring框架的基本功能，使用工厂模式BeanFactory通过控制反转（IoC）、依赖注入（DI）等实现对beans的管理功能，将对象间的耦合关系通过配置文件进行管理，实现了“低耦合”的程序设计原则。
- SpringContext 通过配置文件的方式向spring提供上下文服务，如 JNDI、国际化、校验等
- SpringDAO 是spring框架对数据访问的抽象，封装了对JDBC的操作，统一了异常结构用于管理不同数据库厂商产品抛出的错误信息，简化了对异常信息的处理。
- SpringORM 负责spring与ORM框架的集成，如Hibernate、MyBatis等。
- SpringWeb 是 spring 的 Web 模块，提供 WebApplication 的上下文信息，实现如文件上传、数据绑定、与其他框架（如Struts）的集成等。
- SpringWebMVC 是一个Web的MVC框架，提供了对Controller、Model、Service等组件的管理，视图层通过不同的视图解析器支持多种视图技术，如JSP、Velocity、FreeMarker等
- SpringAOP 是Spring对面向切面编程的支持，支持JDK和CGLib两种字节码操作方式，可以实现对Spring管理的任意对象的AOP支持。