<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 梅峰鑫
  Date: 2018/6/13
  Time: 18:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>添加学科</title>
</head>
<body>
<form name="form" action="/admin/sort?cmd=addSort" method="post" >
    <table border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
            <th>学科：</th>
            <td><input type="text" name="name" width="600"/></td>
        </tr>
        <tr><td><br/></td></tr>
        <tr>
            <td colspan="2" align="center">
                <input type="submit" value="提交">&emsp;&emsp;&emsp;
                <input type="reset" value="重置">&emsp;&emsp;&emsp;
                <input type="button" value="返回" onclick="history.go(-1);">
            </td>
        </tr>
    </table>
</form>
</body>
</html>
