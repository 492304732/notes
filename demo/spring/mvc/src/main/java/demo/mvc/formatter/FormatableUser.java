package demo.mvc.formatter;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import java.util.Date;

/**
 * @Description: TODO
 * @author: 01369674
 * @date: 2018/8/10
 */

@ToString
public class FormatableUser {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Getter @Setter
    private Date birthday;

    @Getter @Setter
    private String userName;

    @NumberFormat(pattern = "#,###.##")
    @Getter @Setter
    private long salary;

}
