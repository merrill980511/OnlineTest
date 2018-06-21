<%--
  Created by IntelliJ IDEA.
  User: 梅峰鑫
  Date: 2018/6/11
  Time: 16:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>修改用户信息页面</title>
</head>
<body>
<form name="form" action="/admin/userInfo?cmd=updateUserInfo" method="post" >
    <table border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
            <td width="80">用户学号</td>
            <td width="226"><input type="text" name="id" id="id" value="${USER_IN_SESSION.id}" readonly></td>
        </tr>
        <tr>
            <td width="80">用&nbsp;&nbsp;户&nbsp;&nbsp;名</td>
            <td width="226"><input type="text" name="name" required value="${USER_IN_SESSION.name}"></td>
        </tr>
        <tr>
            <td>性&emsp;&emsp;别</td>
            <td>
                <input type="radio" name="sex" value="男" ${USER_IN_SESSION.sex == "男" ? "checked" : ""}>男
                <input type="radio" name="sex" value="女" ${USER_IN_SESSION.sex == "女" ? "checked" : ""}>女
            </td>
        </tr>
        <tr>
            <td>Q&emsp;&emsp;Q</td>
            <td><input type="text" name="qq" value="${USER_IN_SESSION.qq}"></td>
        </tr>
        <tr>
            <td>邮&emsp;&emsp;箱</td>
            <td><input type="email" name="email" id="email" value="${USER_IN_SESSION.email}" required></td>
        </tr>
        <tr>
            <td>联系方式</td>
            <td><input type="text" name="phone" id="phone" maxlength="11" value="${USER_IN_SESSION.phone}"></td>
        </tr>
        <tr>
            <td>个人说明</td>
            <td>
                <textarea rows="8"  cols="15" name="remark">${USER_IN_SESSION.remark}</textarea>
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
