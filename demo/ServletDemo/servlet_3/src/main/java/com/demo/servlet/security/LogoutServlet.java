package com.demo.servlet.security;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description: TODO
 * @author: 01369674
 * @date: 2018/6/18
 */

@WebServlet(name = "logoutServlet",urlPatterns = {"/servlet/logout"})
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().removeAttribute("username");
        resp.sendRedirect("/jsp/security/login.jsp");
    }
}
