package demo.mvc.messageConverter;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * @Description: TODO
 * @author: 01369674
 * @date: 2018/8/8
 */

@ToString
public class User {
    @Getter @Setter
    Integer userId;

    @Getter @Setter
    String userName;

    @Getter @Setter
    String password;

    @Getter @Setter
    String email;

    @Getter @Setter
    String telephone;
}
