<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2018/5/9
  Time: 15:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="/vendors/jquery/1.x/jquery.min.js"></script>
    <script type="text/javascript" src="/vendors/select2/select2.js"></script>
    <link rel="stylesheet" type="text/css" href="/vendors/select2/select2.min.css"/>

    <%--<script type="text/javascript" src="/vendors/select2-3.x/select2.min.js"></script>--%>
    <%--<link rel="stylesheet" type="text/css" href="/vendors/select2-3.x/select2.css"/>--%>
    <%--<link rel="stylesheet" type="text/css" href="/vendors/select2-3.x/select2-bootstrap.css"/>--%>
    <script src="/vendors/jquery/jquery.serializejson.js"></script>
    <script>
        $(function () {
            var select = $("#aaa").select2({
                placeholder: '--请选择--',
                name:'aaa',
                data:[
                    {id: "1", text: "房产局"},
                    {id: "2", text: "建设单位"},
                    {id: "3", text: "售房单位"}
                ]
            })

//            select.select2("val", "all")
        })
        function test() {
            console.log($("#infoForm").serializeJSON());
        }
    </script>
</head>
<body>
<form id="infoForm">
    <input name="age"/>
    <br/>
    <select id="aaa" name="ddd" style="width: 200px;"></select>
    <button onclick="test();return false;">test</button>
</form>
</body>
</html>
