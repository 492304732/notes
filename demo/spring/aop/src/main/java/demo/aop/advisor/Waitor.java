package demo.aop.advisor;

import com.sun.org.apache.xml.internal.res.XMLErrorResources_tr;

/**
 * @Description: TODO
 * @author: 01369674
 * @date: 2018/7/30
 */
public class Waitor {
    public void greetTo(String name){
        System.out.println("waiter greet to "+name);
    }

    public void serveTo(String name){
        System.out.println("waiter serve to "+name);
    }
}
