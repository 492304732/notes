package demo.aop.advisor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Description: TODO
 * @author: 01369674
 * @date: 2018/7/30
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:demo/aop/advisor/beans.xml"})
public class AdvisorTest{

    @Autowired @Qualifier("waiter") Waitor waiter;
    @Autowired @Qualifier("seller") Seller seller;

    @Autowired @Qualifier("waiter1") Waitor waiter1;

    @Autowired @Qualifier("waiter2") Waitor waiter2;

    @Autowired @Qualifier("waiter3") Waitor waiter3;

    @Test
    public void testPoitcut(){
        if(waiter!=null) {
            System.out.println("### test waiter ###");
            waiter.greetTo("susu");
            waiter.serveTo("susu");
        }
        if(seller!=null){
            System.out.println("### test seller ###");
            seller.greetTo("susu");
        }
    }

    @Test
    public void testRegexPoitcut(){
        if(waiter1!=null){
            System.out.println("### test waiter1 ###");
            waiter1.greetTo("susu");
            waiter1.serveTo("susu");
        }
    }

    @Test
    public void testDynamicPoitcut(){
        if(waiter2!=null){
            waiter2.greetTo("Tom");
            waiter2.serveTo("Tom");

            waiter2.greetTo("susu");
            waiter2.serveTo("susu");
        }
    }

    @Test
    public void testControllFlowPoitcut(){
        WaiterDelegate wd = new WaiterDelegate();
        wd.setWaitor(waiter3);
        waiter3.greetTo("Tom");
        waiter3.serveTo("Tom");
        wd.service("Peter");
    }
}
