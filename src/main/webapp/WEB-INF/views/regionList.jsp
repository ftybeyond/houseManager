<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>

    <title>区域管理</title>

    <link rel="stylesheet" type="text/css" href="<%=path%>/vendors/datatable/datatables-bootstrap.min.css"/>

    <script type="text/javascript" src="/vendors/datatable/datatables-all.min.js"></script>
    <script type="text/javascript" src="/vendors/layer/layer.js"></script>
    <script type="text/javascript" src="/vendors/jquery/jquery.serializejson.min.js"></script>
    <script type="text/javascript" src="/script/region.js"></script>
</head>
<body>
<!--查询表单-->
<div class="container" style="padding-left: 0px;" >
    <form id="regionSearchForm" class="form-horizontal" role="form">
        <div class="col-xs-5 form-group">
            <label class="control-label col-xs-3">区域编码</label>
            <div class="col-xs-9">
                <input type="text" class="form-control" placeholder="要查询的区域编码" name="id"/>
            </div>
        </div>
        <div class="col-xs-5 form-group">
            <label class="control-label col-xs-3">区域名称</label>
            <div class="col-xs-9">
                <input type="text" class="form-control" placeholder="要查询的区域名称" name="name"/>
            </div>
        </div>
        <div class="col-xs-2 form-group">
            <div class="col-xs-6">
                <button id="doSearch" class="btn btn-primary" type="button">查询</button>
            </div>
            <div class="col-xs-6">
                <button id="doAdd" class="btn btn-primary" type="button">新增</button>
            </div>
        </div>
    </form>
</div>
<div class="ln_solid"></div>
<!-- 数据表格 -->
<div class="container">
    <table id="regionTable" class="table table-striped table-bordered" style="width:100%">
        <thead>
        <tr>
            <th>区域编码</th>
            <th>区域名称</th>
            <th>操作</th>
        </tr>
        </thead>
    </table>
</div>

<div id="popWin" style="display: none">
    <div class="x_panel">
        <div class="x_content">
            <form id="regionForm" class="form-horizontal form-label-left input_mask">
                <div class="form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-3">区域名称</label>
                    <div class="col-md-9 col-sm-9 col-xs-9">
                        <input type="text" class="form-control" name="name" placeholder="请输入区域名称......">
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>