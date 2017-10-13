<%--
  Created by IntelliJ IDEA.
  User: yvettee
  Date: 2017/9/18
  Time: 22:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>文件的上传和下载</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/upLoadServlet3" enctype="multipart/form-data" method="post">
    上传用户：<input type="text" name="userName"><br>
    上传文件一：<input type="file" name="file1"><br>
    上传文件二：<input type="file" name="file2"><br>
    <input type="submit" value="上传">
</form>
</body>
</html>
