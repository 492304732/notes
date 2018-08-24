package demo.aop.monitor;

import org.springframework.stereotype.Component;

/**
 * @Description: 监控行为
 * @author: 01369674
 * @date: 2018/7/30
 */

public class PerformanceMonitor{

    public static void begin(String className,String methodName){
        System.out.println("### begin:"+className+"."+methodName);
    }

    public static void end(){
        System.out.println("### end");
    }
}
