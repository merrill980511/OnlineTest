<%--
  Created by IntelliJ IDEA.
  User: 梅峰鑫
  Date: 2018/6/18
  Time: 16:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="/admin/upload?cmd=check" method="post" enctype="multipart/form-data">
    Excel文件导入（仅支持.xlsx文件,只支持文字类型）：<input type="file" name="questions" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"><br/>
    <input type="submit" value="提交">
</form>
</body>
</html>
