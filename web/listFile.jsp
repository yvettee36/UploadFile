<%--
  Created by IntelliJ IDEA.
  User: yvettee
  Date: 2017/10/13
  Time: 17:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>文件预览</title>
</head>
<body>
下载文件有：<br>
<c:forEach var="entry" items="${requestScope.map}">
    <c:url var="url" value="/downloadServlet">
        <c:param name="fileName" value="${entry.key}"></c:param>
    </c:url>
    ${entry.value} <a href="${url}">下载</a><br>
</c:forEach>
</body>
</html>
