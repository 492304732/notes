### 一、maven profile 元素介绍

参考博客：https://www.cnblogs.com/0201zcr/p/6262762.html

#### 1、背景：
在开发过程中，我们的软件会面对**不同的运行环境**，比如开发环境、测试环境、生产环境，而我们的软件在不同的环境中，有的配置可能会不一样，比如数据源配置、日志文件配置、以及一些软件运行过程中的基本配置，那每次我们将软件部署到不同的环境时，都需要修改相应的配置文件，这样来回修改，很容易出错，而且浪费劳动力。maven提供了一种方便的解决这种问题的方案，就是profile功能。

#### 2、简介：
profile可以让我们定义一系列的配置信息，然后指定其激活条件。这样我们就可以定义多个profile，然后每个profile对应不同的激活条件和配置信息，从而达到不同环境使用不同配置信息的效果。 profile 可定义特定项目、特定用户、全局的配置。其中特定项目的配置定义在项目的pom文件中。

#### 3、profile 配置：
这里定义了三个环境，分别是dev（开发环境）、beta（测试环境）、release（发布环境），其中开发环境是默认激活的（activeByDefault为true），这样如果在不指定profile时默认是开发环境，也在package的时候显示指定你要选择哪个开发环境，详情见后面。

```xml
<profiles>
    <profile>
        <!-- 本地开发环境 -->
        <id>dev</id>
        <properties>
            <profiles.active>dev</profiles.active>
        </properties>
        <activation>
            <!-- 设置默认激活这个配置 -->
            <activeByDefault>true</activeByDefault>
        </activation>
    </profile>
    <profile>
        <!-- 发布环境 -->
        <id>release</id>
        <properties>
            <profiles.active>release</profiles.active>
        </properties>
    </profile>
    <profile>
        <!-- 测试环境 -->
        <id>beta</id>
        <properties>
            <profiles.active>beta</profiles.active>
        </properties>
    </profile>
</profiles> 
```

#### 4、配置文件：

　　针对不同的环境，我们定义了不同的配置文件。开发环境、测试环境、生产环境的配置文件分别放到src/main/resources目录下的config文件夹下。config下有多个环境的配置文件，命名规则为是application-环境名称.properties。

