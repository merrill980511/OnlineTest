<%--
  Created by IntelliJ IDEA.
  User: 梅峰鑫
  Date: 2018/6/13
  Time: 19:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>用户信息修改</title>
</head>
<body>
<form name="form" action="/user/editInfo?cmd=edit" method="post" >
    <table border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
            <td width="80">管理员账号</td>
            <td width="226"><input type="text" name="id" id="id" value="${user.id}" readonly></td>
        </tr>
        <tr>
            <td width="80">用&nbsp;&nbsp;户&nbsp;&nbsp;名</td>
            <td width="226"><input type="text" name="name" required value="${user.name}"></td>
        </tr>
        <tr>
            <td>性&emsp;&emsp;别</td>
            <td>
                <input type="radio" name="sex" value="男" ${user.sex == "男" ? "checked" : ""}>男
                <input type="radio" name="sex" value="女" ${user.sex == "女" ? "checked" : ""}>女
            </td>
        </tr>
        <tr>
            <td>Q&emsp;&emsp;Q</td>
            <td><input type="text" name="qq" value="${user.qq}"></td>
        </tr>
        <tr>
            <td>邮&emsp;&emsp;箱</td>
            <td><input type="email" name="email" id="email" value="${user.email}" required></td>
        </tr>
        <tr>
            <td>联系方式</td>
            <td><input type="text" name="phone" id="phone" maxlength="11" value="${user.phone}"></td>
        </tr>
        <tr>
            <td>个人说明</td>
            <td>
                <textarea rows="8"  cols="15" name="remark">${user.remark}</textarea>
            </td>
        </tr>
        <tr>
            <td colspan="2" align="center">
                <input type="submit" value="提交">&emsp;&emsp;&emsp;
                <input type="reset" value="重置">&emsp;&emsp;&emsp;
                <input type="button" value="返回" onclick="history.go(-1)">
            </td>
        </tr>
    </table>
</form>
</body>
</html>
