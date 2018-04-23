## 3.25 Java 开发环境配置

#### 任务：
1.上官网下载 jdk8，并配置环境变量
jdk8下载地址：http://www.oracle.com/technetwork/cn/java/javase/downloads/jdk8-downloads-2133151-zhs.html
环境变量配置：https://jingyan.baidu.com/article/6dad5075d1dc40a123e36ea3.html

思考题：jre 和 jdk 的区别是什么
什么是 java EE、java SE、Java ME等

2.下载java编程工具 intellij idea
官网地址：https://www.jetbrains.com/idea/
破解方法：https://blog.csdn.net/qq_35434690/article/details/77683655

选做：eclipse 和 idea 是目前最常用的java编程工具，简单了解他们的作用

3. 下载notepad++或者editplus，官网最新版即可

### 问题解答：

#### 1、JRE 和 JDK

- JRE(Java Runtime Environment):Java程序运行的标准环境。包括 JavaSE API 子集和Java虚拟机。

- JDK(Java Development Kit):支持 Java 程序开发的最小环境。包括Java程序设计语言，Java虚拟机，Java API类库这三部分。

#### 2、Java 按技术所服务的领域分
- Java Card:支持一些小程序运行在小内存设备上的平台。
- Java ME:支持Java程序运行在移动终端上的平台，对Java API有所精简，并加入了对移动端的支持。
- Java SE:支持桌面级应用的Java平台（如Windows下的应用程序），提供了完整的Java核心API。
- Java EE:支持使用多层架构的企业应用的Java平台，除了提供JavaSE的API外，还对其做了大量的扩充并提供了相关的部署支持。


## 3.28 Java 语言特性

#### 任务：
主要任务：回顾C语言（面向过程）的语法和语言特性，C++语言（面向对象）的语法和语言特性。比较 java 语言的特性

1. 思考题：编译型语言是什么，解释型语言是什么，他们有什么区别。C/C++属于编译型还是解释型？Java属于编译型还是解释型？
2. 为什么说C/C++是平台相关，java是平台无关（write once,run everywhere）？
3. 思考题**（选做）:什么是 java 垃圾回收机制？回答后，一段时间后再回顾这个问题。
4. 思考题***（选做）：Lambda 表达式语法是 Java 8 加入的新特性，这种语法规则增加了Java语言的易用性。Lambda 表达式函数式编程具体如何编写？它和传统语法相比有什么优势，为什么？
5. 思考题**（选做）：我们安装程序的时候常常要选择32位的版本或者64位的版本，它们的区别在于适应不同的操作系统。请问为什么32位系统运行时最大可用内存是 4G，64位系统是16G？
6. 大部分 Java 源码和文档可在在官网下载jdk目录的 src.jar中找到（路径如\jdk1.7.0_79\src.jar），但有一些源码是没有公布的。Open jdk 提供了与官方 jdk 非常类似实现开源代码。请问Open jdk和jdk的联系和区别？尝试为添加src.jar作为源码包，并阅读String.replace(char oldChar, char newChar)的文档和源码。参考：https://blog.csdn.net/getyouwant/article/details/50428753