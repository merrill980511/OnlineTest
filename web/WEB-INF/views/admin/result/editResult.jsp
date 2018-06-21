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
    <title>修改考试成绩</title>
</head>
<body>
<form action="/admin/result?cmd=editResult" method="post">
    <input type="hidden" value="${testResult.id}" name="id">
    <table cellspacing="0" cellpadding="0" border="0" align="center">
        <tr>
            <th align="right">学号</th>
            <td><input type="text" name="userId" value="${testResult.user.id}" readonly></td>
        </tr>
        <tr>
            <th align="right">姓名</th>
            <td><input type="text" name="userName" value="${testResult.user.name}" readonly></td>
        </tr>
        <tr>
            <th align="right">考试编号</th>
            <td><input type="text" name="testId" value="${testResult.testPaper.id}" readonly></td>
        </tr>
        <tr>
            <th align="right">开始名称</th>
            <td><input type="text" name="testName" value="${testResult.testPaper.name}" readonly></td>
        </tr>
        <tr>
            <th width="70" align="right">考试成绩</th>
            <td><input type="text" name="mark" value="${testResult.mark}"></td>
        </tr>
        <tr>
            <td colspan="2" align="center">
                <input type="submit" value="提交">
                <input type="reset" value="重置">
            </td>
        </tr>
    </table>
</form>
</body>
</html>
