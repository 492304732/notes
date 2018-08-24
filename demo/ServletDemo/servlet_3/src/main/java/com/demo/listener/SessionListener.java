package com.demo.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description: 统计当前 session 数量
 * @author: 01369674
 * @date: 2018/6/7
 */

@WebListener
public class SessionListener implements HttpSessionListener,ServletContextListener{

    /**
     * 初始化 session 计数为0，计数放在 ServletContext 域
     * @param sce
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        servletContext.setAttribute("userCounter", new AtomicInteger());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

    /**
     * session 创建后计数加 1
     * @param se
     */
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        ServletContext servletContext = session.getServletContext();
        //使用了AtomicInteger 来代替Integer类型是为了保证能同步进行加减的操作。
        AtomicInteger userCounter = (AtomicInteger) servletContext.getAttribute("userCounter");
        int userCount = userCounter.incrementAndGet();
        System.out.println("userCount incremented to :" + userCount);
    }

    /**
     * session 销毁时计数减 1
     * @param se
     */
    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        ServletContext servletContext = session.getServletContext();
        AtomicInteger userCounter = (AtomicInteger) servletContext.getAttribute("userCounter");
        int userCount = userCounter.decrementAndGet();
        System.out.println("---------- userCount decremented to:" + userCount);
    }
}
