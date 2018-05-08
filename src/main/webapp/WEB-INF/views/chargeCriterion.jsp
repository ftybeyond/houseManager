<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>

    <title>缴费规则管理</title>

    <link rel="stylesheet" type="text/css" href="<%=path%>/vendors/datatable/datatables-bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="<%=path%>/vendors/select2/select2.min.css"/>
    <script src="<%=path%>/vendors/requireJS/require.js"></script>
    <script type="text/javascript" src="<%=path%>/vendors/requireJS/require-config.js"></script>
    <script type="text/javascript">
        require(["common","mySelect","my97date"],function (main) {
            $(function(){
                main.loadDeps(["ChargeType.json","HouseType.json","ElevatorSign.json"],function (data) {
                    var s = $("select[name='chargeType']").mySelect2({data:data["ChargeType.json"]});
                    $("select[name='houseType']").mySelect2({data:data["HouseType.json"]})
                    $("select[name='elevatorSign']").mySelect2({data:data["ElevatorSign.json"]})
                    var config = {
                        popArea:['400px','500px'],
                        domain:{
                            name:'chargeCriterion',
                            props:[
                                {name:'id',type:'string',false:true},
                                {name:'term',type:'date',showable:true},
                                {name:'houseType',type:'string',showable:true,render:function (row) {
                                    var dic = main.findArrayValue(row,data["HouseType.json"])
                                    return dic&&dic.text?dic.text:"";
                                }},
                                {name:'chargeType',type:'string',showable:true,render:function (row) {
                                    var dic = main.findArrayValue(row,data["ChargeType.json"])
                                    return dic&&dic.text?dic.text:"";
                                }},
                                {name:'elevatorSign',type:'string',showable:true,render:function (row) {
                                    var dic = main.findArrayValue(row,data["ElevatorSign.json"])
                                    return dic&&dic.text?dic.text:"";
                                }},
                                {name: 'priceRatio', type: 'string', showable: true,render:function (row) {
                                    return row.toFixed(2)
                                }},
                                {name: 'priceRatio', type: 'string', showable: true,render:function (row) {
                                    return row.toFixed(2)
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
            <label class="control-label col-xs-3">缴费类型</label>
            <div class="col-xs-9">
                <select  name="chargeType" class="form-control"></select>
            </div>
        </div>
        <div class="col-xs-5 form-group">
            <label class="control-label col-xs-3">住宅类型</label>
            <div class="col-xs-9">
                <select name="houseType" class="form-control" style="width:100%;"></select>
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
            <th>有效日期</th>
            <th>住宅类型</th>
            <th>缴款类型</th>
            <th>电梯标志</th>
            <th>房价系数</th>
            <th>面积系数</th>
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
                    <label class="control-label col-md-3 col-sm-3 col-xs-3">有效日期</label>
                    <div class="col-md-9 col-sm-9 col-xs-9">
                        <input type="text" class="form-control" onClick="WdatePicker()" name="term" placeholder="请选择利率生效日期......">
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-3">住宅类型</label>
                    <div class="col-md-9 col-sm-9 col-xs-9">
                        <select id="houseTypeSelect" name="houseType" class="form-control" style="width:100%;"></select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-3">缴费类型</label>
                    <div class="col-md-9 col-sm-9 col-xs-9">
                        <select id="chargeTypeSelect" name="chargeType" class="form-control" style="width:100%;"></select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-3">电梯标志</label>
                    <div class="col-md-9 col-sm-9 col-xs-9">
                        <select id="elevatorSignSelect" name="elevatorSign" class="form-control" style="width:100%;"></select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-3">房价系数</label>
                    <div class="col-md-9 col-sm-9 col-xs-9">
                        <input type="text" class="form-control"  name="priceRatio" placeholder="按房价计算缴费额的系数......">
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-3">面积系数</label>
                    <div class="col-md-9 col-sm-9 col-xs-9">
                        <input type="text" class="form-control" name="areaRatio" placeholder="按面积计算缴费额的系数......">
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>