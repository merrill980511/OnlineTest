<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 梅峰鑫
  Date: 2018/6/13
  Time: 16:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>学科查询页面</title>
</head>
<body>
<form action="/admin/sort" method="post">
    <br/>
    <table cellspacing="0" cellpadding="0" border="1" align="center">
        <tr>
            <th width="30"></th>
            <th width="100">编号</th>
            <th width="300">名称</th>
        </tr>
        <c:forEach items="${sorts}" var="sort">
            <tr>
                <td align="center"><input type="checkbox" name="id" value="${sort.id}" /></td>
                <td align="center">${sort.id}</td>
                <td align="center">${sort.name}</td>
            </tr>
        </c:forEach>
        <tr>
            <td colspan="7" align="center">
                <input type="button" value="删除" onclick="del()">&emsp;&emsp;&emsp;
                <input type="button" value="添加" onclick="window.location.href='/admin/sort?cmd=add'">
            </td>
        </tr>
    </table>
</form>
<script>
    function del() {
        var names = document.getElementsByName("id");
        var flag = false ;//标记判断是否选中一个
        for(var i=0;i<names.length;i++){
            if(names[i].checked){
                flag = true ;
            }
        }
        if(!flag){
            alert("请选择要操作的项！");
        } else {
            if(confirm("确定要要执行此操作吗?")){
                document.forms[0].action = "/admin/sort?cmd=delete";
                document.forms[0].submit();
            }
        }
    }
</script>
</body>
</html>
