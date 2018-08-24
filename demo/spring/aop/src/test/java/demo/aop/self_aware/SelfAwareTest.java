package demo.aop.self_aware;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Description: TODO
 * @author: 01369674
 * @date: 2018/7/31
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:demo/aop/self_aware/beans.xml"})
public class SelfAwareTest {

    @Autowired @Qualifier("waiter") AWaiter waiter;

    @Autowired SelfAwareWaiter selfAwareWaiter;


    /**
     * 输出：
     * demo.aop.self_aware.AWaiter.greetTo
     * -----How are you! Mr.Tom------
     * A waiter greet toTom
     * A waiter serve to Tom
     *
     * 只增强了 greet to，对于类内部方法调用的 serve to，没有进行增强
     * 说明 Spring AOP 默认配置方法只增强方法被类外部调用的情况
     */
    @Test
    public void test(){
        waiter.greetTo("Tom");
    }

    @Test
    public void testSelfAware(){
        selfAwareWaiter.greetTo("Susan");
    }

}
