package demo.aop.monitor;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @Description: 服务类
 * @author: 01369674
 * @date: 2018/7/30
 */
public class MyService{
    public void insert(){
        System.out.println("MyService.insert");
    }

    public String get(){
        System.out.println("MyService.get");
        return "Company";
    }
}
