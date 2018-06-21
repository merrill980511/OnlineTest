<%--
  Created by IntelliJ IDEA.
  User: 梅峰鑫
  Date: 2018/6/12
  Time: 16:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>添加新用户</title>
</head>
<body>
<script type="text/javascript">
    function check() {
        var pwd1 = document.getElementById("pwd1").value;
        var pwd2 = document.getElementById("pwd2").value;
        if (document.getElementById("id").value != "" &&
            document.getElementById("email").value != "" && pwd1 != "" &&pwd2 != ""){
            if (pwd1 == pwd2){
                form.submit();
            } else {
                alert("两次输入密码不一致！");
            }
        } else {
            alert("账号、邮箱、密码不能为空！");
        }
    }
</script>
<hr/><br/>
<form name="form" action="/admin/userInfo?cmd=addUser" method="post" >
    <table border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
            <td width="80">用户学号</td>
            <td width="226"><input type="text" name="id" id="id" required>*</td>
        </tr>
        <tr>
            <td width="80">用&nbsp;&nbsp;户&nbsp;&nbsp;名</td>
            <td width="226"><input type="text" name="name" required>*</td>
        </tr>
        <tr>
            <td>密&emsp;&emsp;码</td>
            <td><input type="password" name="password" id="pwd1" required>*</td>
        </tr>
        <tr>
            <td>密码确认</td>
            <td><input type="password" name="pwd" id="pwd2" required>*</td>
        </tr>
        <tr>
            <td>性&emsp;&emsp;别</td>
            <td>
                <input type="radio" name="sex" value="男" checked>男
                <input type="radio" name="sex" value="女">女
            </td>
        </tr>
        <tr>
            <td>Q&emsp;&emsp;Q</td>
            <td><input type="text" name="qq"></td>
        </tr>
        <tr>
            <td>邮&emsp;&emsp;箱</td>
            <td><input type="email" name="email" id="email" required>*</td>
        </tr>
        <tr>
            <td>联系方式</td>
            <td><input type="text" name="phone" id="phone" maxlength="11"></td>
        </tr>
        <tr>
            <td>个人说明</td>
            <td>
                <textarea rows="8"  cols="15" name="remark"></textarea>
            </td>
        </tr>
        <tr>
            <td colspan="2" align="center">
                <input type="button" value="提交" onclick="check()">&emsp;&emsp;&emsp;
                <input type="reset" value="重置">&emsp;&emsp;&emsp;
                <input type="button" value="返回" onclick="history.go(-1);">
            </td>
        </tr>
    </table>
</form>
</body>
</html>
