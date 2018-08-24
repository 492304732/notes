package demo.ioc.lifeCycle;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;

import java.beans.PropertyDescriptor;

/**
 * @Description: TODO
 * @author: 01369674
 * @date: 2018/7/18
 */
public class MyInstantiationAwareBeanPostProcessor
        extends InstantiationAwareBeanPostProcessorAdapter {


    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        //只对Car处理
        if("car".equals(beanName)){
            System.out.println("InstantiationAwareBeanPostProcessorAdapter.postProcessBeforeInstantiation");
        }

        return null;
    }

    /**
     * 所有 Bean 实例化之后调用
     */
    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        System.out.println("InstantiationAwareBeanPostProcessorAdapter.postProcessAfterInstantiation");
        return true;
    }

    /**
     * 在设置某个属性时调用
     */
    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs, PropertyDescriptor[] pds, Object bean, String beanName) throws BeansException {
        System.out.println("InstantiationAwareBeanPostProcessorAdapter.postProcessPropertyValues");
        return pvs;
    }
}
