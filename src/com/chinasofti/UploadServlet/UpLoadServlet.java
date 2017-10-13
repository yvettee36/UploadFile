package com.chinasofti.UploadServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by yvettee on 2017/10/12.
 */
@WebServlet(name = "UpLoadServlet", urlPatterns = "/upLoadServlet")
public class UpLoadServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        //如果表单类型为multpart/form-data，采用传统方式是取不到数据的
//        String name = request.getParameter("userName");
//        System.out.println(name);

        InputStream in = request.getInputStream();
        int len = 0;
        byte buffer[] = new byte[1024];
        while ((len = in.read(buffer)) > 0) {
            System.out.println(new String(buffer, 0, len));
        }
    }
}
