package com.chinasofti.UploadServlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by yvettee on 2017/10/12.
 * 处理上传数据
 */
@WebServlet(name = "UpLoadServlet2", urlPatterns = "/upLoadServlet2")
public class UpLoadServlet2 extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //创建工厂
            DiskFileItemFactory factory = new DiskFileItemFactory();
            //创建解析器
            ServletFileUpload upload = new ServletFileUpload(factory);
            List<FileItem> list = upload.parseRequest(request);
            for (FileItem item : list) {
                if (item.isFormField()) {
                    //为普通输入项
                    String inputName = item.getFieldName();
                    String inputValue = item.getString();
                    System.out.println(inputName + "=" + inputValue);
                } else {
                    //代表当前处理的item里面封装的是上传文件
                    String fileName = item.getName();//得到上传文件名,例如"a.txt"
                    InputStream in = item.getInputStream();//得到数据
                    int len = 0;
                    byte buffer[] = new byte[1024];
                    FileOutputStream out = new FileOutputStream("D:\\" + fileName);
                    while ((len = in.read(buffer)) > 0) {
                        out.write(buffer, 0, len);
                    }
                    in.close();
                    out.close();
                }
            }

        } catch (FileUploadException e) {
            throw new RuntimeException(e);
        }
    }
}