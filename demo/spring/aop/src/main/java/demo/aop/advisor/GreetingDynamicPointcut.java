package demo.aop.advisor;

import org.springframework.aop.ClassFilter;
import org.springframework.aop.support.DynamicMethodMatcherPointcut;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: TODO
 * @author: 01369674
 * @date: 2018/7/30
 */
public class GreetingDynamicPointcut extends DynamicMethodMatcherPointcut {

    private static List<String> specialClientList = new ArrayList<>();
    static {
        specialClientList.add("John");
        specialClientList.add("Tom");
    }

    @Override
    public boolean matches(Method method, Class<?> clazz, Object... args) {
        System.out.println("matches 动态检查");
        String clinetName = (String)args[0];
        return specialClientList.contains(clinetName);
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        System.out.println("matches 静态检查");
        return "greetTo".equals(method.getName());
    }

    @Override
    public ClassFilter getClassFilter() {
        return clazz -> {
            System.out.println("classFilter 静态检查");
            return Waitor.class.isAssignableFrom(clazz);
        };
    }
}
