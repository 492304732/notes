package demo.aop.aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * @Description: TODO
 * @author: 01369674
 * @date: 2018/7/27
 */

@Aspect
public class PreGreetingAspect{

    @Before("execution(* greetTo(..))")
    public void beforeGreeting(){
        System.out.println("### Before:How are you");
    }

    @After("execution(* demo.aop.aspect.NaiveWaiter.*(..))")
    public void after(){
        System.out.println("### After");
        System.out.println();
    }
}
