<%--
  Created by IntelliJ IDEA.
  User: 梅峰鑫
  Date: 2018/6/6
  Time: 19:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <meta charset="utf-8">
  <!--include ECharts document-->
  <script src="/images/echarts.js"></script>

  <title>
    ECharts de Hello World
  </title>
</head>
<body>
<!--prepare a DOM with size for ECharts-->
<div id="main" style="width: 600px;height: 400px;"></div>
<div id="main2" style="width: 600px;height: 400px;"></div>
<script type="text/javascript">
    //基于准备好的dom，初始化echarts实例
    var myChart=echarts.init(document.getElementById('main'));
    //指定图表的配置项和数据
    var option = {
        title : {
            text: '某站点用户访问来源',
            subtext: '纯属虚构',
            x:'center'
        },
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient : 'vertical',
            x : 'left',
            data:['直接访问','邮件营销','联盟广告','视频广告','搜索引擎']
        },
        toolbox: {
            show : true,
            feature : {
                mark : {show: true},
                dataView : {show: true, readOnly: false},
                magicType : {
                    show: true,
                    type: ['pie', 'funnel'],
                    option: {
                        funnel: {
                            x: '25%',
                            width: '50%',
                            funnelAlign: 'left',
                            max: 1548
                        }
                    }
                },
                restore : {show: true},
                saveAsImage : {show: true}
            }
        },
        calculable : true,
        series : [
            {
                name:'访问来源',
                type:'pie',
                radius : '55%',
                center: ['50%', '60%'],
                data:[
                    {value:335, name:'直接访问'},
                    {value:310, name:'邮件营销'},
                    {value:234, name:'联盟广告'},
                    {value:135, name:'视频广告'},
                    {value:1548, name:'搜索引擎'}
                ]
            }
        ]
    };


    //使用刚指定的配置项和数据显示图表
    myChart.setOption(option);
</script>

</body>
</html>