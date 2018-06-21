<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 梅峰鑫
  Date: 2018/6/13
  Time: 15:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>题目内容修改</title>
</head>
<body>
<form name="form" action="/admin/question?cmd=editQuestion" method="post" >
    <table border="0" align="center" cellpadding="0" cellspacing="0">
        <input type="hidden" name="id" value="${question.id}">
        <tr>
            <th>题干：</th>
            <td>
                <textarea name="stem">${question.stem}</textarea>
            </td>
            <%--<td><input type="text" name="stem" width="600" value="${question.stem}"/></td>--%>
        </tr>
        <tr>
            <th>题型：</th>
            <td>
                <select name="sort" >
                    <c:forEach items="${sorts}" var="sort">
                        <option value="${sort.id}" ${question.sort.id == sort.id?"selected":""}>${sort.name}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <th>学科：</th>
            <td>
                <select name="selection" >
                    <c:forEach items="${selections}" var="selection">
                        <option value="${selection.id}" ${question.selection.id == selection.id?"selected":""} >${selection.name}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <th>答案：</th>
            <td><input type="text" name="answer" width="100" maxlength="6" value="${question.answer}"/></td>
        </tr>
        <c:forEach items="${question.options}" var="option">
            <tr>
                <td>选项编号（如ABCD）：<input type="text" name="item${option.id}" size="4" maxlength="1" value="${option.item}">&emsp;&emsp;</td>
                <td>
                    <textarea name="content${option.id}">${option.content}</textarea>
                </td>
                <%--<td>选项内容：<input type="text" name="content${option.id}" width="300" value="${option.content}"></td>--%>
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
