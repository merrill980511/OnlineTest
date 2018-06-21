<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 梅峰鑫
  Date: 2018/6/12
  Time: 16:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>题库管理</title>
</head>
<body body style=" overflow-y:auto; overflow-x:auto;height:1500px;">
<form action="/admin/question" method="post">
    <center>
        编号：<input type="number" width="300" align="center" name="id2" value="${queryQuestion.id}">&emsp;
        题干：<input type="text" width="300" align="center" name="stem2" value="${queryQuestion.name}">&emsp;
        学科：<select name="sort" onchange="go();">
                <option value="-1">    </option>
                <c:forEach items="${sorts}" var="item">
                    <option ${item.id == queryQuestion.sort?"selected":""} value="${item.id}">${item.name}</option>
                </c:forEach>
              </select>
        题型：<select name="selection" onchange="go();">
                <option value="-1">    </option>
                <c:forEach items="${selections}" var="item">
                    <option ${item.id == queryQuestion.selection?"selected":""} value="${item.id}">${item.name}</option>
                </c:forEach>
            </select>&emsp;
        <input type="button" value="查询" onclick="go();">
    </center>
    <br/>
    <table cellspacing="0" cellpadding="0" border="1" align="center">
        <tr>
            <th width="30"></th>
            <th width="100">编号</th>
            <th width="300">题干</th>
            <th width="100">类型</th>
            <th width="70">答案</th>
            <th width="300">选项</th>
        </tr>
        <c:forEach var="question" items="${pageResult.listData}">
            <tr>
                <td align="center"><input type="checkbox" name="id" value="${question.id}" /></td>
                <td align="center"><a href="/admin/question?cmd=edit&id=${question.id}">${question.id}</a></td>
                <td align="center">${question.stem}</td>
                <td align="center">${question.sort.name}</td>
                <td align="center">${question.answer}</td>
                <td align="center">

                    <c:forEach var="option" items="${question.options}">
                        <textarea cols=20 rows=1>${option.item}.${option.content}</textarea>
                    </c:forEach>
                </td>
            </tr>
        </c:forEach>
        <tr>
            <td colspan="6" align="center">
                <input type="button" value="删除" onclick="del()">&emsp;&emsp;&emsp;
                <input type="button" value="添加" onclick="window.location.href='/admin/question?cmd=add'">&emsp;&emsp;&emsp;
                <input type="button" value="导入题库" onclick="window.location.href='/admin/upload'">
            </td>
        </tr>
    </table>
    <table width="98%" border="0" align="center" cellpadding="2" cellspacing="0">
        <tr>
            <td  colspan="6" align="center"  >
                <a href="/admin/question?currentPage=1&pageSize=${pageResult.pageSize}">首页</a>
                <a href="/admin/question?currentPage=${pageResult.prevPage}&pageSize=${pageResult.pageSize}">上页</a>
                &nbsp;${pageResult.currentPage}&nbsp;
                <a href="/admin/question?currentPage=${pageResult.nextPage}&pageSize=${pageResult.pageSize}">下页</a>
                <a href="/admin/question?currentPage=${pageResult.totalPage}&pageSize=${pageResult.pageSize}">末页</a>

                当前第${pageResult.currentPage}/${pageResult.totalPage}页，
                一共${pageResult.totalCount}条数据
                &emsp;
                跳转到<input type="number" min="1" max="${pageResult.totalPage}" value="${pageResult.currentPage}"
                          style="width: 50px" name="currentPage"/>页
                <input type="button" value="GO" onclick="go();">

                每页<select name="pageSize" onchange="go();">
                <c:forEach items="${pageResult.pageItems}" var="item">
                    <option ${item == pageResult.pageSize?"selected":""}>${item}</option>
                </c:forEach>
            </select>
            </td>
        </tr>
    </table>
</form>
<script>
    function go() {
        document.forms[0].submit();
    }
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
                document.forms[0].action = "/admin/question?cmd=delete";
                document.forms[0].submit();
            }
        }
    }
</script>
</body>
</html>
