<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>

    <title>房产算法开关</title>

    <link rel="stylesheet" type="text/css" href="<%=path%>/vendors/datatable/datatables-bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="<%=path%>/vendors/select2/select2.min.css"/>
    <script src="<%=path%>/vendors/requireJS/require.js"></script>
    <script type="text/javascript" src="<%=path%>/vendors/requireJS/require-config.js"></script>
    <script type="text/javascript">
        require(["common","mySelect"],function (main) {
            $(function(){
                main.loadDeps(["AlgorithmSwitch.json"],function (data) {
                    $("#paySwitchSelect").mySelect2({data:data["AlgorithmSwitch.json"]});
                    $("#chargeSwitchSelect").mySelect2({data:data["AlgorithmSwitch.json"]})
                    var config = {
                        popArea:['400px','300px'],
                        deleteable:false,
                        domain:{
                            name:'algorithmSwitch',
                            props:[
                                {name:'id',type:'string',showable:true},
                                {name:'name',type:'string',showable:true},
                                {name:'chargeSwitch',type:'string',showable:true,render:function (row, type, full, meta) {
                                    var dic = main.findArrayValue(full.chargeSwitch,data["AlgorithmSwitch.json"])
                                    return dic&&dic.text?dic.text:"";
                                }},
                                {name:'paySwitch',type:'string',showable:true,render:function (row, type, full, meta) {
                                    var dic = main.findArrayValue(full.paySwitch,data["AlgorithmSwitch.json"])
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

<div class="container">
</div>

<!-- 数据表格 -->
<div class="container">
    <table id="datatable" class="table table-striped table-bordered" style="width:100%">
        <thead>
        <tr>
            <th>房产局编码</th>
            <th>房产局名称</th>
            <th>收费开关</th>
            <th>支出开关</th>
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
                    <label class="control-label col-md-3 col-sm-3 col-xs-3">房产局名称</label>
                    <div class="col-md-9 col-sm-9 col-xs-9">
                        <input type="text" class="form-control" name="name" placeholder="请输入房产局名称名称......">
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-3">收费开关</label>
                    <div class="col-md-9 col-sm-9 col-xs-9">
                        <select id="chargeSwitchSelect" name="chargeSwitch" class="form-control" style="width:100%;"></select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-3">支出开关</label>
                    <div class="col-md-9 col-sm-9 col-xs-9">
                        <select id="paySwitchSelect" name="paySwitch" class="form-control" style="width:100%;"></select>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>