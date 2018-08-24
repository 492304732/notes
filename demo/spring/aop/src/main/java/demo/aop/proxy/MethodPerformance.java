package demo.aop.proxy;

/**
 * @Description: 工具类：计算方法运行时间
 * @author: 01369674
 * @date: 2018/7/16
 */
public class MethodPerformance {
    private long begin;
    private long end;
    private String serviceMethod;
    public MethodPerformance(String serviceMethod){
        this.serviceMethod = serviceMethod;
        this.begin = System.currentTimeMillis();
    }
    public void printPerformance(){
        end = System.currentTimeMillis();
        long elaspe = end-begin;
        System.out.println(serviceMethod+"花费"+elaspe+"毫秒");
    }
}
