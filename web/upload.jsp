<%--
  Created by IntelliJ IDEA.
  User: yvettee
  Date: 2017/10/13
  Time: 14:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>上传附件</title>
</head>
<script type="text/javascript">
    function addFile() {
        var files = document.getElementById("files");

        var input = document.createElement("input");
        input.type = 'file';
        input.name = 'file';

        var btn = document.createElement("input");
        btn.type = "button";
        btn.value = "删除";
        btn.onclick = function del() {
            this.parentNode.parentNode.removeChild(this.parentNode);
        }
        var div = document.createElement("div");
        div.appendChild(input);
        div.appendChild(btn);

        files.appendChild(div);
    }
</script>
<body>
<form action="/upLoadServlet3" method="post" enctype="multipart/form-data">
    <table>
        <tr>
            <td>上传用户：</td>
            <td><input type="text" name="userName"></td>
        </tr>

        <tr>
            <td>上传文件：</td>
            <td><input type="button" value="添加上传文件" onclick="addFile()"></td>
        </tr>

        <tr>
            <td></td>
            <td>
                <div id="files">

                </div>
            </td>
        </tr>
        <tr>
            <td></td>
            <td>
                <input type="submit" value="上传">
            </td>
        </tr>
    </table>
</form>
</body>
</html>
