<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 梅峰鑫
  Date: 2018/6/17
  Time: 16:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>考试页面</title>
</head>
<body>
<form name="form" action="/user/test?cmd=check" method="post" >
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
            <th colspan="2">剩余时间：</th>
            <span id="clock"></span>
        </tr>

        <tr>
            <th width="20">编号</th>
            <th width="300">题干</th>
            <th width="300">选项</th>
        </tr>
        <c:set var="num" value="0"></c:set>
        <c:forEach var="question" items="${testPaper.questions}">
            <tr>
                <td align="center" rowspan="5">${num = num+1}</td>
                <td align="center" rowspan="5">
                    <textarea cols="50" rows="4">${question.stem}</textarea>
                </td>
            </tr>
            <c:forEach var="option" items="${question.options}">
                <tr>
                    <td align="center">
                        <c:if test="${question.selection.id == 1}">
                            <input type="radio" name="${question.id}" value="${option.item}">
                        </c:if>
                        <c:if test="${question.selection.id == 2}">
                            <input type="checkbox" name="${question.id}" value="${option.item}">
                        </c:if>
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
                <input type="submit" value="提交">&emsp;&emsp;&emsp;
                <input type="reset" value="重置">&emsp;&emsp;&emsp;
            </td>
        </tr>
    </table>


</form>
<script type="text/javascript">
    function countdown ()
    {
        var end = ${testPaper.time}*60000 + ${testTime};
        var now = new Date ().getTime();

        var m = Math.round ((end - now) / 1000);
        var hours = parseInt ((m % (3600 * 24)) / 3600);
        var minutes = parseInt ((m % 3600) / 60);
        var seconds = m % 60;

        if (m < 0)
        {
            document.getElementById ("clock").innerHTML = '0';
            document.forms[0].submit();
        }
        document.getElementById ("clock").innerHTML = "离结束还剩" + hours + "小时" + minutes + "分钟" + seconds + "秒";
        setTimeout ('countdown()', 1000);
    }
    window.onload = function ()
    {
        countdown ();
    }
</script>
</body>
</html>
