<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 梅峰鑫
  Date: 2018/6/17
  Time: 9:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>添加考试成绩</title>
</head>
<body>
<form action="/admin/result?cmd=addResult" method="post">
    <table cellspacing="0" cellpadding="0" border="0" align="center">
        <tr>
            <th width="100" align="right">学号</th>
            <td><input type="text" name="userId"></td>
        </tr>
        <tr>
            <th width="100" align="right">考试编号</th>
            <td><input type="text" name="testId"></td>
        </tr>
        <tr>
            <th width="70" align="right">考试成绩</th>
            <td><input type="text" name="mark"></td>
        </tr>
        <tr>
            <td colspan="2" align="center">
                <input type="submit" value="提交">
                <input type="reset" value="重置">
                <input type="button" value="返回" onclick="history.go(-1);">
            </td>
        </tr>
    </table>
</form>
</body>
</html>
