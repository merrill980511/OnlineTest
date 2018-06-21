<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 梅峰鑫
  Date: 2018/6/14
  Time: 20:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>考试结果查询界面</title>
</head>
<body>
<form action="/user/result" method="post">
    <br/>
    <table cellspacing="0" cellpadding="0" border="0" align="center">
        <tr>
            <td align="right">试卷编号：</td>
            <td><input type="text" width="300" align="center" name="id2" value="${queryTestResult.testId}"><hr/></td>
        </tr>
        <tr>
            <td align="right">试卷名称：</td>
            <td><input type="text" width="300" align="center" name="name2" value="${queryTestResult.testName}"><hr/></td>
        </tr>
        <tr>
            <td align="right">最小成绩：</td>
            <td><input type="number" width="300" id="min" align="center" name="min" min="0" max="100" value="${queryTestResult.min}"><hr/></td>
        </tr>
        <tr>
            <td align="right">最大成绩：</td>
            <td><input type="number" width="300" id="max" align="center" name="max" min="0" max="100" value="${queryTestResult.max}"><hr/></td>
        </tr>
        <tr>
            <td align="right">学科：</td>
            <td>
                <select name="sort">
                    <option value="-1">    </option>
                    <c:forEach items="${sorts}" var="item">
                        <option ${item.id == queryTestResult.sort?"selected":""} value="${item.id}">${item.name}</option>
                    </c:forEach>
                </select><hr/>
            </td>
        </tr>
        <tr>
            <td colspan="2" align="center">
                <input type="button" value="查询" onclick="check();">
            </td>
        </tr>
    </table>

    <br/>
    <table cellspacing="0" cellpadding="0" border="1" align="center">
        <tr>
            <th width="150">科目</th>
            <th width="100">考试编号</th>
            <th width="150">考试名称</th>
            <th width="70">考试成绩</th>
        </tr>
        <c:forEach var="testResult" items="${pageResult.listData}">
            <tr>
                <td align="center">${testResult.testPaper.sort.name}</td>
                <td align="center">${testResult.testPaper.id}</td>
                <td align="center">${testResult.testPaper.name}</td>
                <td align="center">${testResult.mark}</td>
            </tr>
        </c:forEach>
        <tr>
            <td colspan="8" align="center">
                <input type="button" value="返回" onclick="window.location.href='/views/user/right.jsp'">
            </td>
        </tr>
    </table>
    <table width="98%" border="0" align="center" cellpadding="2" cellspacing="0">
        <tr>
            <td  colspan="7" align="center"  >
                <a href="/user/result?cmd=&currentPage=1&pageSize=${pageResult.pageSize}">首页</a>
                <a href="/user/result?currentPage=${pageResult.prevPage}&pageSize=${pageResult.pageSize}">上页</a>
                &nbsp;${pageResult.currentPage}&nbsp;
                <a href="/user/result?currentPage=${pageResult.nextPage}&pageSize=${pageResult.pageSize}">下页</a>
                <a href="/user/result?currentPage=${pageResult.totalPage}&pageSize=${pageResult.pageSize}">末页</a>

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
    function check() {
        var max = document.getElementById("max").value;
        var min = document.getElementById("min").value;
        if (max-min>=0){
            document.forms[0].submit();
        } else {
            alert("输入的成绩范围有误！");
        }
    }
</script>
</body>
</html>
