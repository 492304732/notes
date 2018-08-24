package demo.aop.advisor;

/**
 * @Description: 代理类
 * @author: 01369674
 * @date: 2018/7/30
 */
public class WaiterDelegate {
    private Waitor waitor;
    public void service(String clientName){
        waitor.greetTo(clientName);
        waitor.serveTo(clientName);
    }
    public void setWaitor(Waitor waitor){
        this.waitor=waitor;
    }
}
