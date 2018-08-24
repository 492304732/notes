package com.demo1.servlet;

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
public class HelloServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("处理get请求。。。");
        PrintWriter out = resp.getWriter();
        out.println("<b>HelloServlet:get</b>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("处理post请求。。。");
        PrintWriter out = resp.getWriter();
        out.println("<b>HelloServlet:post</b>");
    }
}
