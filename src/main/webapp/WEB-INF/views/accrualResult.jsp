<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>

    <title>计息登帐/回退</title>

    <link rel="stylesheet" type="text/css" href="<%=path%>/vendors/datatable/datatables-bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="<%=path%>/vendors/zTree/css/metroStyle/metroStyle.css"/>
    <link rel="stylesheet" type="text/css" href="<%=path%>/vendors/gentelella/css/custom.min.css"/>
    <link rel="stylesheet" type="text/css" href="<%=path%>/vendors/layer/theme/default/layer.css"/>

    <style type="text/css">
        body{
            background: #F7F7F7;
            padding: 20px;
        }
        li{
            font-size: 16px;
        }
    </style>
    <script src="<%=path%>/vendors/requireJS/require.js"></script>
    <script type="text/javascript" src="<%=path%>/vendors/requireJS/require-config.js"></script>
    <script type="text/javascript">
        var treeObj;
        var table;
        require(["houseTree","dataTables-bs","layer","my97date"],function (tree,table) {
            layer.config({
                offset:"100px"
            })
            $(function () {
                treeObj = tree.genTree("selectTree",{
                    onCheck:function () {
                        table.ajax.reload();
                    }
                });
                table = $("#datatable").table({
                    ajax:{
                        url:'/rest/accrual/resultTable.action',
                        data:function (a) {
                            delete  a.columns;
                            delete  a.order;
                            delete a.search;
                            a.paths = tree.getPathParam();
                            a.state = 0;
                        }
                    },
                    columns:[
                        {data:"houseCode"},
                        {data:"accrual",render:function(row){
                            return row.toFixed(2);
                        }},
                        {data:"startTime"},
                        {data:"endTime"},
                        {data:"balance"},
                        {data:"rate",render:function (row) {
                            return row.toFixed(12);
                        }}
                    ]
                })
                $("#accrualBackBtn").on("click",function(){
                    layer.confirm('确定回退计息记录？', {icon: 3, title: '提示'}, function (index) {
                        if(table.data().length>0){
                            $.post("/rest/accrual/accrualBack.action",{paths:tree.getPathParam(),state:0},null,"json").done(function (data) {
                                layer.alert(data.description);
                                table.ajax.reload();
                            });
                        }else{
                            layer.alert("未找到可回退的计息记录!")
                        }
                    })
                });
                $("#accrualBillBtn").on("click",function(){
                    layer.confirm('确定登帐计息记录？', {icon: 3, title: '提示'}, function (index) {
                        if(table.data().length>0){
                            $.post("/rest/accrual/bill.action",{paths:tree.getPathParam(),state:0},null,"json").done(function (data) {
                                layer.alert(data.description);
                                table.ajax.reload();
                            });
                        }else{
                            layer.alert("未找到可登帐的计息记录!")
                        }
                    });
                });
            })
        })
    </script>

</head>
<body>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-3 column" style="min-width: 250px;">
            <div class="x_panel">
                <div class="x_title">
                    <h4><i class="fa fa-align-left"></i> 计息范围 </h4>
                    <div class="clearfix"></div>
                </div>
                <div class="x_content">
                    <div id="selectTree" class="ztree"></div>
                </div>
                <div class="x_content">
                    <button id="selectAllBtn" class="btn-primary btn-xs" style="float: right">全选/反选</button>
                </div>
            </div>
        </div>
        <div class="col-md-9 column">
            <div class="row clearfix" style="padding: 10px;">
                <button id="accrualBillBtn" style="float:right" class="btn btn-primary" type="button">计息登帐</button>
                <button id="accrualBackBtn" style="float:right" class="btn btn-primary" type="button">计息回退</button>
            </div>
            <div class="row" style="margin-left: 20px;">
                <table id="datatable" class="table table-striped table-bordered" style="width:100%">
                    <thead>
                    <tr>
                        <th>产业代码</th>
                        <th>利息(单位:元)</th>
                        <th>起始时间</th>
                        <th>截至时间</th>
                        <th>计息金额(元)</th>
                        <th>利率(日)</th>
                    </tr>
                    </thead>
                </table>
            </div>
        </div>
    </div>
</div>

</body>
</html>