package com.demo1.servlet;

import com.demo1.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description: 注册
 * @author: 01369674
 * @date: 2018/5/22
 */

public class RegServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);//因为表单是post方式提交的
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");

        User u = getUserFromRequest(request);

        //把注册成功的用户对象保存在session中
        request.getSession().setAttribute("regUser", u);
        //重定向
        request.getRequestDispatcher("/jsp/basic/userInfo.jsp").forward(request, response);
    }

    private User getUserFromRequest(HttpServletRequest request){
        User u = new User();
        String username,password,email,introduce,sex,isAccept,birthdaystr;
        Date birthday = new Date();
        String[]favorites;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        username = request.getParameter("username");
        password = request.getParameter("password");
        sex = request.getParameter("sex");
        email = request.getParameter("email");
        introduce = request.getParameter("introduce");
        birthdaystr = request.getParameter("birthday");

        if (birthdaystr!=null&&birthdaystr.length()!=0) {
            try {
                birthday = sdf.parse(birthdaystr);
            } catch (ParseException e) {
                System.out.println("birthday时间格式化失败！");
            }
        }

        favorites = request.getParameterValues("favorite");//getParamterValues返回字符串数组
        isAccept = request.getParameter("isAccept");

        u.setUsername(username);
        u.setPassword(password);
        u.setEmail(email);
        u.setBirthday(birthday);
        u.setIntroduce(introduce);
        u.setFavorites(favorites);
        u.setSex(sex);
        u.setFlag(isAccept!=null&&isAccept.equals("true")?true:false);

        return u;
    }
}
