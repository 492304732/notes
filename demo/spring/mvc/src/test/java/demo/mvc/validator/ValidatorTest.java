package demo.mvc.validator;

import org.junit.Test;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * @Description: TODO
 * @author: 01369674
 * @date: 2018/8/10
 */
public class ValidatorTest {
    static final String basePath = "http://localhost:8080";

    @Test
    public void testValidatorSuccess(){
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String,String> form = new LinkedMultiValueMap<>();
        form.add("userName","tomcat");
        form.add("password","1233222222132");
        form.add("realName","tommm");
        form.add("birthday","1988-08-13");
        form.add("salary","21,300.00");
        String result = restTemplate.postForObject(basePath+"user/handle91",form,String.class);
        System.out.println(result);
    }

    @Test
    public void testValidatorError(){
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String,String> form = new LinkedMultiValueMap<>();
        form.add("userName","tom");
        form.add("password","1233");
        form.add("realName","tommm");
        form.add("birthday","2020-10-20");
        form.add("salary","21,300.00");
        String result = restTemplate.postForObject(basePath+"user/handle91",form,String.class);
        System.out.println(result);
    }
}
