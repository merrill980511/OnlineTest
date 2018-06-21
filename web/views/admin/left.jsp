<%--
  Created by IntelliJ IDEA.
  User: 梅峰鑫
  Date: 2018/6/10
  Time: 14:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>在线考试系统</title>
</head>
<body background="/images/leftBackground.gif">
<table width="144" border="0" cellpadding="2" cellspacing="0" >
    <tr>
        <th width="139" align="center">功能列表</th>
    </tr>
    <tr>
        <td align="center"><a href="/admin?cmd=userInfo" target="AdminRightFrame">用户管理</a></td>
    </tr>
    <tr>
        <td align="center"><a href="/admin?cmd=question" target="AdminRightFrame">题库管理</a></td>
    </tr>
    <tr>
        <td align="center"><a href="/admin?cmd=sort" target="AdminRightFrame">学科管理</a></td>
    </tr>
    <tr>
        <td align="center"><a href="/admin?cmd=testPaper" target="AdminRightFrame">试卷管理</a></td>
    </tr>
    <tr>
        <td align="center"><a href="/admin?cmd=result" target="AdminRightFrame">成绩管理</a></td>
    </tr>
    <tr>
        <td align="center"><a href="/admin?cmd=editPassword" target="AdminRightFrame">修改密码</a></td>
    </tr>
    <tr>
        <td align="center"><a href="/admin?cmd=editInfo" target="AdminRightFrame">信息完善</a></td>
    </tr>
    <tr>
        <td align="center"><a href="/index.jsp" target="_top">安全退出</a></td>
    </tr>
</table>
</body>
</html>
