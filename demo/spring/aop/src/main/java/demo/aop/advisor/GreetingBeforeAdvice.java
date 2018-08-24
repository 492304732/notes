package demo.aop.advisor;

import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * @Description: 前置增强
 * @author: 01369674
 * @date: 2018/7/30
 */
public class GreetingBeforeAdvice implements MethodBeforeAdvice {
    @Override
    public void before(Method method, Object[] args, Object obj) throws Throwable {
        System.out.println(obj.getClass().getName()+"."+method.getName());
        String clientName = (String)args[0];
        System.out.println("-----How are you! Mr."+clientName+"------");
    }
}
