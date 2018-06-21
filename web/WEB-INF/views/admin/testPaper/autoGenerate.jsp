<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 梅峰鑫
  Date: 2018/6/14
  Time: 18:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>自动生成试卷界面</title>
</head>
<body>
<form action="/admin/testPaper?cmd=autoGenerate" method="post">
    <table cellpadding="0" cellspacing="0" border="0" align="center">
        <tr>
            <th>名称：</th>
            <td><input type="text" name="name" required></td>
        </tr>
        <tr>
            <th>学科：</th>
            <td>
                <select name="sort" >
                    <c:forEach items="${sorts}" var="sort">
                        <option value="${sort.id}">${sort.name}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <c:forEach items="${selections}" var="selection">
            <tr>
                <th>${selection.name}：</th>
                <td><input type="number" min="0" name="${selection.id}"><br/></td>
            </tr>
        </c:forEach>
        <tr>
            <td colspan="2" >
                <input type="submit" value="生成">&emsp;
                <input type="reset" value="重置">
            </td>
        </tr>
    </table>
</form>
</body>
</html>
