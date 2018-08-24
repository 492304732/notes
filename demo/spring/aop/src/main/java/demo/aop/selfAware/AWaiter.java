package demo.aop.self_aware;

/**
 * @Description: TODO
 * @author: 01369674
 * @date: 2018/7/31
 */
public class AWaiter {

    public void serveTo(String clientName){
        System.out.println("A waiter serve to "+ clientName);
    }

    /**
     * 自调用，测试代理
     * @param clientName
     */
    public void greetTo(String clientName){
        System.out.println("A waiter greet to"+clientName);
        serveTo(clientName);
    }
}
