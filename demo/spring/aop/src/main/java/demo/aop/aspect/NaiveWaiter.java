package demo.aop.aspect;

/**
 * @Description: TODO
 * @author: 01369674
 * @date: 2018/7/27
 */
public class NaiveWaiter implements Waiter {
    @Override
    public void greetTo(String clientName) {
        System.out.println("NaiveWaiter:greet to "+clientName);
    }

    @Override
    public void serveTo(String clientName) {
        System.out.println("NaiveWaiter:serving "+clientName);
    }
}
