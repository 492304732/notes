package demo.ioc.lifeCycle;

import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * @Description: 测试 BeanFactory生命周期
 * @author: 01369674
 * @date: 2018/7/18
 */
public class BeanLifeCycleTest {

    @Test
    public void lifeCycleInBeanFactory(){
        Resource res = new ClassPathResource("demo/ioc/lifeCycle/beans.xml");
        BeanFactory bf = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader((DefaultListableBeanFactory)bf);
        reader.loadBeanDefinitions(res);
        ((ConfigurableBeanFactory) bf).addBeanPostProcessor(new MyBeanPostProcessor());
        ((ConfigurableBeanFactory) bf).addBeanPostProcessor(new MyInstantiationAwareBeanPostProcessor());

        Car car1 = (Car)bf.getBean("car");
        car1.introduce();
        car1.setColor("red");

        Car car2 = (Car)bf.getBean("car");

        System.out.println("car1==car2:"+(car1==car2));

        ((DefaultListableBeanFactory) bf).destroySingletons();

    }
}
