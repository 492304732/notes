package demo.mvc.converter;

import demo.mvc.messageConverter.User;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * @Description: 自定义类型转换器测试
 * @author: 01369674
 * @date: 2018/8/10
 */
public class ConverterTest {

    static final String basePath = "http://localhost:8080";

    /**
     * StringToUserConverter 将自定义格式转换成实体类
     */
    @Test
    public void testStringToUserConverter(){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<User> response = restTemplate.postForEntity(basePath+"/user/handle81?user=tom:123:44332@126.com",null,User.class);
        System.out.println(response.getBody().toString());
    }
}
