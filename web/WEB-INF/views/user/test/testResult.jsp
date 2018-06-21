<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 梅峰鑫
  Date: 2018/6/17
  Time: 20:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>成绩显示页面</title>
</head>
<body>
    此次得分：${mark}分。<br/>

    <table border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
            <th colspan="2">试卷编号：</th>
            <td>${testPaper.id}</td>
        </tr>
        <tr>
            <th colspan="2">试卷名称：</th>
            <td>${testPaper.name}</td>
        </tr>


        <tr>
            <th width="20">编号</th>
            <th width="300">题干</th>
            <th width="70">答案</th>
            <th width="300">选项</th>
        </tr>
        <c:set var="num" value="0"></c:set>
        <c:forEach var="question" items="${testPaper.questions}">
            <tr>
                <td align="center" rowspan="6">${num = num+1}</td>
                <td align="center" rowspan="6">
                    <textarea cols="50" rows="4">${question.stem}</textarea>
                </td>
            </tr>
            <tr>
                <td align="center" rowspan="6">${question.answer}</td>
            </tr>
            <c:forEach var="option" items="${question.options}">
                <tr>
                    <td align="center">
                        <textarea cols=30 rows=1>${option.item}.${option.content}</textarea>
                    </td>
                </tr>
            </c:forEach>
            <tr>
                <td colspan="3"><br/></td>
            </tr>
        </c:forEach>

        <tr>
            <td colspan="3" align="center">
                <input type="button" value="完成" onclick="window.location.href='/views/user/right.jsp'">
            </td>
        </tr>
    </table>
</body>
</html>
