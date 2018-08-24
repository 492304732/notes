package com.demo2.servlet.file;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @Description: 文件上传
 * @author: 01369674
 * @date: 2018/5/28
 */

@WebServlet(urlPatterns = {"/servlet/upload"}, name="uploadServlet")
public class UploadServlet extends HttpServlet {
    /** 上传目录名 */
    private static final String UPLOAD_DIR = "uploadDir/img/";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("UploadServlet");
        /****** 初始化部分 ******/
        //设置编码格式，前端后台统一是utf-8
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");

        /****** 上传文件******/
        //获取目录，自定义文件名
        ServletContext context = request.getServletContext();
        String realPath = context.getRealPath(UPLOAD_DIR)+"\\";
        String fileName = "test.png";
        String filePath = realPath+fileName;
        //创建目录
        File dir = new File(realPath);
        if(!dir.exists()){
            dir.mkdirs();
        }
        //获取输入输出流,上传文件
        InputStream fileSource = request.getInputStream();
        File file = new File(filePath);
        OutputStream outputStream = new FileOutputStream(file);
        try {
            byte[] b = new byte[1024];
            int n;
            while ((n = fileSource.read(b)) != -1) {
                outputStream.write(b, 0, n);
            }
            fileSource.close();
        } finally {
            outputStream.close();
        }

        /****** 返回前端 ******/
        try {
            request.setAttribute("path",filePath);
            request.getRequestDispatcher("/jsp/file/uploadFile.jsp").forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }
}
