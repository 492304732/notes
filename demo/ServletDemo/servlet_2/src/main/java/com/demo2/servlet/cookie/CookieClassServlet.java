package com.demo2.servlet.cookie;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Description: 根据cookie保存的用户偏好（每页显示多少条数据）选择显示
 * @author: 01369674
 * @date: 2018/5/23
 */

@WebServlet(name = "CookieClassServlet",urlPatterns = { "/cookieClass" })
public class CookieClassServlet extends HttpServlet {
    private static final long serialVersionUID = 837369L;
    private String[] methods = {
            "clone", "getComment", "getDomain",
            "getMaxAge", "getName", "getPath",
            "getSecure", "getValue", "getVersion",
            "isHttpOnly", "setComment", "setDomain",
            "setHttpOnly", "setMaxAge", "setPath",
            "setSecure", "setValue", "setVersion"
    };

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        Cookie maxRecordsCookie = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("maxRecords")) {
                    maxRecordsCookie = cookie;
                    break;
                }
            }
        }
        int maxRecords = 5; // default
        if (maxRecordsCookie != null) {
            try {
                maxRecords = Integer.parseInt(maxRecordsCookie.getValue());
            } catch (NumberFormatException e) {
                // do nothing, use maxRecords default value
            }
        }
        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();
        writer.print("<html><head>" + "<title>Cookie Class</title>"
                        + "</head><body>"
                        + PreferenceServlet.MENU
                        + "<div>Here are some of the methods in " +
                        "javax.servlet.http.Cookie");
        writer.print("<ul>");
        for (int i = 0; i < maxRecords; i++) {
            writer.print("<li>" + methods[i] + "</li>");
        }
        writer.print("</ul>");
        writer.print("</div></body></html>");
    }
}
