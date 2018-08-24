package demo.aop.advisor;

import demo.aop.aspect.Waiter;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.support.StaticMethodMatcherPointcutAdvisor;

import java.lang.reflect.Method;

/**
 * @Description: 定义切面
 * @author: 01369674
 * @date: 2018/7/30
 */
public class GreetingAdvisor extends StaticMethodMatcherPointcutAdvisor {
    @Override
    public boolean matches(Method method, Class<?> aClass) {
        return "greetTo".equals(method.getName());
    }

    @Override
    public ClassFilter getClassFilter() {
        //为Waiter或者Waiter的之类
        return clazz -> Waitor.class.isAssignableFrom(clazz);
    }
}
