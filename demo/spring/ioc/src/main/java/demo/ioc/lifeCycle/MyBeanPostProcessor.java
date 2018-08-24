package demo.ioc.lifeCycle;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * @Description: BeanPostProcessor
 * @author: 01369674
 * @date: 2018/7/18
 */
public class MyBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if(beanName.equals("car")){
            Car car = (Car)bean;
            if(car.getColor()==null){
                System.out.println("BeanPostProcessor.postProcessBeforeInitialization,设置color为黑色");
                car.setColor("black");
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(beanName.equals("car")){
            Car car = (Car)bean;
            if(car.getMaxSpeed()>=200){
                System.out.println("BeanPostProcessor.postProcessAfterInitialization,超速，设置maxSpeed为200");
                car.setMaxSpeed(200);
            }
        }
        return bean;
    }
}
