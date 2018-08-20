<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>

    <title>批量导入</title>
    <link rel="stylesheet" type="text/css" href="<%=path%>/vendors/datatable/datatables-bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="<%=path%>/vendors/select2/select2.min.css"/>
    <style type="text/css">
        body{
            padding-left: 20px;
        }
        form{
            width:250px;
        }
    </style>
    <script src="<%=path%>/vendors/requireJS/require.js"></script>
    <script type="text/javascript" src="<%=path%>/vendors/requireJS/require-config.js"></script>
    <script type="text/javascript">
        require(["layer","jquery-ajaxForm"], function () {
            layer.config({
                path: '/vendors/layer/',
                offset: "100px"
            });
            $(function () {
                $("#importBtn").on("click",function () {
                    var loadingMask = layer.msg('拼命加载中......', {shade: [0.8, '#393D49'], time: 0, icon: 16});
                    $("#importForm").ajaxForm({
                        dataType : 'json',
                        success : function(data) {
                            layer.alert(data.description)
                        },
                        error: function(xhr) {
                            layer.alert("服务器内部错误!");
                        }
                    })
                })
            })
        })
    </script>
</head>
<body>
    <h3>导入时请按照模版录入数据！！！</h3>
    <br>
    <a href="<%=path%>/download/template.xls">模版下载</a>
    <h3>${message}</h3>
    <form id="importForm" action="/rest/upload/import.action" class="form-horizontal" method="post" enctype ="multipart/form-data">
        <div class="form-group">
            <input type="file" class="form-control" name="file"/>
        </div>
            <button id="importBtn" class="btn btn-primary">导入</button>

    </form>
</body>
</html>
