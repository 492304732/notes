package demo.mvc.controller;

import demo.mvc.formatter.FormatableUser;
import demo.mvc.messageConverter.User;
import demo.mvc.validator.ValidatableUser;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 测试 messageConverter
 * @author: 01369674
 * @date: 2018/8/7
 */

@Controller
@RequestMapping("/user")
public class UserController {

    // StringHttpMessageConverter
    @RequestMapping("/handle41")
    @ResponseBody
    public String handle41(@RequestBody String requestBody){
        System.out.println(requestBody);
        return "success";
    }

    // ByteArrayHttpMessageConverter
    @RequestMapping("/handle42/{imageId}")
    @ResponseBody
    public byte[] handle42(@PathVariable("imageId") String imageId) throws IOException {
        System.out.println("load image of "+imageId);
        Resource res = new ClassPathResource("/demo/mvc/messageConverter/image.png");
        byte[] fileData = FileCopyUtils.copyToByteArray(res.getInputStream());
        return fileData;
    }

    // MarshallingHttpMessageConverter 处理XML格式的请求或者响应信息
    @RequestMapping("/handle51")
    public ResponseEntity<User> handle51(HttpEntity<User> requestEntity){
        User user = requestEntity.getBody();
        user.setUserId(1000);
        System.out.println(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping("/handle81")
    @ResponseBody
    public User handle81(@RequestParam("user") User user){
        return user;
    }

    @RequestMapping("/handle82")
    @ResponseBody
    public FormatableUser handle82(FormatableUser user){
        return user;
    }

    @RequestMapping("/handle91")
    @ResponseBody
    public String handle91(@Valid @ModelAttribute("user") ValidatableUser user, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            StringBuffer sb = new StringBuffer("参数错误"+"\n");
            for(FieldError error:bindingResult.getFieldErrors()){
                sb.append(error.getCodes()[0]+" "+error.getDefaultMessage()+"\n");
                System.out.println(error.getRejectedValue());
            }
            return sb.toString();
        }else{
            return user.toString();
        }
    }

    @RequestMapping("/showUserListInExcel")
    public String showUserListInExcel(ModelMap mm) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        List<ValidatableUser> userList = new ArrayList<>();
        ValidatableUser user1 = new ValidatableUser();
        user1.setUserName("tom");
        user1.setRealName("tommy");
        user1.setBirthday(formatter.parse("1965-02-03"));

        ValidatableUser user2 = new ValidatableUser();
        user2.setUserName("john");
        user2.setRealName("johnny");
        user2.setBirthday(formatter.parse("1982-05-06"));

        mm.addAttribute("userList",userList);

        return "userListExcel";
    }
}
