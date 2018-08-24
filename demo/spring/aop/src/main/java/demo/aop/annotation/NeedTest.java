package demo.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description: TODO
 * @author: 01369674
 * @date: 2018/8/1
 */

//声明注解的保留期限
@Retention(RetentionPolicy.RUNTIME)
//声明可以使用注解的目标类型
@Target(ElementType.METHOD)
public @interface NeedTest {
    boolean value() default true;
}