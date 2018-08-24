package demo.aop.proxy;

import demo.aop.proxy.cglib.CglibProxy;
import demo.aop.proxy.jdk.PerformanceHandler;
import org.junit.Test;

import java.lang.reflect.Proxy;

/**
 * @Description: 动态代理测试
 * @author: 01369674
 * @date: 2018/7/16
 */
public class ProxyTest {

    @Test
    public void jdkProxy(){
        ForumService target = new ForumServiceImpl();
        PerformanceHandler handler = new PerformanceHandler(target);
        ForumService proxy = (ForumService) Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                handler);
        proxy.removeForum(10);
        proxy.removeTopic(1012);
    }

    @Test
    public void cglibProxy(){
        CglibProxy proxy = new CglibProxy();
        ForumService forumService = (ForumService)proxy.getProxy(ForumServiceImpl.class);
        forumService.removeForum(10);
        forumService.removeTopic(1023);
    }

}
