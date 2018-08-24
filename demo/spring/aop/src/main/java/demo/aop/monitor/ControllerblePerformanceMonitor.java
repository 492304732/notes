package demo.aop.monitor;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.DelegatingIntroductionInterceptor;

/**
 * @Description: 引介增强，增加监控开关
 * @author: 01369674
 * @date: 2018/7/30
 */
public class ControllerblePerformanceMonitor
        extends DelegatingIntroductionInterceptor
        implements Monitorable{

    //ThreadLocal 保证线程安全
    private ThreadLocal<Boolean> MonitorStatusMap = new ThreadLocal<>();

    @Override
    public void setMonitorActive(boolean active) {
        MonitorStatusMap.set(active);
    }

    public Object invoke(MethodInvocation mi) throws Throwable{
        Object obj = null;
        if(MonitorStatusMap.get()!=null && MonitorStatusMap.get().equals(true)){
            PerformanceMonitor.begin(mi.getClass().getName(),mi.getMethod().getName());
            obj = super.invoke(mi);
            PerformanceMonitor.end();
        }else{
            obj = super.invoke(mi);
        }
        return obj;
    }
}
