package demo.aop.self_aware;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: TODO
 * @author: 01369674
 * @date: 2018/7/31
 */
public class SelfAwareWaiter implements BeanSelfProxyAware{

    SelfAwareWaiter selfAwareWaiter;

    public void serveTo(String clientName){
        System.out.println("A waiter serve to "+ clientName);
    }

    public void greetTo(String clientName){
        System.out.println("A waiter greet to"+clientName);
        serveTo(clientName);
    }

    @Override
    public void setSelfProxy(Object object) {
        this.selfAwareWaiter = (SelfAwareWaiter)object;
    }
}
