## 控制反转和依赖注入

> <u>Question：什么是 Spring IoC 容器，它的优点是什么？</u> 

Spring 的 IoC 功能将类与类之间的依赖从代码中脱离出来，用配置的方式进行依赖关系描述。

用 new 的方式创建依赖的对象，在接口有多个实现时不能灵活切换，导致两个类之间的高耦合。



## 依赖注入的方式

#### 按照配置方式分类

- 基于 XML 配置

  Bean的实现类来源于第三方类库，如 DataSource、JdbcTemplate 等，无法在类中标注注解，所以通过XML配置方式比较好。

  XML 可以实现较复杂的配置；可以去除代码对 Spring 的依赖；将配置与代码分离，方便在不修改代码功能的情况下修改配置。

- 基于注解配置

  Bean的实现类是当前项目开发的，可以直接在 Java类中使用基于注解的配置。

  基于注解的配置相较于XML写法简洁；不用频繁修改配置文件，哪个类的职责由哪个类解决，方便扩展和删除功能模块。


- 基于 Java 类配置

  可以通过代码的方式控制Bean初始化的整体流程。如果实例化Bean的逻辑比较复杂，则比较适合基于Java的类配置。

- 基于 Groovy DSL 配置

  可以通过 Groovy 脚本灵活控制Bean的初始化过程。如果实例化Bean的逻辑比较复杂，则比较适合基于 Groovy DSL 的配置。

#### 按照注入方式分类

**构造器注入**

支持构造器注入的理由是：

- 构造函数可以保证一些重要的属性在Bean实例化时设置好，避免因为一些重要属性没有提供导致一个无用Bean实例的情况。
- 不需要为每个属性提供Setter方法，减少了类的方法个数。
- 可以更好地封装变量，不需要为每个属性指定Setter方法，避免外部错误的调用。

**属性注入**

支持属性注入而反对构造器注入的理由是：

- 如果一个类的属性众多，那么构造函数的签名将变成一个庞然大物，可读性很差。
- 灵活性不强，在有些属性是可选的情况下，如果通过构造函数注入，也需要为可选的参数提供一个null值。
- 如果有多个构造函数，需要考虑配置文件和具体构造函数匹配歧义的问题，配置相对复杂。
- 构造函数不利于类的继承和扩展，因为子类需要引用父类复杂的构造函数。
- 构造函数注入有时会造成循环依赖的问题。

**构造器注入和属性注入的选择**

- 在类的属性众多，或者多个构造函数的情况下，推荐使用属性注入。这样可以增加代码的可读性，减少代码错误率。

**工厂方法注入**

**不推荐工厂方法**，这需要编写额外的类和代码。工厂方法是经常被使用的设计模式，也是控制反转和单实例设计思想的主要实现方式。Spring 容器以框架的方式提供工厂方法的功能，已经成为底层设施的一部分。Spring 已经用更优雅的方式实现了传统工厂模式的所有功能，我们大可不必做这些重复的工作。



## 具体配置

#### Bean 定义

定义Bean后，Spring 容器将为相应的类创建一个实例（默认singleton）

- XML：在XML文件中通过 bean 元素定义。
```xml
<Bean id="car" class="com.smart.simple.Car"/>
```
- 注解：在 Bean 实现类处通过标注 @Component 或衍型类（@Respository、@Service、@Controller）定义Bean。

#### Bean 名称 

id 和 name 都可以指定 Bean 的名称，尽量使用 id 对 Bean 进行命名。

- XML：通过<bean>的 id 或 name 属性定义，默认名称为全限定类名，如：com.smart.simple.Car
- 注解：通过注解的value属性定义，如@Component("car")。默认名称为小写字母开头的类名（不含包名）。

```
1. Spring 配置文件不允许存在两个相同的id，可以允许存在两个相同的name。如果有多个name相同的bean，则后定义的会覆盖先定义的。
2. 一个bean可以允许定义多个id和name。
3. 默认名称的多个bean可以通过#加上数字指定。
```

#### Bean 注入

设置 Bean 的成员变量，包括所依赖的对象。

- XML：通过 <property> 子元素或通过 p 命名空间的动态属性

```xml
<Bean id="car" class="com.smart.simple.Car">
  <!-- 属性注入 -->
  <property name="maxSpped" value="200"/>
  <property name="flag" ref="flag"/>
  <!-- 构造器注入 -->
  <constructor-arg type="java.lang.String" value="red flag"/>
</Bean>

<!-- p 命名空间 -->
<bean name="john-modern" class="com.example.Person" p:name="John Doe" p:spouse-ref="jane"/>
<bean name="jane" class="com.example.Person" p:name="Jane Doe"/>
```

- 注解：在成员变更或方法入参处标注 @Autowire，按照类型自动匹配注入。还可以配合使用@Qualifier，按名称匹配方式注入。

```
1. 构造函数注入可以按类型和索引匹配入参。
2. 属性注入按变量名匹配。
```

#### Bean 生命过程方法

定义Bean的初始化方法和销毁方法等生命周期相关的方法。

- XML：通过<bean> 的 init-method 和destroy-method 属性指定 Bean 实现类的方法名。最多只能指定一个初始化方法和一个销毁方法。
- 注解：通过在目标方法上标注 @PostContruct 和 @PreDestroy，可指定多个。

#### Bean 作用范围

配置 Bean 的作用范围，需要配合Web不同作用范围上下文的监听器配置

- XML：通过<bean> 的scope属性指定。
- 注解：通过在类定义出标注@Scope指定，如 @Scope("prototype")。

| 类型        | 说明                                       |
| --------- | ---------------------------------------- |
| singleton | 在 Spring 容器中仅存在一个Bean实例，Bean 以单例的方式存在。   |
| prototype | 每次从容器中调用Bean时，都返回一个新的实例，即每次调用 getBean() 时，相当于执行 new XxxBean() 的操作。 |
| request   | 每次 HTTP 请求都会创建一个新的 Bean，该作用域仅适用与 WebApplicationContext 环境。 |
| session   | 同一个 HTTP Session 共享一个 Bean，不同 HTTP Session 使用不同的Bean。该作用域仅适用与 WebApplicationContext 环境。 |

#### Bean 延迟初始化

延迟初始化，即在Spring容器启动时，不会立刻注入属性值，而是延迟到调用此属性的时候才会注入属性值。

- XML：通过<bean> 的 lazy-inti 属性指定，默认为default，继承于<beans> 的 default-lazy-init 设置，该值默认为 false。
- 通过在类定义处标注 @Lazy 指定，如 @Lazy(true)

#### 特殊配置

- XML 中特殊字符的处理：1. 用<![CDATA[]]>标签将特殊字符封装起来。2. 用转义序列表示特殊字符
- 内部 Bean、级联属性可以简化配置。
- 如果要将属性设置为null，用<null/>表示。
- 可以配置集合类型的属性：List、Set、Map、Properties
- lookup 方法注入：可解决通过一个singletonBean获取一个prototypeBean时使用。
- 方法替换：用新建 Bean 的方法替换已有 Bean 的方法
- Bean 之间可以配置继承关系继承父 Bean 的属性



