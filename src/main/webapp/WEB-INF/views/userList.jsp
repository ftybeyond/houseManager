<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>

    <title>用户管理</title>

    <link rel="stylesheet" type="text/css" href="<%=path%>/vendors/datatable/datatables-bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="<%=path%>/vendors/select2/select2.min.css"/>
    <script src="<%=path%>/vendors/requireJS/require.js"></script>
    <script type="text/javascript" src="<%=path%>/vendors/requireJS/require-config.js"></script>
    <script type="text/javascript">
        require(["common","mySelect"],function (main) {
            $(function(){
                main.loadDeps(["role","company"],function (data) {
                    $("#companySelect").mySelect2({data:data["company"]});
                    $("#roleSelect").mySelect2({data:data["role"]});
                    var config = {
                        popArea:['400px','350px'],
                        domain:{
                            name:'user',
                            props:[
                                {name:'id',type:'string',showable:false},
                                {name:'loginName',type:'string',showable:true},
                                {name:'realName',type:'string',showable:true},
                                {name:'role',type:'string',showable:true,render:function (row, type, full, meta) {
                                    var dic = main.findArrayValue(row,data["role"])
                                    return dic&&dic.text?dic.text:"";
                                }},
                                {name:'org',type:'string',showable:true,render:function (row, type, full, meta) {
                                    var dic = main.findArrayValue(row,data["company"])
                                    return dic&&dic.text?dic.text:"";
                                }}
                            ]
                        },
                        validateRules:{
                            loginName:{
                                required:true,
                                maxlength:20
                            },
                            role:'required',
                            org:'required'
                        }
                    }
                    var table = main.init(config);
                })
            })
        })
    </script>
</head>
<body>
<!--查询表单-->
<div class="container" >
    <form id="searchForm" class="form-horizontal" role="form">
        <div class="col-xs-5 form-group">
            <label class="control-label col-xs-3">登录名</label>
            <div class="col-xs-9">
                <input type="text" class="form-control" placeholder="要查询的登录名" name="loginName"/>
            </div>
        </div>
        <div class="col-xs-5 form-group">
            <label class="control-label col-xs-3">真实姓名</label>
            <div class="col-xs-9">
                <input type="text" class="form-control" placeholder="要查询的真实姓名" name="realName"/>
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
            <th>登录名</th>
            <th>真实姓名</th>
            <th>用户角色</th>
            <th>所属单位</th>
            <th>操作</th>
        </tr>
        </thead>
    </table>
</div>

<div id="popWin" style="display: none;">
    <div class="x_panel">
        <div class="x_content">
            <form id="infoForm" class="form-horizontal form-label-left input_mask">
                <div class="form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-3">登录名</label>
                    <div class="col-md-9 col-sm-9 col-xs-9">
                        <input type="text" class="form-control" name="loginName" placeholder="请输入登录名......">
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-3">真实姓名</label>
                    <div class="col-md-9 col-sm-9 col-xs-9">
                        <input type="text" class="form-control" name="realName" placeholder="请输入真实姓名......">
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-3">所属单位</label>
                    <div class="col-md-9 col-sm-9 col-xs-9">
                        <select id="companySelect" name="org" class="form-control" style="width:100%;"></select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-3">用户角色</label>
                    <div class="col-md-9 col-sm-9 col-xs-9">
                        <select id="roleSelect" name="role" class="form-control" style="width:100%;"></select>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>