package demo.aop.advisor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Description: TODO
 * @author: 01369674
 * @date: 2018/7/31
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:demo/aop/advisor/creator.xml"})
public class CreatorTest {
    @Autowired Waitor waiter;
    @Autowired Seller seller;

    @Test
    public void test(){
        waiter.greetTo("Tom");
        seller.greetTo("Tom");
    }
}
