package com.demo2.servlet.file;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @Description: 文件下载
 * @author: 01369674
 * @date: 2018/5/29
 */

@WebServlet(urlPatterns = {"/servlet/download"}, name="downloadServlet")
public class DownloadServlet extends HttpServlet {
    /** 上传目录名 */
    private static final String UPLOAD_DIR = "uploadDir/img/";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        System.out.println("DownloadServlet");
        resp.setHeader("content-type","text/html;charset=UTF-8");
        String path = getServletContext().getRealPath(UPLOAD_DIR)+"\\";
        String fileName = req.getParameter("filename");
        File file = new File(path + fileName);
        resp.setContentType("application/x-msdownload");
        resp.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
        if (file.exists()) {
            try (InputStream inputStream = new FileInputStream(file)) {
                OutputStream outputStream = resp.getOutputStream();
                byte b[] = new byte[1024];
                int n;
                while ((n = inputStream.read(b)) != -1) {
                    outputStream.write(b, 0, n);
                }
                outputStream.close();
                inputStream.close();
            }catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
