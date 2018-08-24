package com.demo.servlet.security;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description: 登录
 * @author: 01369674
 * @date: 2018/6/8
 */

@WebServlet(name = "loginServlet",urlPatterns = {"/servlet/login"})
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String basePath = req.getContextPath();

        if("admin".equals(username)&&"admin".equals(password)){
            req.getSession().setAttribute("username",username);
            String refer = req.getHeader("Referer");
            if(refer!=null){
                resp.sendRedirect(refer);
            }else{
                req.getRequestDispatcher(basePath).forward(req,resp);
            }
        }else{
            req.setAttribute("message","用户名或密码不正确");
            req.getRequestDispatcher(basePath+"/jsp/security/login.jsp").forward(req,resp);
        }
    }
}
