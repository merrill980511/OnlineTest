<%--
  Created by IntelliJ IDEA.
  User: 梅峰鑫
  Date: 2018/6/10
  Time: 11:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>在线考试系统</title>
</head>
    <frameset rows="80,*" cols="*" frameborder="no" border="0" framespacing="0">
        <frame src="/views/top.html" name="topFrame" scrolling="No" noresize="noresize" id="topFrame" title="topFrame" />
        <frameset rows="*" cols="155,*" frameborder="no" border="0" framespacing="0">
            <frame src="/views/admin/left.jsp" name="AdminLeftFrame" scrolling="No" noresize="noresize" id="AdminLeftFrame" title="AdminLeftFrame" />
            <frame src="/views/admin/right.jsp" name="AdminRightFrame" noresize="noresize" id="AdminRightFrame" title="AdminRightFrame" />
            <%--<frame src="right.jsp?action=query" name="mainFrame" id="mainFrame" title="mainFrame" />--%>
        </frameset>
    </frameset>
</html>
