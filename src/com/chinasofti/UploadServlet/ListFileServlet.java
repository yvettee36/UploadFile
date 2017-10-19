package com.chinasofti.UploadServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yvettee on 2017/10/13.
 */
//列出网站所有文件
@WebServlet(name = "ListFileServlet", urlPatterns = "/listFileServlet")
public class ListFileServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = this.getServletContext().getRealPath("/WEB-INF/upload");
        Map map = new HashMap();
        listFile(new File(path), map);

        request.setAttribute("map", map);
        request.getRequestDispatcher("/listFile.jsp").forward(request, response);
    }

    //如何保存递归出来的资源
    public void listFile(File file, Map map) {
        if (!file.isFile()) {//判断是否是文件
            File[] children = file.listFiles();//得到子级目录
            for (File f : children) {
                listFile(f, map);//递归
            }
        } else {
            String fileName = file.getName().substring(file.getName().indexOf("_") + 1);
            map.put(file.getName(), fileName);//<a href="/servlet?fileName=文件在服务器的名称">文件的原始文件名</a>
        }
    }
}