![配置文件路径](https://images2015.cnblogs.com/blog/731178/201701/731178-20170109093008306-1225533526.png)

处理过程：

1）通过profile选中你要使用的环境

2）通过package命令，将环境变量注入到application.properties中（这样子，那些公用的环境变量就不用在各个环境的配置文件中配置了）

3）项目中加载 application.xml 文件

加载配置文件：

```xml
<context:property-placeholder location="classpath:application.properties"/>
```

application-beta.properties文件有如下部分内容：

```xml
env.datasource.jdbcUrl=jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF-8&rewriteBatchedStatements=true&autoReconnect=true&zeroDateTimeBehavior=round
env.datasource.username=root
env.datasource.password=12233
```

application.properties有部分如下内容用于注入上面的内容：

```xml
datasource.jdbcUrl=${env.datasource.jdbcUrl}
datasource.username=${env.datasource.username}
datasource.password=${env.datasource.password}
```

#### 5、指定资源文件的位置：

在pom.xml的build结点下，配置资源文件的位置，如下所示：

```xml
<build>
        <finalName>seewo-admin</finalName>
        <!-- 定义了变量配置文件的地址 -->
        <filters>
            <filter>src/main/resources/config/application-${env}.properties</filter>
        </filters>
        <resources>
            <resource>
                <directory>src/main/resources</directory>
                <filtering>true</filtering>
            </resource>
        </resources>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-war-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
```

这里**注意一个参数<filtering>true</filtering>，一定要设置成true.**这样才会用对应env目录下的配置文件覆盖原来的。

#### 6、激活 profile

1）默认的激活

　　上面的profile配置中设置的默认的激活环境。

```xml
<activeByDefault>true</activeByDefault> 
```

2）使用-P参数显示激活一个profile

　　当我们在进行Maven操作时就可以使用-P参数显示的指定当前激活的是哪一个profile了。比如我们需要在对项目进行打包的时候使用id为dev的profile，我们就可以这样做：

```xml
mvn package –Pdev
```

这里假设dev是在settings.xml中使用dev标记的处于激活状态的profile，那么当我们使用“-P !profile”的时候就表示在当前操作中该profile将不处于激活状态。



### 二、利用 maven-assembly-plugin 加载不同环境的配置文件

参考博客：http://www.cnblogs.com/lianshan/p/7348093.html

官网资料：https://maven.apache.org/plugins/maven-assembly-plugin/descriptor-refs.html

#### 1、背景

如何加载不同环境的配置文件已经成了势在必行的，我们通常利用profile进行，但是单单的profile实在无法满足我们的需求，因为这实在是太简单太单一了，我们将它与maven-assembly-plugin，结合起来，来实现配置分离的问题。

#### 2、profile不同环境参数设置

此参数的配置profile几乎相同，不过是在properties的属性声明将其与assembly结合起来，具体示例如下。

```xml
<profiles>
        <profile>
            <id>dev</id>
            <activation>
                <activeByDefault>true</activeByDefault>
            </activation>
            <properties>
                <env.devMode>dev</env.devMode>
                <skipAssemblyDEV>true</skipAssemblyDEV>
                <skipAssemblyUAT>false</skipAssemblyUAT>
                <skipAssemblyPROD>false</skipAssemblyPROD>
            </properties>
        </profile>
        <profile>
            <id>uat</id>
            <activation>
                <activeByDefault>false</activeByDefault>
            </activation>
            <properties>
                <env.devMode>uat</env.devMode>
                <skipAssemblyDEV>false</skipAssemblyDEV>
                <skipAssemblyUAT>true</skipAssemblyUAT>
                <skipAssemblyPROD>false</skipAssemblyPROD>
            </properties>
        </profile>
    </profiles>
```

我定义了三个环境的相同的四个变量，我在mvn clean  package -P dev 时指定了环境信息，也就相对于制定了变量的值。可以观察到skipAssemblyDEV 、skipAssemblyUAT、 skipAssemblyPROD这三个值都是排他的，聪明的小伙伴已经可能意识到了，一定有三个地方使用了这三个变量，并将他们指向了指定的配置文件。

#### 3、assembly的相关配置

```xml
<!--assembly test start-->
<!--this  include  the xml  assembly-->
<plugin>
  <groupId>org.apache.maven.plugins</groupId>
  <artifactId>maven-assembly-plugin</artifactId>
  <version>3.0.0</version>
  
  <executions>
    <execution>
      <id>make-assembly-dev</id>  <!-- 标志-->
      <phase>package</phase>      <!-- 指定其编译阶段-->
      <goals>
        <goal>single</goal>     <!--The goals to execute with the given configuration-->
      </goals>
      <configuration>
        <skipAssembly>${skipAssemblyDEV}</skipAssembly>   <!--profile声明参数调用-->
        <descriptors>
          <descriptor>src/main/assembly/dev/assembly.xml</descriptor>   <!--加载指定的assembly配置文件-->
        </descriptors>
      </configuration>
    </execution>
    
    <execution>
      <id>make-assembly-uat</id>
      <phase>package</phase>
      <goals>
        <goal>single</goal>
      </goals>
      <configuration>
        <skipAssembly>${skipAssemblyUAT}</skipAssembly> 
        <descriptors>
          <descriptor>src/main/assembly/uat/assembly.xml</descriptor>
        </descriptors>
      </configuration>
    </execution>
    
    <execution>
      <id>make-assembly-prod</id>
      <phase>package</phase>
      <goals>
        <goal>single</goal>
      </goals>
      <configuration>
        <skipAssembly>${skipAssemblyPROD}</skipAssembly>
        <descriptors>
          <descriptor>src/main/assembly/prod/assembly.xml</descriptor>
        </descriptors>
      </configuration>
    </execution>
  </executions>
  
</plugin>
<!--assembly test end-->
```

首先我们通过 profile声明了相应的参数。

skipAssembly 声明值true和false 表明我们是否要执行下列声明的配置文件，官方解释如下：Flag allowing one or more executions of the assembly plugin to be configured as skipped for a particular build. This makes the assembly plugin more controllable from profiles.

#### 4、assembly.xml 相关配置文件

```xml
<assembly>
    <id>assembly-${env.devMode}</id> <!--输出文件名-->
    <formats>
        <format>tar.gz</format> <!--打包文件结构-->
    </formats>
    <includeBaseDirectory>false</includeBaseDirectory>
    <fileSets>
        <fileSet>
            <directory>src/main/assembly/bin</directory>  <!-- 项目文件目录-->
            <outputDirectory>bin</outputDirectory> <!--生成bin目录-->
            <directoryMode>0755</directoryMode> <!--目录执行权限-->
            <fileMode>0755</fileMode><!--文件执行权限-->
        </fileSet>
        <fileSet>
            <directory>src/main/assembly/uat/conf</directory>
            <outputDirectory>conf</outputDirectory>
            <directoryMode>0744</directoryMode>
            <fileMode>0644</fileMode>
        </fileSet>
        <fileSet>
            <directory>lib</directory>
            <outputDirectory>lib</outputDirectory>
            <directoryMode>0744</directoryMode>
            <fileMode>0644</fileMode>
        </fileSet>
    </fileSets>
    <dependencySets>
        <dependencySet>
            <outputDirectory>lib</outputDirectory><!-- 依赖jar包放置目录-->
        </dependencySet>
    </dependencySets>
</assembly>
```



### 三、cjs 项目中的 profile

profiles 配置：分为本地、测试、生产三种不同的配置，默认激活本地环境的配置，打包时在参数中显示激活测试或者生产的配置。

```xml
<!--hkcjs: pom.xml -->
	<profiles>
		<!-- 本地环境 -->
		<profile>
			<id>dev</id>
			<!-- 默认激活dev --> 
			<activation>
				<activeByDefault>true</activeByDefault>
			</activation>
			<properties>
				<hkcjs.uploadDir>D:/nfsc/www/</hkcjs.uploadDir>
				<env.devMode>dev</env.devMode>
				<skipAssemblyDEV>false</skipAssemblyDEV>
				<skipAssemblyUAT>true</skipAssemblyUAT>
				<skipAssemblyPROD>true</skipAssemblyPROD>
			</properties>
		</profile>
		<!-- 测试环境 -->
		<profile>
			<id>uat</id>
			<properties>
				<hkcjs.uploadDir>/home/www/img/</hkcjs.uploadDir>
				<env.devMode>uat</env.devMode>
				<skipAssemblyDEV>true</skipAssemblyDEV>
				<skipAssemblyUAT>false</skipAssemblyUAT>
				<skipAssemblyPROD>true</skipAssemblyPROD>
			</properties>
		</profile>
		<!-- 生产环境 -->
		<profile>
			<id>prod</id>
			<properties>
				<hkcjs.uploadDir>/home/www/img/</hkcjs.uploadDir>
				<env.devMode>prod</env.devMode>
				<skipAssemblyDEV>true</skipAssemblyDEV>
				<skipAssemblyUAT>true</skipAssemblyUAT>
				<skipAssemblyPROD>false</skipAssemblyPROD>
			</properties>
		</profile>
	</profiles>
```


assembly-plugin 配置：指定了本地、生产、测试三个 execution，通过profile中定义的属性决定使用哪一个execution。在 <descriptor> 中指定了配置文件的描述文件路径。
```xml
<!--hkcjs-service: pom.xml -->
<plugin>
  <groupId>org.apache.maven.plugins</groupId>
  <artifactId>maven-assembly-plugin</artifactId>
  <executions>
    <execution>
      <id>make-assembly-dev</id>
      <phase>package</phase>
      <goals>
        <goal>single</goal>
      </goals>
      <configuration>
        <skipAssembly>${skipAssemblyDEV}</skipAssembly>
        <descriptors>
          <descriptor>src/main/assembly/dev/assembly.xml</descriptor>
        </descriptors>
        <finalName>${project.artifactId}</finalName>
      </configuration>
    </execution>
    <execution>
      <id>make-assembly-uat</id>
      <phase>package</phase>
      <goals>
        <goal>single</goal>
      </goals>
      <configuration>
        <skipAssembly>${skipAssemblyUAT}</skipAssembly>
        <descriptors>
          <descriptor>src/main/assembly/uat/assembly.xml</descriptor>
        </descriptors>
        <finalName>${project.artifactId}</finalName>
      </configuration>
    </execution>
    <execution>
      <id>make-assembly-prod</id>
      <phase>package</phase>
      <goals>
        <goal>single</goal>
      </goals>
      <configuration>
        <skipAssembly>${skipAssemblyPROD}</skipAssembly>
        <descriptors>
          <descriptor>src/main/assembly/prod/assembly.xml</descriptor>
        </descriptors>
        <finalName>${project.artifactId}</finalName>
      </configuration>
    </execution>
  </executions>
</plugin>
```

assembly.xml 描述了配置文件的打包方式
```xml
<!--hkcjs-service: src/main/assembly/dev/assembly.xml -->
<assembly>
	<id>assembly-${env.devMode}</id>
	<formats>
		<format>tar.gz</format>
	</formats>
	<includeBaseDirectory>false</includeBaseDirectory>
	<fileSets>
		<fileSet>
			<directory>src/main/assembly/bin</directory>
			<outputDirectory>bin</outputDirectory>
			<directoryMode>0755</directoryMode>
			<fileMode>0755</fileMode>
		</fileSet>
		<fileSet>
			<directory>src/main/assembly/dev/conf</directory>
			<outputDirectory>conf</outputDirectory>
			<directoryMode>0744</directoryMode>
			<fileMode>0644</fileMode>
		</fileSet>
		<fileSet>
			<directory>lib</directory>
			<outputDirectory>lib</outputDirectory>
			<directoryMode>0744</directoryMode>
			<fileMode>0644</fileMode>
		</fileSet>
	</fileSets>
	<dependencySets>
		<dependencySet>
			<outputDirectory>lib</outputDirectory>
		</dependencySet>
	</dependencySets>
</assembly>
```


maven 的 package 命令为三种不同环境打包结果：

![maven 打包结果](https://upload-images.jianshu.io/upload_images/1341067-3492c1546fb7e2fe.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

各个环境 assembly 的配置文件不相同

