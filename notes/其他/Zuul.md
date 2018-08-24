# Zuul 文档

摘自-----Zuul‘s wiki of github: https://github.com/Netflix/zuul/wiki

## Why did we build Zuul 功能概览

The volume and diversity of Netflix API traffic sometimes results in production issues arising quickly and without warning. We need a system that allows us to rapidly change behavior in order to react to these situations.

> Netflix API 流量数量大，种类多，有时会导致紧急生产问题，而且没有任何警告。我们需要一个允许我们快速改变行为的系统，以便对这些情况做出反应。

Zuul uses a range of different types of filters that enables us to quickly and nimbly apply functionality to our edge service. These filters help us perform the following functions:

> Zuul 使用一系列不同类型的过滤器，灵活配置运用于网络边缘。这些过滤器可帮助我们执行以下功能：

Authentication and Security - identifying authentication requirements for each resource and rejecting requests that do not satisfy them.

> - 身份验证和安全性 - 身份验证，并验证每个资源的是否符合用户权限，拒绝不满足权限的请求。

Insights and Monitoring - tracking meaningful data and statistics at the edge in order to give us an accurate view of production.

> - 监控和统计 - 在网络边缘跟踪有意义的统计数据，为我们提供准确的生产视图。

Dynamic Routing - dynamically routing requests to different backend clusters as needed.

> - 动态路由 - 根据需要，动态地将请求路由到不同的后端群集。

Stress Testing - gradually increasing the traffic to a cluster in order to gauge performance.

> - 压力测试 - 逐渐增加群集的流量进行测试，衡量性能。

Load Shedding - allocating capacity for each type of request and dropping requests that go over the limit.

> - 限流 - 为每种类型的请求分配流量，丢弃超过流量限制的请求。

Static Response handling - building some responses directly at the edge instead of forwarding them to an internal cluster

> - 静态响应处理 - 对静态资源直接相应，不转发到后端集群。



## Getting Started 2.0

下载官方 example 运行：

https://github.com/Netflix/zuul/wiki/Getting-Started-2.0



## How It Works 2.0

https://github.com/Netflix/zuul/wiki/How-It-Works-2.0

### Architectural Overview 软件架构

At a high level view, Zuul 2.0 is a Netty server that runs pre-filters (inbound filters), then proxies the request using a Netty client and then returns the response after running post-filters (outbound filters).

> 总览来看，Zuul 2.0 是一个 Netty 服务器，它从互联网接收请求后（request），首先运行入站过滤器（inbound filters），然后运用 Netty Clinet 进行代理，最后进行出站过滤（outbound filters）后返回响应（response）。

