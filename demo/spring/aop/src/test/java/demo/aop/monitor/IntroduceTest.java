package demo.aop.monitor;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Description: 测试引介增强
 * @author: 01369674
 * @date: 2018/7/30
 */
public class IntroduceTest {

    @Test
    public void introduce(){
        String configPath = "demo/aop/monitor/beans.xml";
        ApplicationContext ctx = new ClassPathXmlApplicationContext(configPath);
        MyService service = (MyService)ctx.getBean("myService");

        Monitorable monitorable = (Monitorable)service;
        monitorable.setMonitorActive(true);

        service.get();
        service.insert();
    }
}
