package demo.mvc.validator;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Past;
import java.util.Date;

/**
 * @Description: 实体加入校验注解
 * @author: 01369674
 * @date: 2018/8/10
 */

@ToString
public class ValidatableUser {

    @Getter @Setter
    private String userId;

    @Length(min=4,max=30)
    @Getter @Setter
    private String userName;

    @Length(min=6,max=30)
    @Getter @Setter
    private String password;

    @Length(min=2,max=100)
    @Getter @Setter
    private String realName;

    @Past
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Getter @Setter
    private Date birthday;

    @DecimalMin(value = "1000.00")
    @DecimalMax(value="100000.00")
    @NumberFormat(pattern = "#,###.##")
    @Getter @Setter
    private long salary;
}
