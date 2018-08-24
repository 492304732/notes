package demo.ioc.lifeCycle;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;

/**
 * @Description: Bean:Car
 * @author: 01369674
 * @date: 2018/7/18
 */

public class Car implements BeanFactoryAware,BeanNameAware,InitializingBean,DisposableBean {

    private String brand;
    private String color;
    private int maxSpeed;

    private BeanFactory beanFactory;

    private String beanName;

    public Car(){
        System.out.println("调用car构造函数");
    }

    public void setBrand(String brand) {
        System.out.println("setBrand");
        this.brand = brand;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getColor(){
        return color;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("BeanFactoryAware.setBeanFactory");
        this.beanFactory = beanFactory;
    }

    @Override
    public void setBeanName(String s) {
        System.out.println("BeanNameAware.setBeanName");
        this.beanName = s;
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("DisposableBean.destroy");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("InitializingBean.afterPropertiesSet()");
    }


    public void introduce(){
        System.out.println("brand"+brand+";color"+color+";maxSpeed"+maxSpeed);
    }

    /**
     * 自定义初始化方法
     */
    public void myInit(){
        System.out.println("调用init-method指定的myInit，将maxSpped设置为240");
        this.maxSpeed=240;
    }

    /**
     * 自定义销毁方法
     */
    public void myDestroy(){
        System.out.println("调用 destroy-method 所指定的 myDestroy");
    }


}
