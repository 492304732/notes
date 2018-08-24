package demo.mvc.formatter;

import org.junit.Test;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * @Description: TODO
 * @author: 01369674
 * @date: 2018/8/10
 */
public class FormatterTest {
    static final String basePath = "http://localhost:8080";

    @Test
    public void testFormatter(){
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String,String> form = new LinkedMultiValueMap<>();
        form.add("userName","tom");
        form.add("birthday","1998-08-13");
        form.add("salary","4,500.00");
        String html = restTemplate.postForObject(basePath+"/user/handle82",form,String.class);
        System.out.println(html);
    }
}
