package demo.mvc.viewResolve;

import demo.mvc.validator.ValidatableUser;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * @Description: 自定义 Excel 表格的 view
 * @author: 01369674
 * @date: 2018/8/10
 */
public class UserListExcelView extends AbstractXlsxView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setHeader("Content-Disposition","inline;filename="+new String("用户列表.xlsx".getBytes(),"iso8859-1"));

        //创建表头
        List<ValidatableUser> userList = (List<ValidatableUser>)model.get("userList");
        Sheet sheet = workbook.createSheet("users");
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("账号");
        header.createCell(1).setCellValue("姓名");
        header.createCell(2).setCellValue("生日");

        //按行插入数据
        int rowNum = 1;
        for(ValidatableUser user:userList){
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(user.getUserName());
            row.createCell(1).setCellValue(user.getRealName());
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            row.createCell(2).setCellValue(formatter.format(user.getBirthday()));
        }
    }
}
