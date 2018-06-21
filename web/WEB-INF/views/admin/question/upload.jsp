<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 梅峰鑫
  Date: 2018/6/18
  Time: 14:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>题库导入</title>
</head>
<body>
    <form action="/admin/upload?cmd=sort" method="post">
        分类：
        <select name="sort" id="sort">
            <c:forEach items="${sorts}" var="sort">
                <option value="${sort.id}">${sort.name}</option>
            </c:forEach>
        </select><br/>
        <input type="submit" value="确定">
    </form>
<script>
</script>
</body>
</html>
