package demo.mvc.messageConverter;

import com.thoughtworks.xstream.io.xml.StaxDriver;
import org.junit.Test;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Collections;

/**
 * @Description: messageConverter 测试
 * @author: 01369674
 * @date: 2018/8/7
 */
public class UserControllerTest {

    static final String basePath = "http://localhost:8080";

    static final String testImagePath = "D:/test.jpg";

    /**
     * String 请求响应
     */
    @Test
    public void testHandle41(){
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String,String> form = new LinkedMultiValueMap<>();
        form.add("userName","tom");
        form.add("password","123456");
        form.add("age","45");
        restTemplate.postForLocation(basePath+"/user/handle41",form);
    }

    /**
     * byte[] 请求响应，如文件传输
     */
    @Test
    public void testHandle42() throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        byte[] response = restTemplate.postForObject(basePath+"/user/handle42/{itemId}",
                null,byte[].class,"1233");
        Resource outFile = new FileSystemResource(testImagePath);
        FileCopyUtils.copy(response,outFile.getFile());
    }

    /**
     * application/xml 格式请求响应
     */
    @Test
    public void testHandle51(){
        RestTemplate restTemplate = buildRestTemplate();

        User user = new User();
        user.setUserName("tom");
        user.setPassword("1233");
        user.setEmail("1234@163.com");

        //设置请求头
        HttpHeaders entityHeaders = new HttpHeaders();
        entityHeaders.setContentType(MediaType.valueOf("application/xml;UTF-8"));
        entityHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_XML));

        HttpEntity<User> requestEntity = new HttpEntity<>(user,entityHeaders);
        ResponseEntity<User> responseEntity = restTemplate.exchange(
                basePath+"/user/handle51",
                HttpMethod.POST,requestEntity,User.class
        );
    }

    private RestTemplate buildRestTemplate(){
        RestTemplate restTemplate = new RestTemplate();

        XStreamMarshaller xmlMarshaller = new XStreamMarshaller();
        xmlMarshaller.setStreamDriver(new StaxDriver());
        xmlMarshaller.setAnnotatedClasses(new Class[]{User.class});

        MarshallingHttpMessageConverter xmlConverter = new MarshallingHttpMessageConverter();
        xmlConverter.setMarshaller(xmlMarshaller);
        xmlConverter.setUnmarshaller(xmlMarshaller);
        restTemplate.getMessageConverters().add(xmlConverter);

        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        restTemplate.getMessageConverters().add(jsonConverter);

        return restTemplate;
    }
}
