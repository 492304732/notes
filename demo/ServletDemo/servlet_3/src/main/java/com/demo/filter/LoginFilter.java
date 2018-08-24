package com.demo.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;


/**
 * @Description: 登录
 * @author: 01369674
 * @date: 2018/6/8
 */

@WebFilter(filterName = "LoginFilter",urlPatterns = "/*")
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession();
        String username = (String)session.getAttribute("username");
        String basePath = httpRequest.getContextPath();
        if(username==null){
            String url = httpRequest.getRequestURI();
            if(!url.contains("login.jsp")&&!url.contains("/servlet/login")){
                request.getRequestDispatcher(basePath+"/jsp/security/login.jsp").forward(request,response);
            }
        }
        chain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
