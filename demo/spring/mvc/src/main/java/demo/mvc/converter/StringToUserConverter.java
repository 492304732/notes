package demo.mvc.converter;

import demo.mvc.messageConverter.User;
import org.springframework.core.convert.converter.Converter;


/**
 * @Description: String到User的转换，自定义
 * @author: 01369674
 * @date: 2018/8/10
 */
public class StringToUserConverter implements Converter<String,User> {


    /**
     * User字符串的格式为 <userName>:<passWord>:<email>
     * 此方法解析字符串，将其转化为对象
     */
    @Override
    public User convert(String s) {
        User user = new User();
        if(s!=null){
            String[] items = s.split(":");
            user.setUserName(items[0]);
            user.setPassword(items[1]);
            user.setEmail(items[2]);
        }
        return user;
    }
}