![](https://camo.githubusercontent.com/263a4e85f8b9a9e76eb0b61c4cff2b142f9344ec/68747470733a2f2f692e696d6775722e636f6d2f6b5453543948562e706e67)


### Filters

The filters are where the core of the business logic happens for Zuul. They have the power to do a very large range of actions and can run at different parts of the request-response lifecycle as shown in the diagram above.

> 过滤器是Zuul业务逻辑的核心。 他们有能力执行大范围的操作，并且可以在请求 - 响应生命周期的不同部分运行，如上图所示。

- **Inbound Filters** execute before routing to the origin and can be used for things like authentication, routing and decorating the request.

> 入站过滤器：在路由到后端之前执行，可用于身份验证，路由和装饰请求。

- **Endpoint Filters** can be used to return static responses, otherwise the built-in `ProxyEndpoint`filter will route the request to the origin.

> 端点过滤器：可用于返回静态响应，否则内置的ProxyEndpoint过滤器会将请求路由到后端。

- **Outbound Filters** execute after getting the response from the origin and can be used for metrics, decorating the response to the user or adding custom headers.

> 出站过滤器：在从后台获取响应后执行，可用于度量（metrics），装饰对用户的响应，或添加自定义响应头。

There are also two types of filters: sync and async. Since we're running on an event loop, it's **CRITICAL** to never block in a filter. If you're going to block, do so in an async filter, on a separate threadpool -- otherwise you can use sync filters.

> 还有两种类型的过滤器：同步和异步。 由于我们基于事件循环 (event loop) 运行，因此防止过滤器阻塞是至关重要的。 如果您的代码需要阻塞(block)，请使用异步过滤器，在单独的线程池上执行此操作。
>



## Server Configuration 服务器配置

https://github.com/Netflix/zuul/wiki/Server-Configuration

### Server Modes

The currently supported server modes are:

- HTTP
- HTTP/2 (requires TLS)
- HTTP - Mutual TLS

You can see an example of how they're configured in the [sample application](https://github.com/Netflix/zuul/blob/2.1/zuul-sample/src/main/java/com/netflix/zuul/sample/SampleServerStartup.java). You can run the different modes by changing the value of the `SERVER_TYPE` variable.

> 有三种不同的服务器模式，可以通过改变 SERVER_TYPE 的值转换服务器模式。



## Filters 过滤器

https://github.com/Netflix/zuul/wiki/Filters#sample-filters

Filters are are the core of Zuul's functionality. They are responsible for the business logic of the application and can perform a variety of tasks.

> 过滤器是Zuul功能的核心。 他们负责应用程序的业务逻辑，并可以执行各种任务。

- **Type**: most often defines the stage during the routing flow when the Filter will be applied (although it can be any custom string)
- **Async**: define if the filter is sync or async, generally meaning do you need to make an external call or just doing work on-box
- **Execution Order**: applied within the Type, defines the order of execution across multiple Filters
- **Criteria**: the conditions required in order for the Filter to be executed
- **Action**: the action to be executed if the Criteria is met

> - 类型：通常在应用过滤器时在路由流程中定义阶段（尽管它可以是任何自定义字符串）
> - 异步：定义过滤器是同步还是异步
> - 执行顺序：在同一种类型中，定义多个过滤器的执行顺序
> - 标准：执行过滤器的条件
> - 操作：满足条件时要执行的操作

Zuul provides a framework to dynamically read, compile, and run these Filters. Filters do not communicate with each other directly - instead they share state through a RequestContext which is unique to each request.

> Zuul提供了一个动态读取，编译和运行这些过滤器的框架。 过滤器不直接相互通信 - 它们通过RequestContext 共享状态，RequestContext 对每个请求(request)都是唯一的。

Filters are currently written in Groovy, although Zuul supports any JVM-based language. The source code for each Filter is written to a specified set of directories on the Zuul server that are periodically polled for changes. Updated filters are read from disk, dynamically compiled into the running server, and are invoked by Zuul for each subsequent request.

> 过滤器目前用Groovy编写。
>
> 每个Filter的代码都写入Zuul服务器上的一组指定目录，这些目录会定期轮询更改。 
>
> 更新的过滤器从磁盘读取，动态编译到正在运行的服务器中，并由Zuul为每个后续请求调用。

### Incoming

Incoming filters get executed before the request gets proxied to the origin. This is generally where the majority of the business logic gets executed. For example: authentication, dynamic routing, rate limiting, DDoS protection, metrics.

> Incoming filters 在请求被代理到后端之前执行。 这通常是执行大多数业务逻辑的地方。 例如：身份验证，动态路由，速率限制，DDoS保护，指标（metrics）。

### Endpoint

Endpoint filters are responsible for handling the request based on the execution of the incoming filters. Zuul comes with a built-in filter (`ProxyEndpoint`) for proxying requests to backend servers, so the typical use for these filters is for static endpoints. For example: health check responses, static error responses, 404 responses.

> Endpoint filters 负责根据 incoming filters 的执行来处理请求。 Zuul 有一个内置过滤器（ProxyEndpoint），用于代理对后端服务器的请求，因此 Endpoint filters 的典型用途是 static endpoint。 例如：健康检查响应（health check responses），静态错误响应，404响应。

### Outgoing

Outgoing filters handle actions after receiving a response from the backend. Typically they're used more for shaping the response and adding metrics than any heavy lifting. For example: storing statistics, adding/stripping standard headers, sending events to real-time streams, gziping responses.

> Outgoing filters 在从后端接收响应后处理操作。 通常，他们用于加工响应并添加指标，而不是其他繁重的工作。 例如：存储统计信息，添加/剥离标准标头，将事件发送到实时流，gziping响应。

### Async

Filters can be executed either synchronously or asynchronously. If your filter isn't doing a lot of work and is not blocking or going off-box, you can safely use a sync filter by extending `HttpInboundSyncFilter` or `HttpOutboundSyncFilter`.

> 过滤器可以同步或异步执行。 如果您的过滤器没有运行复杂或耗时的代码，没有阻塞或开箱即用，您可以通过扩展 HttpInboundSyncFilter 或 HttpOutboundSyncFilter 安全地使用同步过滤器。

However, in some cases you need to get some data from another service or a cache, or perhaps do some heavy computations. In these cases, you should not block the Netty threads and should use an async filter that returns an `Observable` wrapper for a response. For these filters you would extend the `HttpInboundFilter` or `HttpOutboundFilter`.

> 但是，在某些情况下，您需要从另一个服务或缓存中获取一些数据，或者可能需要进行一些繁重的计算。 在这些情况下，您不应该阻塞 Netty 线程，并且应该使用异步过滤器来返回响应的Observable包装器。 对于这些过滤器，您可以扩展 HttpInboundFilter 或 HttpOutboundFilter。

Example of a sync filter: [Routes](https://github.com/Netflix/zuul/blob/2.1/zuul-sample/src/main/groovy/com/netflix/zuul/sample/filters/inbound/Routes.groovy)

Example of an async filter: [SampleServiceFilter](https://github.com/Netflix/zuul/blob/2.1/zuul-sample/src/main/groovy/com/netflix/zuul/sample/filters/inbound/SampleServiceFilter.groovy)

> 同步过滤器示例：路由 [Routes](https://github.com/Netflix/zuul/blob/2.1/zuul-sample/src/main/groovy/com/netflix/zuul/sample/filters/inbound/Routes.groovy)
>
> 异步过滤器的示例：SampleServiceFilter  [SampleServiceFilter](https://github.com/Netflix/zuul/blob/2.1/zuul-sample/src/main/groovy/com/netflix/zuul/sample/filters/inbound/SampleServiceFilter.groovy)

### Useful Filters

Zuul comes with some useful filters built and examples of other in the [sample app](https://github.com/Netflix/zuul/tree/2.1/zuul-sample).

> 常用过滤器的例子见   [sample app](https://github.com/Netflix/zuul/tree/2.1/zuul-sample)。下面 Sample Filters 和 Core Filters 也是一些例子。

#### Sample Filters

- [DebugRequest](https://github.com/Netflix/zuul/blob/2.1/zuul-sample/src/main/groovy/com/netflix/zuul/sample/filters/inbound/DebugRequest.groovy) - look for a query param to add extra debug logging for a request
- [Healthcheck](https://github.com/Netflix/zuul/blob/2.1/zuul-sample/src/main/groovy/com/netflix/zuul/sample/filters/endpoint/Healthcheck.groovy) - simple static endpoint filter that returns 200, if everything is bootstrapped correctly
- [ZuulResponseFilter](https://github.com/Netflix/zuul/blob/2.1/zuul-sample/src/main/groovy/com/netflix/zuul/sample/filters/outbound/ZuulResponseFilter.groovy) - add informational headers to provide extra details on routing, request execution, status and error cause

#### Core Filters

- [GZipResponseFilter](https://github.com/Netflix/zuul/blob/2.1/zuul-core/src/main/java/com/netflix/zuul/filters/common/GZipResponseFilter.java) - can be enabled to gzip outbound responses
- [SurgicalDebugFilter](https://github.com/Netflix/zuul/blob/2.1/zuul-core/src/main/java/com/netflix/zuul/filters/common/SurgicalDebugFilter.java) - can be enabled to route specific requests to different hosts for debugging



## Core Features 核心功能

https://github.com/Netflix/zuul/wiki/Core-Features

### Service Discovery 服务发现

Zuul is built to work seamlessly with [Eureka](https://github.com/Netflix/eureka) but can also be configured to work with static server lists or a discovery service of your choice.

> Zuul可与Eureka无缝协作，也可配置为使用静态服务器列表，或您选择的发现服务。

The standard approach with a Eureka server would look like this:

> 使用Eureka服务器的标准方法如下所示：

```
### Load balancing backends with Eureka

eureka.shouldUseDns=true
eureka.eurekaServer.context=discovery/v2
eureka.eurekaServer.domainName=discovery${environment}.netflix.net
eureka.eurekaServer.gzipContent=true

eureka.serviceUrl.default=http://${region}.${eureka.eurekaServer.domainName}:7001/${eureka.eurekaServer.context}

api.ribbon.NIWSServerListClassName=com.netflix.niws.loadbalancer.DiscoveryEnabledNIWSServerList
api.ribbon.DeploymentContextBasedVipAddresses=api-test.netflix.net:7001
```

In this configuration you have to specify your Eureka context and location. Given that, Zuul will automatically select the server list from Eureka with the given VIP for the `api` [Ribbon](https://github.com/Netflix/ribbon) client. You can find more info about Ribbon configuration [here](https://github.com/Netflix/ribbon/wiki/Working-with-load-balancers).

> 在此配置中，您必须指定您的Eureka上下文和位置。 配置好后，Zuul将从Eureka自动从具有给定VIP的服务列表，交给 api Ribbon客户端。 

To configure Zuul with a static server list or a different discovery provider, you'll have to keep the the `listOfServers` property up to date:

> 要使用静态服务器列表或其他 discovery provider 配置Zuul，您必须使 listOfServers 属性保持最新：

```
### Load balancing backends without Eureka

eureka.shouldFetchRegistry=false

api.ribbon.listOfServers=100.66.23.88:7001,100.65.155.22:7001
api.ribbon.client.NIWSServerListClassName=com.netflix.loadbalancer.ConfigurationBasedServerList
api.ribbon.DeploymentContextBasedVipAddresses=api-test.netflix.net:7001
```

Notice in this configuration the server list class name is `ConfigurationBasedServerList` instead of `DiscoveryEnabledNIWSServerList`.

> 请注意，在此配置中，服务器列表类名是 ConfigurationBasedServerList 而不是DiscoveryEnabledNIWSServerList。



### Loading Balancing 负载均衡

By default Zuul load balances using the [ZoneAwareLoadBalancer](https://github.com/Netflix/ribbon/blob/master/ribbon-loadbalancer/src/main/java/com/netflix/loadbalancer/ZoneAwareLoadBalancer.java) from Ribbon. The algorithm is a round robin of the instances available in discovery, with availability zone success tracking for resiliency. The load balancer will keep stats for each zone and will drop a zone if the failure rates are above a configurable threshold.

> 默认情况下，Zuul 使用 Ribbon 的 ZoneAwareLoadBalancer 进行负载平衡。 ？？该算法是服务发现实例的循环，可用区域成功跟踪弹性。 负载均衡器将保留每个区域（zone）的统计信息，如果故障率高于配置的阈值，则会丢弃区域。

If you want to use your own custom load balancer, you can set the `NFLoadBalancerClassName`property for that Ribbon client namespace or override the `getLoadBalancerClass()` method in the [DefaultClientChannelManager](https://github.com/Netflix/zuul/blob/2.1/zuul-core/src/main/java/com/netflix/zuul/netty/connectionpool/DefaultClientChannelManager.java). Note that your class should extend [DynamicServerListLoadBalancer](https://github.com/Netflix/ribbon/blob/master/ribbon-loadbalancer/src/main/java/com/netflix/loadbalancer/DynamicServerListLoadBalancer.java).

> 如果要使用自己的自定义负载均衡，可以为该 Ribbon 客户端命名空间设置 NFLoadBalancerClassName 属性，或覆盖 DefaultClientChannelManager 中的 getLoadBalancerClass() 方法。 请注意，您的类应该扩展 DynamicServerListLoadBalancer。

Ribbon also allows you to configure the load balancing rule. For example, you can swap the `RoundRobinRule` for the `WeightedResponseTimeRule`, `AvailabilityFilteringRule`, or your own rule.

> Ribbon 还允许您配置负载平衡规则。 例如，您可以将 RoundRobinRule 替换为WeightedResponseTimeRule，AvailabilityFilteringRule 或您自己的规则。

You can find more details [here](https://github.com/Netflix/ribbon/wiki/Working-with-load-balancers).



### Connection Pooling 连接池

Zuul does **not** use Ribbon for making outgoing connections and instead uses its own connection pool, using a Netty client. **Zuul creates a connection pool per host, per event loop.** It does this in order to reduce context switching between threads and to ensure sanity for both the inbound event loops and outbound event loops. The result is that the entire request is run on the same thread, regardless of which event loop is running it.

> Zuul不使用Ribbon 来进行传出连接，而是使用自己的连接池，使用Netty客户端。 每个事件循环，Zuul为每个主机创建一个连接池。 它这样做是为了减少线程之间的上下文切换，并确保入站事件循环和出站事件循环的健全性。 结果是整个请求在同一个线程上运行，无论哪个事件循环正在运行它。

One of the side-effects of this strategy is that the minumum amount of connections made to each back-end server can be quite high if you have a lot of Zuul instances running, with a lot of event loops in each. This is important to keep in mind when configuring the connection pool.

> 这种策略的一个副作用是，如果你运行了很多Zuul实例，每个后端服务器的最小连接数量可能会非常高，每个实例都有很多事件循环。 配置连接池时请务必记住这一点。

Some useful settings, and their default values, for tweaking the connection pool are:

> 调整连接池的一些有用设置及其默认值为：

**Ribbon Client Config Properties：**

```
<originName>.ribbon.ConnectionTimeout                // default: 500 (ms)
<originName>.ribbon.MaxConnectionsPerHost            // default: 50
<originName>.ribbon.ConnIdleEvictTimeMilliSeconds    // default: 60000 (ms)
<originName>.ribbon.ReceiveBufferSize                // default: 32 * 1024
<originName>.ribbon.SendBufferSize                   // default: 32 * 1024
<originName>.ribbon.UseIPAddrForServer               // default: true
```

**Zuul Properties：**

```
# Max amount of requests any given connection will have before forcing a close
<originName>.netty.client.maxRequestsPerConnection    // default: 1000

# Max amount of connection per server, per event loop
<originName>.netty.client.perServerWaterline          // default: 4

# Netty configuration connection
<originName>.netty.client.TcpKeepAlive                // default: false
<originName>.netty.client.TcpNoDelay                  // default: false
<originName>.netty.client.WriteBufferHighWaterMark    // default: 32 * 1024
<originName>.netty.client.WriteBufferLowWaterMark     // default: 8 * 1024
<originName>.netty.client.AutoRead                    // default: false
```

The connection pool also outputs a lot of metrics, so take a look at the [Spectator](https://github.com/Netflix/spectator) registry if you want to collect them.

> 连接池还会输出大量指标，因此如果要收集它们，请查看Spectator注册表。

### Status Categories 状态码

Although HTTP statuses are universal they don't provide a lot of granularity. In order to have more specific failure modes, we've created an enumeration of possible failures.

> 尽管HTTP状态是通用的，但它们并不提供细粒度。 为了获得更具体的故障模式，我们创建了全面故障的枚举。

| StatusCategory                           | Definition                               |
| ---------------------------------------- | ---------------------------------------- |
| SUCCESS                                  | Successful request                       |
| SUCCESS_NOT_FOUND                        | Succesfully proxied but status was 404   |
| SUCCESS_LOCAL_NOTSET                     | Successful request but no StatusCategory was set |
| SUCCESS_LOCAL_NO_ROUTE                   | Technically successful, but no routing found for the request |
| FAILURE_LOCAL                            | Local Zuul failure (e.g. exception thrown) |
| FAILURE_LOCAL_THROTTLED_ORIGIN_SERVER_MAXCONN | Request throttled due to max connection limit reached to origin server |
| FAILURE_LOCAL_THROTTLED_ORIGIN_CONCURRENCY | Request throttled due to origin concurrency limit |
| FAILURE_LOCAL_IDLE_TIMEOUT               | Request failed due to idle connection timeout |
| FAILURE_CLIENT_CANCELLED                 | Request failed because client cancelled  |
| FAILURE_CLIENT_PIPELINE_REJECT           | Request failed because client attempted to send pipelined HTTP request |
| FAILURE_CLIENT_TIMEOUT                   | Request failed due to read timeout from the client (e.g. truncated POST body) |
| FAILURE_ORIGIN                           | The origin returned a failure (i.e. 500 status) |
| FAILURE_ORIGIN_READ_TIMEOUT              | The request to the origin timed out      |
| FAILURE_ORIGIN_CONNECTIVITY              | Could not connect to origin              |
| FAILURE_ORIGIN_THROTTLED                 | Origin throttled the request (i.e. 503 status) |
| FAILURE_ORIGIN_NO_SERVERS                | Could not find any servers to connect to for the origin |
| FAILURE_ORIGIN_RESET_CONNECTION          | Origin reset the connection before the request could complete |

You can get or set the status using the `StatusCategoryUtils` class. For example:

> 用 `StatusCategoryUtils`  类设置状态，如下：

```
// set
StatusCategoryUtils.setStatusCategory(request.getContext(), ZuulStatusCategory.SUCCESS)
// get
StatusCategoryUtils.getStatusCategory(response)
```



### Retries

One of the key features Netflix uses for resiliency is retries. In Zuul, we take retries seriously and make extensive use of them. We use the following logic to determine when to retry a request:

> Netflix 保证灵活性（resiliency）的关键功能之一是重试（retries）。 在 Zuul，我们认真对待重试并广泛使用它们。 我们使用以下逻辑来确定何时重试请求：

#### Retry on errors

- If error is read timeout, reset connection or connect error

> 如果读取超时，则重置连接或返回连接错误

#### Retry on status codes

- If the status code is 503
- If the status code is a configurable idempotent status (see below) and method is one of: `GET`, `HEAD` or `OPTIONS`

We **don't** retry if we are in a transient state, more specifically:

- If we have started to send the response back to the client
- If we have lost any body chunks (partially buffered or truncated bodies)

> 下列情况需要重试：
>
> - 错误码 503
> - 请求（request）是幂等的，如：GET，HEAD 或 OPTIONS
>
> 如果处于暂态状态，不会重试：
>
> - 如果我们已经开始将响应发送回客户端
> - 如果我们丢失了任何块（body chunks）（部分缓存或请求块 partially buffered or truncated bodies）

Associated properties:

```
# Sets a retry limit for both error and status code retries
<originName>.ribbon.MaxAutoRetriesNextServer            // default: 0

# This is a comma-delimited list of status codes
zuul.retry.allowed.statuses.idempotent                  // default: 500
```


### Request Passport

One of our best tools for debugging is the request passport. It is a time-ordered set of all of the states that a request transitioned through, with the associated timestamps in nanoseconds.

> 我们最好的调试工具之一是请求护照（request passport）。 它是请求转换的所有状态的按时间排序的集合，相关的时间戳以纳秒为单位。

#### Example of successful request

This is a simple request that runs some filters, does some IO, proxies the request, runs filters on the response and then writes it out to the client.

> 这是一个简单的请求，它运行一些过滤器，一些IO，代理请求，在响应上运行过滤器，然后将其写入客户端。

```
CurrentPassport {start_ms=1523578203359,
[+0=IN_REQ_HEADERS_RECEIVED,
+260335=FILTERS_INBOUND_START,
+310862=IN_REQ_LAST_CONTENT_RECEIVED,
+1053435=MISC_IO_START,
+2202112=MISC_IO_STOP,
+3917598=FILTERS_INBOUND_END,
+4157288=ORIGIN_CH_CONNECTING,
+4218319=ORIGIN_CONN_ACQUIRE_START,
+4443588=ORIGIN_CH_CONNECTED,
+4510115=ORIGIN_CONN_ACQUIRE_END,
+4765495=OUT_REQ_HEADERS_SENDING,
+4799545=OUT_REQ_LAST_CONTENT_SENDING,
+4820669=OUT_REQ_HEADERS_SENT,
+4822465=OUT_REQ_LAST_CONTENT_SENT,
+4830443=ORIGIN_CH_ACTIVE,
+20811792=IN_RESP_HEADERS_RECEIVED,
+20961148=FILTERS_OUTBOUND_START,
+21080107=IN_RESP_LAST_CONTENT_RECEIVED,
+21109342=ORIGIN_CH_POOL_RETURNED,
+21539032=FILTERS_OUTBOUND_END,
+21558317=OUT_RESP_HEADERS_SENDING,
+21575084=OUT_RESP_LAST_CONTENT_SENDING,
+21594236=OUT_RESP_HEADERS_SENT,
+21595122=OUT_RESP_LAST_CONTENT_SENT,
+21659271=NOW]}
```

#### Example of a timeout

This is an example of a timeout. It's similar to the previous example but not the time gap between the outbound request and timeout event.

> 这是一个超时的例子。 它与前面的示例类似，但不是出站请求和超时事件之间的时间差。

```
CurrentPassport {start_ms=1523578490446,
[+0=IN_REQ_HEADERS_RECEIVED,
+139712=FILTERS_INBOUND_START,
+1364667=MISC_IO_START,
+2235393=MISC_IO_STOP,
+3686560=FILTERS_INBOUND_END,
+3823010=ORIGIN_CH_CONNECTING,
+3891023=ORIGIN_CONN_ACQUIRE_START,
+4242502=ORIGIN_CH_CONNECTED,
+4311756=ORIGIN_CONN_ACQUIRE_END,
+4401724=OUT_REQ_HEADERS_SENDING,
+4453035=OUT_REQ_HEADERS_SENT,
+4461546=ORIGIN_CH_ACTIVE,
+45004599181=ORIGIN_CH_READ_TIMEOUT,
+45004813647=FILTERS_OUTBOUND_START,
+45004920343=ORIGIN_CH_CLOSE,
+45004945985=ORIGIN_CH_CLOSE,
+45005052026=ORIGIN_CH_INACTIVE,
+45005246081=FILTERS_OUTBOUND_END,
+45005359480=OUT_RESP_HEADERS_SENDING,
+45005379978=OUT_RESP_LAST_CONTENT_SENDING,
+45005399999=OUT_RESP_HEADERS_SENT,
+45005401335=OUT_RESP_LAST_CONTENT_SENT,
+45005486729=NOW]}
```

#### Example of a failed request

This is an example of a request that caused an exception. Again, it's similar to the previous ones, but note the retries and exception events.

> 这是导致异常的请求的示例。 同样，它与之前的类似，但请注意重试和异常事件。

```
CurrentPassport {start_ms=1523578533258,
[+0=IN_REQ_HEADERS_RECEIVED,
+161428=FILTERS_INBOUND_START,
+208805=IN_REQ_LAST_CONTENT_RECEIVED,
+934637=MISC_IO_START,
+1751747=MISC_IO_STOP,
+2606657=FILTERS_INBOUND_END,
+2734497=ORIGIN_CH_CONNECTING,
+2780877=ORIGIN_CONN_ACQUIRE_START,
+3181771=ORIGIN_CH_CONNECTED,
+3272876=ORIGIN_CONN_ACQUIRE_END,
+3376958=OUT_REQ_HEADERS_SENDING,
+3405924=OUT_REQ_LAST_CONTENT_SENDING,
+3557967=ORIGIN_RETRY_START,
+3590208=ORIGIN_CH_CONNECTING,
+3633635=ORIGIN_CONN_ACQUIRE_START,
+3663060=ORIGIN_CH_CLOSE,
+3664703=OUT_REQ_HEADERS_ERROR_SENDING,
+3674443=OUT_REQ_LAST_CONTENT_ERROR_SENDING,
+3681289=ORIGIN_CH_ACTIVE,
+3706176=ORIGIN_CH_INACTIVE,
+4022445=ORIGIN_CH_CONNECTED,
+4072050=ORIGIN_CONN_ACQUIRE_END,
+4144471=OUT_REQ_HEADERS_SENDING,
+4171228=OUT_REQ_LAST_CONTENT_SENDING,
+4186672=OUT_REQ_HEADERS_SENT,
+4187543=OUT_REQ_LAST_CONTENT_SENT,
+4192830=ORIGIN_CH_ACTIVE,
+4273401=ORIGIN_CH_EXCEPTION,
+4274124=ORIGIN_CH_EXCEPTION,
+4303020=ORIGIN_CH_IO_EX,
+4537569=FILTERS_OUTBOUND_START,
+4646348=ORIGIN_CH_CLOSE,
+4748074=ORIGIN_CH_INACTIVE,
+4957163=FILTERS_OUTBOUND_END,
+4968947=OUT_RESP_HEADERS_SENDING,
+4985532=OUT_RESP_LAST_CONTENT_SENDING,
+5003476=OUT_RESP_HEADERS_SENT,
+5004610=OUT_RESP_LAST_CONTENT_SENT,
+5062221=NOW]}

```

You can log the passport, add it to a header or ship it off to a persistent store for later debugging. To get it out of the request you can either use the channel or the session context. For example:

> 您可以记录护照，将其添加到标题或将其发送到持久性存储以供以后调试。 要将其从请求中删除，您可以使用通道或会话上下文。 例如：

```
// from channel
CurrentPassport passport = CurrentPassport.fromChannel(channel);
// from context
CurrentPassport passport = CurrentPassport.fromSessionContext(context);
```

### Request Attempts

Another very useful debugging feature is tracking the request attempts that Zuul makes. We typically add this as an internal-only header on every response, and it makes tracing and debugging requests much simpler for us and our internal partners.

> 另一个非常有用的调试功能是跟踪 Zuul 的请求尝试。 我们通常将此作为每个响应的内部标头添加，它使我们和内部合作伙伴的跟踪和调试请求更加简单。

#### Example of successful request

```
[{"status":200,"duration":192,"attempt":1,"region":"us-east-1","asg":"simulator-v154","instanceId":"i-061db2c67b2b3820c","vip":"simulator.netflix.net:7001"}]
```

#### Example of failed request

```
[{"status":503,"duration":142,"attempt":1,"error":"ORIGIN_SERVICE_UNAVAILABLE","exceptionType":"OutboundException","region":"us-east-1","asg":"simulator-v154","instanceId":"i-061db2c67b2b3820c","vip":"simulator.netflix.net:7001"},
{"status":503,"duration":147,"attempt":2,"error":"ORIGIN_SERVICE_UNAVAILABLE","exceptionType":"OutboundException","region":"us-east-1","asg":"simulator-v154","instanceId":"i-061db2c67b2b3820c","vip":"simulator.netflix.net:7001"}]
```

You can get the request attempts from the session context on an **outbound** filter. For example:

```
// from context
RequestAttempts attempts = RequestAttempts.getFromSessionContext(context);
```
### Origin Concurrency Protection

Sometimes origins can get into trouble, particularly when the volume of requests exceeds their capacity. Given that we're a proxy, a bad origin could potentially affect other origins by saturating our connections and memory. In order to protect origins and Zuul, we have concurrency limits in place to help smooth our service interruptions.

> 有时后台可能会遇到麻烦，特别是当请求量超过其容量时。 鉴于我们是一个代理，一个不好的集群可能会通过占用连接和内存来影响其他起源。 为了保护后台服务器和Zuul，我们有适当的并发限制使我们的服务平稳中断。

There are two ways we manage origin concurrency:

#### Overall Origin Concurrency

```
zuul.origin.<originName>.concurrency.max.requests        // default: 200
zuul.origin.<originName>.concurrency.protect.enabled     // default: true
```

#### Per Server Concurrency

```
<originName>.ribbon.MaxConnectionsPerHost                // default: 50
```

If an origin exceeds overall concurrency or per-host concurrency, Zuul will return a 503 to the client.

> 如果后台超过整体并发或每主机并发，Zuul 将向客户端返回503。

### HTTP/2

Zuul can run in HTTP/2 mode as demonstrated in the [sample app](https://github.com/Netflix/zuul/blob/2.1/zuul-sample/src/main/java/com/netflix/zuul/sample/SampleServerStartup.java#L109). In this mode, it requires an SSL cert and if you're going to run Zuul behind an ELB you'll have to use the TCP listener.

> Zuul可以在HTTP / 2模式下运行，如示例应用程序中所示。 在这种模式下，它需要SSL证书，如果你要在ELB后面运行Zuul，你将不得不使用TCP监听器。

Relevant HTTP/2 properties:

```
server.http2.max.concurrent.streams            // default: 100
server.http2.initialwindowsize                 // default: 5242880
server.http2.maxheadertablesize                // default: 65536
server.http2.maxheaderlistsize                 // default: 32768
```

### Mutual TLS

Zuul can run in Mutual TLS mode as demonstrated in the [sample app](https://github.com/Netflix/zuul/blob/2.1/zuul-sample/src/main/java/com/netflix/zuul/sample/SampleServerStartup.java#L133). In this mode, you'll have to have both an SSL cert and trust store for incoming certs. As with HTTP/2, you'll have to run this behind the TCP listener of the ELB.

> Zuul可以在Mutual TLS模式下运行，如示例应用程序中所示。 在此模式下，您必须同时拥有SSL证书和信任存储区以用于传入证书。 与HTTP / 2一样，您必须在ELB的TCP侦听器后面运行它。

### Proxy Protocol

Proxy protocol is an important feature when using a TCP listener and can be enabled in Zuul using the follow server config properties:

> 使用TCP侦听器时，代理协议是一项重要功能，可以使用以下服务器配置属性在Zuul中启用：

```
// strip XFF headers since we can no longer trust them
channelConfig.set(CommonChannelConfigKeys.allowProxyHeadersWhen, StripUntrustedProxyHeadersHandler.AllowWhen.NEVER);

// prefer proxy protocol when available
channelConfig.set(CommonChannelConfigKeys.preferProxyProtocolForClientIp, true);

// enable proxy protocol
channelConfig.set(CommonChannelConfigKeys.withProxyProtocol, true);
```

The client IP will be set correctly on the `HttpRequestMessage` in the filters, and can also be retrieved directly from the channel:

> 客户端IP将在过滤器中的HttpRequestMessage上正确设置，也可以直接从通道中检索：

```
String clientIp = channel.attr(SourceAddressChannelHandler.ATTR_SOURCE_ADDRESS).get();
```

### GZip

Zuul comes with an outbound [GZipResponseFilter](https://github.com/Netflix/zuul/blob/2.1/zuul-core/src/main/java/com/netflix/zuul/filters/common/GZipResponseFilter.java) that will gzip outgoing responses.

It makes the decision based on content type, body size and whether the request `Accept-Encoding`header contains `gzip`.

> Zuul带有一个出站GZipResponseFilter，它将gzip传出响应。
>
> 它根据内容类型，正文大小以及请求 Accept-Encodingheader 是否包含 gzip 做出决定。



## Push Messaging

Zulu 2.0 supports push messaging - i.e. sending messages from server to client. It supports two protocols, WebSockets and Server Sent Events (SSE) for sending push messages.

Our [`sample app`](https://github.com/Netflix/zuul/blob/2.1/zuul-sample/src/main/java/com/netflix/zuul/sample/SampleServerStartup.java) demonstrates set up of both WebSockets as well as SSE to enable push messaging for Zuul.

> Zulu 2.0支持推送消息传递 - 即从服务器向客户端发送消息。 它支持两种协议，WebSockets和用于发送推送消息的服务器发送事件（SSE）。
>
> 我们的示例应用程序演示了WebSockets和SSE的设置，以便为Zuul启用推送消息传递。
