package com.chinasofti.UploadServlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Created by yvettee on 2017/10/12.
 * 处理上传数据
 */
@WebServlet(name = "UpLoadServlet3", urlPatterns = "/upLoadServlet3")
public class UpLoadServlet3 extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //允许上传文件的类型
        List types = Arrays.asList("jpg", "gif", "avi", "txt", "mp4");

        try {
            //创建解析工厂
            DiskFileItemFactory factory = new DiskFileItemFactory();//默认10K
            factory.setSizeThreshold(1024 * 1024);//1M大小
            File file = new File(this.getServletContext().getRealPath("/temp"));
            factory.setRepository(file);//设置临时保存目录

            //创建解析器
            ServletFileUpload upload = new ServletFileUpload(factory);

            //监听解析了多少数据
            upload.setProgressListener(new ProgressListener() {
                @Override
                public void update(long pBytesRead, long pContentLength, int pItems) {
                    System.out.println("当前已解析：" + pBytesRead);
                }
            });
            //不能超过5M，超过5M，for循环解析时会抛出异常
            upload.setFileSizeMax(5 * 1024 * 1024);
            //判断上传的表单是否是Multipart表单
            if (!upload.isMultipartContent(request)) {
                //按照传统方式提交表单
                request.getParameter("userName");
                return;
            }
            //看一下表单提交的普通输入项值和文件名是什么编码，就设置文件上传的编码
            upload.setHeaderEncoding("UTF-8");//解决了文件名的乱码问题
            //调用解析器解析request，得到保存了所有上传数据的list
            List<FileItem> list = upload.parseRequest(request);
            //迭代list集合，拿到封装了每个输入项数据的fileItem
            for (FileItem item : list) {
                //判断item的类型
                if (item.isFormField()) {
                    //为普通输入项
                    String inputName = item.getFieldName();
                    String inputValue = item.getString("UTF-8");
                    // inputValue = new String(inputValue.getBytes("iso8859-1"),"UTF-8");//输入框值的编码
                } else {
                    //代表当前处理的item里面封装的是上传文件
                    String fileName = item.getName();//得到上传文件名,例如"a.txt"
                    if (fileName == null || fileName.trim().equals("")) {
                        continue;
                    }
                    String extendName = fileName.substring(fileName.lastIndexOf(".") + 1);
                    if (!types.contains(extendName)) {
                        request.setAttribute("message", "本系统不支持" + extendName + "这种类型！");
                        request.getRequestDispatcher("/message.jsp").forward(request, response);
                        return;
                    }
                    InputStream in = item.getInputStream();//得到数据
                    int len = 0;
                    byte buffer[] = new byte[1024];
                    //让外界访问不到你的文件保存路径
                    String saveFileName = generateFileName(fileName);
                    String savePath = generateSavePath(this.getServletContext().getRealPath("/WEB-INF/upload"), saveFileName);
                    FileOutputStream out = new FileOutputStream(savePath + File.separator + saveFileName);
                    while ((len = in.read(buffer)) > 0) {
                        out.write(buffer, 0, len);
                    }

                    in.close();
                    out.close();

                    //删除临时文件，必须在流关闭之后
                    //为了确保临时文件无论如何都要被删除，就要放在try/catch里面
                    item.delete();
                }
            }
        } catch (FileUploadBase.FileSizeLimitExceededException e) {
            request.setAttribute("message", "文件大小不能超过5M!");
            request.getRequestDispatcher("/message.jsp").forward(request, response);
            return;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        request.setAttribute("message", "上传成功！！");
        request.getRequestDispatcher("/message.jsp").forward(request, response);
    }

    //产生唯一的文件名
    public String generateFileName(String fileName) {
        return UUID.randomUUID().toString() + "_" + fileName;
    }

    //文件打散存储
    public String generateSavePath(String path, String fileName) {
        int hashCode = fileName.hashCode();//二进制32位
        int dir1 = hashCode & 0xf;//一级目录
        int dir2 = (hashCode >> 4) & 0xf;//二级目录
        String savePath = path + File.separator + dir1 + File.separator + dir2;
        File file = new File(savePath);
        if (!file.exists()) {
            file.mkdirs();//多级目录
        }
        return savePath;
    }
}
