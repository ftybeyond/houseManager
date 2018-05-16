<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>

    <title>单位管理</title>

    <link rel="stylesheet" type="text/css" href="<%=path%>/vendors/datatable/datatables-bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="<%=path%>/vendors/select2/select2.min.css"/>
    <script src="<%=path%>/vendors/requireJS/require.js"></script>
    <script type="text/javascript" src="<%=path%>/vendors/requireJS/require-config.js"></script>
    <script type="text/javascript">
        require(["common","mySelect"],function (main) {
            $(function(){
                //var select = $("#natureSelect").mySelect('CompanyNature.json');
                main.loadDeps(["CompanyNature.json"],function (data) {
                    $("#natureSelect").mySelect2({data:data["CompanyNature.json"]})
                    var config = {
                        popArea:['400px','350px'],
                        domain:{
                            name:'company',
                            props:[
                                {name:'id',type:'string',showable:true},
                                {name:'name',type:'string',showable:true},
                                {name:'legalPersonName',type:'string',showable:true},
                                {name:'legalPersonLicense',type:'string',showable:true},
                                {name:'accountNum',type:'string',showable:true},
                                {name:'nature',type:'string',showable:true,render:function (row, type, full, meta) {
                                    var dic = main.findArrayValue(full.nature,data["CompanyNature.json"])
                                    return dic&&dic.text?dic.text:"";
                                }}
                            ]
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
<div class="container" style="padding-left: 0px;" >
    <form id="searchForm" class="form-horizontal" role="form">
        <div class="col-xs-5 form-group">
            <label class="control-label col-xs-3">单位编码</label>
            <div class="col-xs-9">
                <input type="text" class="form-control" placeholder="要查询的单位编码" name="id"/>
            </div>
        </div>
        <div class="col-xs-5 form-group">
            <label class="control-label col-xs-3">单位名称</label>
            <div class="col-xs-9">
                <input type="text" class="form-control" placeholder="要查询的单位名称" name="name"/>
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
            <th>单位编码</th>
            <th>单位名称</th>
            <th>单位法人</th>
            <th>法人证件</th>
            <th>银行账户</th>
            <th>单位性质</th>
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
                    <label class="control-label col-md-3 col-sm-3 col-xs-3">单位名称</label>
                    <div class="col-md-9 col-sm-9 col-xs-9">
                        <input type="text" class="form-control" name="name" placeholder="请输入单位名称......">
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-3">法人姓名</label>
                    <div class="col-md-9 col-sm-9 col-xs-9">
                        <input type="text" class="form-control" name="legalPersonName" placeholder="请输入法人姓名......">
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-3">身份证号</label>
                    <div class="col-md-9 col-sm-9 col-xs-9">
                        <input type="text" class="form-control" name="legalPersonLicense" placeholder="请输入法人身份证号......">
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-3">银行账户</label>
                    <div class="col-md-9 col-sm-9 col-xs-9">
                        <input type="text" class="form-control" name="accountNum" placeholder="请输入银行账户......">
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-3">单位性质</label>
                    <div class="col-md-9 col-sm-9 col-xs-9">
                        <select id="natureSelect" name="nature" class="form-control" style="width:100%;"></select>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>