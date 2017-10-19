package com.chinasofti.UploadServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

/**
 * Created by yvettee on 2017/10/13.
 */
@WebServlet(name = "DownloadServlet", urlPatterns = "/downloadServlet")
public class DownloadServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //得到文件名(URL的)
        String fileName = request.getParameter("fileName");
        //中文要设置编码（get方式请求的）
        fileName = new String(fileName.getBytes("iso8859-1"), "UTF-8");
        System.out.println("文件名=" + fileName);
        //找出这个文件
        String path = this.getServletContext().getRealPath("/WEB-INF/upload") + File.separator + getPath(fileName);
        System.out.println("path=" + path);
        File file = new File(path + File.separator + fileName);
        System.out.println("路径=" + file.getAbsolutePath());

        if (!file.exists()) {
            request.setAttribute("message", "对不起，您要访问的资源不存在。");
            request.getRequestDispatcher("/message.jsp").forward(request, response);
            return;
        }

        //得到文件的原始文件名
        String oldName = file.getName().substring(file.getName().indexOf("_") + 1);
        System.out.println("oldName = " + oldName);
        //通知浏览器以下载方式打开下面发送的数据
        response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(oldName, "UTF-8"));

        FileInputStream in = new FileInputStream(file);
        int len = 0;
        byte buffer[] = new byte[1024];
        OutputStream out = response.getOutputStream();
        while ((len = in.read(buffer)) > 0) {
            out.write(buffer, 0, len);
        }
        in.close();
    }

    public String getPath(String fileName) {
        int hashCode = fileName.hashCode();
        int dir1 = hashCode & 0xf;
        int dir2 = (hashCode >> 4) & 0xf;

        return dir1 + File.separator + dir2;
    }
}
