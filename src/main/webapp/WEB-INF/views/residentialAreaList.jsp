<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>

    <title>小区管理</title>

    <link rel="stylesheet" type="text/css" href="<%=path%>/vendors/datatable/datatables-bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="<%=path%>/vendors/select2/select2.min.css"/>
    <script src="<%=path%>/vendors/requireJS/require.js"></script>
    <script type="text/javascript" src="<%=path%>/vendors/requireJS/require-config.js"></script>
    <script type="text/javascript">
        require(["common","mySelect"],function (main) {
            var config = {
                domain:{
                    name:'residentialarea',
                    props:[
                        {name:'id',type:'string',showable:true},
                        {name:'name',type:'string',showable:true},
                        {name:'company',type:'string',showable:true},
//                        {name:'region',type:'string',showable:true},
                        {name:'street',type:'string',showable:true},
                        {name:'address',type:'string',showable:true},
                        {name:'area_elevator',type:'string',showable:true},
                        {name:'area_noelevator',type:'string',showable:true},
                        {name:'nature',type:'string',showable:true}
                    ]
                }
            }
            $(function(){
                //$("#regionSelect").mySelect('region');
                main.init(config);
            })
        })
    </script>
</head>
<body>
<!--查询表单-->
<div class="container" style="padding-left: 0px;" >
    <form id="searchForm" class="form-horizontal" role="form">
        <div class="col-xs-5 form-group">
            <label class="control-label col-xs-3">小区编码</label>
            <div class="col-xs-9">
                <input type="text" class="form-control" placeholder="要查询的小区编码" name="id"/>
            </div>
        </div>
        <div class="col-xs-5 form-group">
            <label class="control-label col-xs-3">小区名称</label>
            <div class="col-xs-9">
                <input type="text" class="form-control" placeholder="要查询的小区名称" name="name"/>
            </div>
        </div>
        <div class="col-xs-2 form-group">
            <div class="col-xs-6">
                <button id="searchBtn" class="btn btn-primary" type="button">查询</button>
            </div>
            <div class="col-xs-6">
                <button id="addBtn" class="btn btn-primary" type="button">新增</button>
            </div>
        </div>
    </form>
</div>
<div class="ln_solid"></div>
<!-- 数据表格 -->
<div class="container">
    <table id="datatable" class="table table-striped table-bordered" style="width:100%">
        <thead>
        <tr>
            <th>小区编码</th>
            <th>小区名称</th>
            <th>单位名称</th>
            <th>所属区域</th>
            <th>所属街道</th>
            <th>地址</th>
            <th>有电梯面积</th>
            <th>无电梯面积</th>
            <th>房屋性质</th>
            <th>操作</th>
        </tr>
        </thead>
    </table>
</div>

<div id="popWin" style="display: none">
    <div class="x_panel">
        <div class="x_content">
            <form id="infoForm" class="form-horizontal form-label-left input_mask">
                <div class="form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-3">小区名称</label>
                    <div class="col-md-9 col-sm-9 col-xs-9">
                        <input type="text" class="form-control" name="name" placeholder="请输入小区名称......">
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-3">所属区域</label>
                    <div class="col-md-9 col-sm-9 col-xs-9">
                        <select id="regionSelect" name="region" class="form-control" style="width:100%;"></select>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>