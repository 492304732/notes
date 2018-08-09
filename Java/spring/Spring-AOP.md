## 面向切面

按照软件重构的概念，如果多各类中出现重复代码，则应该考虑定义一个父类，将这些相同的代码提取到父类中。但如性能监控和事务管理这些非业务性代码包围业务代码的情况，无法用提取父类完成，这就需要用AOP（面向切面编程）的思想来解决。

如下图，业务代码像是圆木的树心，性能监控和事务管理的代码像一个年轮，这也正是横切代码概念的由来。不能通过抽象父类的方式消除这些重复代码，因为这些横切逻辑依附在业务方法的流程中，不能转移到其他方法中。

![横切逻辑示意图.png](https://upload-images.jianshu.io/upload_images/3041231-1f518783a9637b57.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)=

> 总结一下：
>
> - 继承：纵向抽取公共代码
> - AOP：横向抽取公共代码



### AOP 的应用场景

- 权限控制、缓存控制、事务控制、审计日志、性能监控、分布式追踪、异常处理



## 动态代理

### 代理模式

代理模式：

- 代理类和委托类实现同样的接口，客户端面向接口调用而不关心实现
- 可以通过给代理类增加额外的功能来扩展委托类的功能。可以在业务功能执行的前后加入一些公共的服务，如加入缓存、日志这些功能。

静态代理：

- 可以在不影响业务逻辑类的情况下修改扩展功能，让业务代码纯净地存在。
- 我们得为每一个服务都得创建代理类，工作量太大，不易管理。同时接口一旦发生改变，代理类也得相应修改。

动态代理：

- 静态代理手动编写代理类，在编译时class文件就已经生成。动态代理只需要编写动态处理器，真正的代理对象在Java 运行时动态创建。

### JDK 动态代理

代码示例见 springdemo 项目中 com.springdemo.demo.proxy

- 通过实现 InvocationHandler 接口的 invoke 方法，将横切逻辑代码和业务类方法的业务逻辑代码交织在一起。
- 通过 JDK 的 Proxy.newProxyInstance 创建代理类，可以为不同的接口类交织相同的横切逻辑。

### CGLib 动态代理

使用 JDK 创建代理有一个限制，即它只能为接口创建代理实例。对于没有通过接口定义业务方法的类，可以用GCLib 技术实现动态代理。

GCLib 采用底层的字节码技术，可以为一个类创建子类，再子类中采用方法拦截的技术拦截所有父类方法的调用并顺势植入横切逻辑。GCLib采用动态创建子类的方式生成代理对象，所以不能对目标类中的final或private方法进行代理。

> 总结一下：
>
> - JDK 允许开发者在运行期创建接口的代理实例。
> - GCLib 通过动态创建子类的方式生成代理对象。

### 动态代理比较

如何选择 JDK 和 CGLib：

- GCLib 所创建的动态代理对象性能比 JDK 所创建的动态代理对象性能高不少（大概10倍）。但 CGLib 在创建代理对象时花费的时间却比 JDK 动态代理多（大概8倍）。
- 对于 Singleton 的代理对象或者具有实例池的代理，因为无需频繁地创建代理对象，所以比较适合采用CGLib 动态代理技术，反之则适合采用 JDK 动态代理技术。

JDK 和 CGLib 的不足：

- 目标类的所有方法都添加了监控逻辑，但有时我们只希望对某些特定的方法添加横切逻辑。
- 只能在目标类方法的开始和结束前织入代码。
- 需要手工编写代理实例的创建过程，为不同的类创建代理时需要分别编写创建代码，不能做到通用。

Spring 基于JDK 或 CGLib 的动态代理，增强了AOP的功能。



## Spring 面向切面编程

### AOP 术语

- 连接点（Joinpoint）：在 Spring AOP 中，指具体需要增强的方法。
- 切点（Pointcut）：一个切点包含多个连接点。切点指定了一些过滤条件，可以从所有方法中筛选出符合条件的。
- 增强（Advice）：增强是织入目标连接点上的一段程序代码，确定代码的织入时机，如前置增强、后置增强、环绕增强等。通过切点和增强，可以定义连接点。
- 切面（Aspect）：切面由切点和增强组成，它即包括横切逻辑的定义，也包括连接点的定义。

### 增强类型

增强（Adviser）包含了横切代码，和描述织入时机（方法前、方法后主方位信息）。

- 前置增强：目标方法执行之前增强
- 后置增强：目标方法执行之后增强
- 环绕增强：目标方法执行前后增强
- 异常抛出增强：目标方法抛出异常后实施增强
- 引介增强：在目标类中添加新的方法和属性


### 创建切面

#### 切点（Pointcut）

切点描述了横切代码要织入到哪些类的哪些方法上。

Spring 通过 Pointcut 接口描述切点，Pointcut 通过 ClassFilter 和 MethodMatcher 构成，它通过 ClassFilter 定位到某些特定的类上，通过 MethodMatcher 定位到某些特定的方法上。

Pointcut 接口关系图如下图所示：

![Pointcut类关系图.png](https://upload-images.jianshu.io/upload_images/1341067-df80308ef0c396f5.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

- ClassFilter 只定义了一个方法 matches(Class clazz)，其参数代表一个被检测类，该方法判别被检测的类是否符合过滤条件。
- MethodMatcher 可定义静态方法匹配器和动态方法匹配器。
  - 静态方法匹配器仅对方法名签名（包括方法名和入参类型及顺序）进行匹配；而动态方法匹配器会在运行期检查方法入参的值。
  - 静态匹配仅会判别一次，而动态匹配因为每次调用方法的入参都可能不一样，所以每次调用方法都必须判断，因此，动态匹配不常使用。
  - 方法匹配器的类型由 isRuntime 方法的返回值决定，返回 false 表示静态方法匹配器，返回true表示动态方法匹配器。
- Spring 还支持注解切点和表达式切点。

> 代码示例：
>
> 动态方法匹配的例子见：aop-executor 工程的 AdvisorTest.testDynamicPoitcut

### 切面

一个切面包含了横切代码、织入哪些类方法、织入的方法方位等信息。

切面类的继承关系如下图：

- Advisor：简单切面，仅包含一个 Adviser（增强）。它代表的连接点是所有目标类的所有方法，比较宽泛，一般不会直接使用。
- PointcutAdvisor: 代表具有切点的切面，包含Adviser 和 Pointcut 两个类。这样就能通过类、方法名和方法方位等信息灵活地定义切面的连接点。
  - PointcutAdvisor 有六个具体实现类，最常用的是 DefaultPointcutAdvisor。它可以用过任意 Pointcut 和 Advisor 定义一个切面，唯一不支持的是引介增强。
- IntroductionAdvisor：代表引介切面，引介切面是对应引介增强的特殊切面，它应用于类层面上，所以引介切点使用 ClassFilter 进行定义。

![切面类继承关系.png](https://upload-images.jianshu.io/upload_images/1341067-d2b215d3ac83e1d5.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

> 代码示例：
>
> 引介切面：aop-executor 工程的 IntroduceTest.introduce
>
> 正则表达式等切面：aop-executor 工程的 AdvisorTest



### 自动创建代理

使用 ProxyFactoryBean 创建实体，需要为每一个 bean 单独配置。为了避免为每一个 bean 配置代理，可以使用自动代理创建器。

- BeanNameAutoProxyCreator：允许用户制定一组需要自动代理的Bean名称（可以使用通配符），还可指定一个 Adviser（增强）。
- DefaultAdvisorAutoProxyCreator：基于 Advisor 匹配机制的自动代理创建器，它会对容器中的Advisor进行扫描，自动将这些切面应用到匹配的Bean中（为目标Bean创建代理实例）。

> 代码示例：
>
> 见 aop-executor 工程的 CreatorTest

