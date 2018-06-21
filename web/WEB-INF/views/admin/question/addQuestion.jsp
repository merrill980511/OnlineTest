<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 梅峰鑫
  Date: 2018/6/12
  Time: 16:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>添加试题</title>
</head>
<body>
<hr/><br/>
<form name="form" action="/admin/question?cmd=addQuestion" method="post" >
    <table border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
            <th>题干：</th>
            <td>
                <textarea name="stem"></textarea>
            </td>
            <%--<td><input type="text" name="stem" width="600"/></td>--%>
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
        <tr>
            <th>题型：</th>
            <td>
                <select name="selection" >
                    <c:forEach items="${selections}" var="selection">
                        <option value="${selection.id}">${selection.name}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <th>答案：</th>
            <td><input type="text" name="answer" width="100" maxlength="6"/></td>
        </tr>
        <c:forEach var="i" begin="1" end="${6}">
            <tr>
                <td>选项编号（如ABCD）：<input type="text" name="item${i}" size="4" maxlength="1" >&emsp;&emsp;</td>
                <td>
                    <textarea name="content${i}"></textarea>
                </td>
                <%--<td>选项内容：<input type="text" name="content${i}" width="300"></td>--%>
            </tr>
        </c:forEach>
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
