package com.demo.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

/**
 * @Description: 日志过滤器
 * 把 Request请求的URL记录到日志文本文件中。
 * 日志文本文件名通过Filter的初始化参数来配置。
 * @author: 01369674
 * @date: 2018/6/7
 */

public class LoggingFilter implements Filter {

    private PrintWriter logger;
    private String prefix;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        prefix = filterConfig.getInitParameter("prefix");
        String logFileName = filterConfig.getInitParameter("logFileName");
        String appPath = filterConfig.getServletContext().getRealPath("/");
        // without path info in logFileName, the log file will be
        // created in $TOMCAT_HOME/bin
        System.out.println("logFileName:" + logFileName);
        try {
            logger = new PrintWriter(new File(appPath, logFileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new ServletException(e.getMessage());
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        //System.out.println("LoggingFilter.doFilter");
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        logger.println(new Date() + " " + prefix + httpServletRequest.getRequestURI());
        logger.flush();
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        System.out.println("destroying filter");
        if (logger != null) {
            logger.close();
        }
    }
}
