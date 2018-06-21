<%--
  Created by IntelliJ IDEA.
  User: 梅峰鑫
  Date: 2018/6/18
  Time: 17:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<script type="text/javascript">
    function countdown ()
    {
        var end = new Date (2018, 10, 29, 3);
        var now = new Date ();

        var m = Math.round ((end - now) / 1000);
        var day = parseInt (m / 24 / 3600);
        var hours = parseInt ((m % (3600 * 24)) / 3600);
        var minutes = parseInt ((m % 3600) / 60);
        var seconds = m % 60;

        if (m < 0)
        {
            document.getElementById ("clock").innerHTML = '0';
            return;
        }
        document.getElementById ("clock").innerHTML = "离开始还剩" + day + "天" + hours + "小时" + minutes + "分钟" + seconds
            + "秒";
        setTimeout ('countdown()', 1000);
    }
    window.onload = function ()
    {
        countdown ();
    }
</script>
</head>
<body>
<span id="clock"></span>
</body>
</html>
