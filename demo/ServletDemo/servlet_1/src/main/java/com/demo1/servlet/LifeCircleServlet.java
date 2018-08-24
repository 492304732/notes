package com.demo1.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Description: TODO
 * @author: 01369674
 * @date: 2018/5/22
 */
public class LifeCircleServlet extends HttpServlet {
    public LifeCircleServlet() {
        System.out.println("LifeCircleServlet构造方法被执行！");
    }

    public void destroy() {
        System.out.println("LifeCircleServlet销毁方法被执行！");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("LifeCircleServlet的doGet方法被执行！");
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
        out.println("<HTML>");
        out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
        out.println("  <BODY>");
        out.println("<h1>你好我是LifeCircleServlet</h1>");
        out.println("  </BODY>");
        out.println("</HTML>");
        out.flush();
        out.close();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("LifeCircleServlet的doPost方法被执行！");
        doGet(request, response);// 让doPost与doGet执行相同的操作
    }

    public void init() throws ServletException {
        System.out.println("LifeCircleServlet的初始化方法被执行！");
    }
}
