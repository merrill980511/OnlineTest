<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 梅峰鑫
  Date: 2018/6/14
  Time: 10:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>试卷完善</title>
</head>
<body>
<form name="form" action="/admin/testPaper?cmd=editTestPaper" method="post" >
    <table border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
            <th colspan="2">试卷编号：</th>
            <td><input type="text" name="testPaperId" width="300" value="${testPaper.id}" readonly/></td>
        </tr>
        <tr>
            <th colspan="2">试卷名称：</th>
            <td><input type="text" name="name" width="600" value="${testPaper.name}"/></td>
        </tr>
        <tr>
            <th colspan="2">类型：</th>
            <td>
                <select name="sort" readonly>
                    <c:forEach items="${sorts}" var="sort">
                        <option value="${sort.id}" ${testPaper.sort.id == sort.id?"selected":""}>${sort.name}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <th colspan="2">开始时间：</th>
            <td ><input type="datetime" name="startTime" width="100" value="${testPaper.startTime}"/></td>
        </tr>
        <tr>
            <th colspan="2">结束时间：</th>
            <td><input type="datetime" name="endTime" width="100" value="${testPaper.endTime}"/></td>
        </tr>
        <tr>
            <th colspan="2">总时长（分钟）：</th>
            <td ><input type="number" name="time" min="1" width="100" value="${testPaper.time}"/></td><br/>
        </tr>
        <c:forEach items="${testPaper.questions}" var="question">
            <tr>
                <td align="center"><input type="checkbox" name="id" value="${question.id}" /></td>
                <td>题干：
                    <textarea readonly>${question.stem}</textarea>
                <td>&emsp;答案：<input type="text" name="answer${question.id}" width="300" value="${question.answer}" readonly>&emsp;</td>
                <td>
                    <textarea rows="2" content="30"><c:forEach var="option" items="${question.options}">${option.item}.${option.content}</c:forEach></textarea>
                </td>
            </tr>
        </c:forEach>
        <tr>
            <td colspan="3" align="center">
                <input type="submit" value="提交">&emsp;&emsp;&emsp;
                <input type="button" value="删除" onclick="del();">&emsp;&emsp;&emsp;
                <input type="button" value="添加" onclick="window.location.href='/admin/testPaper?cmd=addQuestion'">&emsp;&emsp;&emsp;
                <input type="reset" value="重置">&emsp;&emsp;&emsp;
                <input type="button" value="返回" onclick="history.go(-1);">
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
            if(confirm("删除后不可撤销，确定要要执行此操作吗?")){
                document.forms[0].action = "/admin/testPaper?cmd=deleteQuestion";
                document.forms[0].submit();
            }
        }
    }
</script>
</body>
</html>
