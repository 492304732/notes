参考博客：https://blog.csdn.net/yx0628/article/details/30091211

## 一、JDK 命令行工具

### 1、jps：虚拟机进程状况工具

​        jps（JVM Process Status Tool）可以列出正在运行的虚拟机进程，并显示虚拟机执行主类名称以及这些进程的本地虚拟机唯一ID。

命令格式：

```shell
jsp [options] [hostid]
```

jps的参数：

```shell
-q  # 只输出LVMID，省略主类的名称
-m  # 输出虚拟机进程启动时传递给主类main()函数的参数
-l  # 输出主类的全名，如果进城执行的Jar包，则输出Jar路径
-v  # 输出虚拟机进程启动时JVM参数
```

执行结果：

![jps命令](https://upload-images.jianshu.io/upload_images/1341067-bcbbf6df78e42500.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

从图中看，coss 和 cos（tomcat）的进程码分别为1048和10076

### 2、jstat：虚拟机统计信息监控工具

​        jstat(JVM Statistics Monitoring Tool) 是用于监视虚拟机各种运行状态信息的命令行工具，它可以显示本地或者远程虚拟机进程中的类装载、内存、垃圾收集、JIT编译等运行数据，在没有GUI图形界面，只提供了纯文本控制台环境的服务器上，它将是运行期定位虚拟机性能问题的首选工具。

命令格式：

```shell
jstat [option vmid [interval[s|ms] [count]]]
# 参数 interval 和 count 代表查询间隔和次数，如果省略这两个参数，说明只查询一次。

jstat -gc 2764 250 20   # 每250毫秒查询一次进程2764垃圾收集情况，一共查询20次
```

jstat 参数：

```shell
-class             # 监视类装载、卸载数量、总空间以及类装载所耗费的时间
-gc                # 监视Java堆状况，包括Eden区、两个Survivor区、老年代、永久代等的容量、已用空间、GC时间合计等信息
-gccapacity        # 监视内容与-gc基本相同，但输出主要关注Java堆各个区域使用到的最大、最小空间
-gcutil            # 监视内容与-gc基本相同，但输出主要关注已使用空间占总空间的百分比
-gccause           # 与-gcutil功能一样，但是会额外输出导致上一次GC产生的原因
-gcnew             # 监视新生代GC状况
-gcnewcapacity     # 监视内容与-gcnew基本相同，输出主要关注使用到的最大、最小空间
-gcold             # 监视老年代GC状况
-gcoldcapacity     # 监视内容与-gcold基本相同，输出主要关注使用到的最大、最小空间
-gcpermcapacity    # 输出永久代使用到的最大、最小空间
-compiler          # 输出JIT编译器编译过的方法、耗时等信息
-printcompilation  # 输出已经被JIT编译的方法
```

执行结果：

- jstat -class

![jstat -class](https://upload-images.jianshu.io/upload_images/1341067-0c9be6f14059f119.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

​	从图中看处：coss 工程装载了6359个class，占用了12767.8 bytes 空间，加载时间为 3.57s。启动	cos 的tomcat 装载了8399个class，占用了 17859.1 bytes，加载时间为 5.69。

- jstat -gcutil 10076

  ![jstat -gc](https://upload-images.jianshu.io/upload_images/1341067-98a06276b6af9654.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

  查询结果放入表格后如上图，程序运行以来，一共进行了 9 次 young gc（YGC），用时0.232秒（YGCT），一共进行了0次full gc（FGC），用时0秒（FGCT），GC总耗时 0.232秒。S0、S1 是两个Survivor区所占空间，E 为新生代 Eden区所占空间，O表示老年代，M表示永久代（有些jvm版本为P）。

### 3、jinfo：Java 配置信息工具

​       jinfo（Configuration Info for Java）实时的查看和调整虚拟机的各项参数，使用jps命令的-v参数可以查看虚拟机启动时显式指定的参数列表，但如果想知道未被显式指定的参数的系统默认值，除了找资料外，就只能使用jinfo的-flag选项进行查询了。

命令格式：

```shell
jinfo [option] pid
```

执行结果：

![jinfo](https://upload-images.jianshu.io/upload_images/1341067-f90a52cc5f2ff67b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

### 4、jmap：Java 内存映像工具

​        jmap（Memory Map for Java）命令用于生成堆转储快照（一般称为headdump或dump文件）。还可以查询finalize执行队列，Java堆和永久代的详细信息，如空间使用率、当前用的是哪种收集器等。

命令格式：

```shell
jmap [option] vmid
```

jmap 选项：

```shell
-dump      # 生成Java堆转储快照。格式为：-dump:[live, ] format=b, file=<filename>, 其中live子参数说明是否只dump出存活的对象。
-finalizerinfo  # 显示在F-Queue中等待Finalizer线程执行finalize方法的对象。只在Linux/Solaris平台下有效
-heap      # 显示Java堆详细信息，如使用哪种回收器、参数配置、分代状况等。只在Linux/Solaris平台下有效
-histo     # 显示堆中对象统计信息，包括类、实例数量、合计容量
-permstat  # 以ClassLoader为统计口径显示永久代内存状态。只在Linux/Solaris平台下有效
-F         #  当虚拟机进程对-dump选项没有响应时，可使用这个选项强制生成dump快照，只在Linux/Solaris平台下有效
```

### 5、jhat：虚拟机堆转储快照分析工具

​        jhat（JVM Heap Analysis Tool）与jmap搭配使用，分析jmap生成的堆转储快照。jhat内置了一个微型的HTTP/HTML服务器，生成dump文件的分析结果后，可以在浏览器中查看。不过在实际中不使用jhat来分析dump，主要原因：1.一般不在部署应用的服务器上直接分析dump文件，而要复制到其他机器上进行分析，因为是耗时且消耗硬件资源。2.jhat功能比较简陋，如VisualVM，Eclipse Momery Analyzer、IBM HeapAnalyzer等都更强大。

### 5、jstack：Java 堆栈跟踪工具

​        jstack（Stack Trace for Java）命令用于生成虚拟机当前时刻的线程快照（一般称为threaddump或者javacore文件）。线程快照就是当前虚拟机内每一条线程正在执行的方法堆栈的集合，**生成线程快照的主要目的是定位线程出现长时间停顿的原因，如线程间死锁、死循环、请求外部资源导致的长时间等待等都是导致线程长时间停顿的常见原因。**线程出现停顿的时候通过jstack来查看各个线程的调用堆栈，就可以知道没有响应的线程到底在后台做些什么事情，或者等待什么资源。

### 6、HSDIS：JIT 生成代码反编译

​        HSDIS是一个Sun官方推荐的HotSpot虚拟机JIT编译代码的反汇编插件，它包含在HotSpot虚拟机的源码之中，但没有提供编译后的程序。它的作用是让HotSpot的-XX：+PrintAssembly指令调用它来把动态生成的本地代码还原为汇编代码输出，同时还生成大量非常有价值的注释。

## 二、JDK 可视化工具

### 1、JConsole：Java 监视与管理控制台

Jconsole（Java Monitoring and Management Console）是一种基于JMX的可视化监视、管理工具。

### 1.1 内存监控 (jstat)

```java
/**
 * Jconsole 监视代码
 * 内存占位对象，一个 OOMObject 对象大约 64KB
 */
public class OOMObject {
    public byte[] placeholder = new byte[64*1024];

    public static void fillHeap(int num) throws InterruptedException {
        List<OOMObject> list = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            //稍作延时，让监视曲线变化更明显
            Thread.sleep(50);
            list.add(new OOMObject());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        fillHeap(1000);
    }
}
```

Jconsole 对堆不同区域的监控图：

![jconsole](https://upload-images.jianshu.io/upload_images/1341067-f7d9b4850f1d304f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

### 2.2 线程监控 (jstack)