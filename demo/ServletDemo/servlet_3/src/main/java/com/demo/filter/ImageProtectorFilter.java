package com.demo.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @Description: 图片保护过滤器
 * 用于在浏览器中输入图像文件的 URL路径时，防止下载图像文件。
 * 应用中的图像文件只有当图像链接在页面中被点击的时候才会显示。
 * 该Filter的实现原理是检查HTTP Header的referer值。
 * 如果该值为null，就意味着当前的请求中没有referer值，
 * 即当前的请求是直接通过输入URL来访问该资源的。
 * @author: 01369674
 * @date: 2018/6/7
 */

//@WebFilter(filterName = "ImageProtetorFilter", urlPatterns = {"*.png", "*.jpg", "*.gif" })
public class ImageProtectorFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        System.out.println("ImageProtectorFilter");
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String referrer = httpServletRequest.getHeader("referer");
        System.out.println("referrer:" + referrer);
        if (referrer != null) {
            filterChain.doFilter(request, response);
        } else {
            throw new ServletException("Image not available");
        }
    }

    @Override
    public void destroy() {

    }
}
