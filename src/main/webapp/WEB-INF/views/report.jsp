<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>

    <title>统计报表</title>

    <link rel="stylesheet" type="text/css" href="<%=path%>/vendors/datatable/datatables-bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="<%=path%>/vendors/zTree/css/metroStyle/metroStyle.css"/>
    <link rel="stylesheet" type="text/css" href="<%=path%>/vendors/gentelella/css/custom.min.css"/>
    <link rel="stylesheet" type="text/css" href="<%=path%>/vendors/select2/select2.min.css"/>
    <link rel="stylesheet" type="text/css" href="<%=path%>/vendors/layer/theme/default/layer.css"/>

    <style type="text/css">
        body{
            background: #F7F7F7;
            padding: 20px;
        }
        li{
            font-size: 16px;
        }
        .toolbar{
            float:right;
        }
        h5{
            clear:both;
            float:right;
        }
    </style>
    <script src="<%=path%>/vendors/requireJS/require.js"></script>
    <script type="text/javascript" src="<%=path%>/vendors/requireJS/require-config.js"></script>
    <script type="text/javascript">
        var treeObj;
        var table;
        require(["houseTree","common","mySelect","my97date","datatables-print"],function (tree,common) {
            layer.config({
                offset:"100px"
            });
            common.loadDeps(["SummaryType.json","SummaryGroup.json","TradeType.json"],function (data) {
                $(function () {
                    $("#summaryTypeSelect").mySelect2({data:data["SummaryType.json"]});
                    $("#summaryGroupSelect").mySelect2({data:data["SummaryGroup.json"]});
                    $("#summaryTypeSelect").val(0).trigger('change');
                    $("#summaryGroupSelect").val(1).trigger('change');
                    treeObj = tree.genTree("selectTree",{
                        onCheck:function () {
                            table.ajax.reload();
                        }
                    });
                    var summaryMsg = function () {

                        var msg = '<h5>汇总范围：'+(tree.getAllSelectPath()?tree.getAllSelectPath():'全部')+'</h5>';
                        msg += '<h5>查询类型：'+$("#summaryTypeSelect").select2('data')[0].text+'</h5>';
                        msg += '<h5>分组类型：'+$("#summaryGroupSelect").select2('data')[0].text+'</h5>';
                        return msg;
                    }
                    table = $("#datatable").table({
                        ajax:{
                            url:'/rest/accountLog/report.action',
                            data:function (a) {
                                delete  a.columns;
                                delete  a.order;
                                delete a.search;
                                a.paths = tree.getPathParam();
                                var searchFormData = $("#searchForm").serializeJSON();
                                $.extend(a,searchFormData);
                            }
                        },
                        columns:[
                            {data:"houseCode"},
                            {data:"houseOwner"},
                            {data:"residentialAreaName"},
                            {data:"buildingName"},
                            {data:"unitName"},
                            {data:"houseFloor"},
                            {data:"houseName"},
                            {data:"houseArea"},
                            {data:"tradeType",render:function (row) {
                                var dic = common.findArrayValue(row,data["TradeType.json"])
                                return dic&&dic.text?dic.text:"";
                            }},
                            {data:"tradeTime"},
                            {data:"handler"},
                            {data:"tradeMoney"}
                        ],
                        dom: 'Blrtip',
                        buttons: [
                            {
                                extend: 'print',
                                className:'btn btn-dark',
                                text: '打印'
                            }
                        ]
                    })
                    $("#searchBtn").on("click",function () {
                        table.ajax.reload();
                    })
                    var summaryTable
                    $("#summaryBtn").on("click",function () {
                        if(summaryTable){
                            summaryTable.ajax.reload();
                        }else{
                            summaryTable = $("#summaryTable").table({
                                info:false,
                                paging:false,
                                ajax:{
                                    url:'/rest/accountLog/reportSummary.action',
                                    data:function (a) {
                                        delete  a.columns;
                                        delete  a.order;
                                        delete a.search;
                                        a.paths = tree.getPathParam();
                                        var searchFormData = $("#searchForm").serializeJSON();
                                        $.extend(a,searchFormData);
                                    }
                                },
                                columns:[
                                    {data:"residentialAreaName"},
                                    {data:"buildingName"},
                                    {data:"unitName"},
                                    {data:"houseCode"},
                                    {data:"sumResult"}
                                ],
                                dom: 'B<"toolbar">lrtip',
                                buttons: [
                                    {
                                        extend: 'print',
                                        className:'btn btn-dark',
                                        messageTop:function () {
                                            return summaryMsg()
                                        },
                                        messageBottom:function () {
                                            return '<div style="float: right;">打印时间:'+new Date().format("yyyy-MM-dd")+'</div>'
                                        },
                                        text: '打印'
                                    }
                                ]
                            })
                        };
                        summaryTable.columns( [0,1,2,3]).visible( true );
                        var summaryGroup = $("#summaryGroupSelect").val();
                        if(summaryGroup == 1){
                            //小区
                            summaryTable.columns( [1, 2, 3] ).visible( false );
                        }else if(summaryGroup == 2){
                            //楼栋汇总
                            summaryTable.columns( [2 ,3]).visible( false );
                        }else if(summaryGroup == 3){
                            //单元汇总
                            summaryTable.columns( [3]).visible( false );
                        }

                        $("div.toolbar").html(summaryMsg())
                        layer.open({
                            type: 1,
                            title: "统计汇总",
                            offset: '20px',
                            content: $('#popWin'),
                            area: ["800px","500px"],
                            btn: ['关闭'],
                            btn1:function(index,layObj){
                                layer.close(index)
                            }
                        })
                    })
                })
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
                    <h4><i class="fa fa-align-left"></i> 选择范围 </h4>
                    <div class="clearfix"></div>
                </div>
                <div class="x_content">
                    <div id="selectTree" class="ztree"></div>
                </div>
            </div>
        </div>
        <div class="col-md-9 column">
            <div class="row clearfix" style="padding: 10px;">
                <form id="searchForm" class="form-horizontal" role="form">
                    <div class="row clearfix">
                        <div class="col-xs-5 search-form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-3">查询类型</label>
                            <div class="col-md-9 col-sm-9 col-xs-9">
                                <select id="summaryTypeSelect" name="summaryType" class="form-control"
                                        style="width:100%;"></select>
                            </div>
                        </div>
                        <div class="col-xs-5 search-form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-3">分组范围</label>
                            <div class="col-md-9 col-sm-9 col-xs-9">
                                <select id="summaryGroupSelect" name="summaryGroup" class="form-control"
                                        style="width:100%;"></select>
                            </div>
                        </div>
                        <div class="col-xs-2 search-form-group">
                            <div class="col-xs-12">
                                <button id="summaryBtn" class="btn btn-primary" type="button">汇总</button>
                            </div>
                        </div>
                    </div>
                    <div class="row clearfix">
                        <div class="col-xs-5 search-form-group">
                            <label class="control-label col-xs-3">起始日期</label>
                            <div class="col-md-9 col-sm-9 col-xs-9">
                                <input type="text" id="startDate" class="form-control" placeholder="要查询交易起始日期" onClick="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\',{d:-1})}'})" name="fromDate"/>
                            </div>
                        </div>
                        <div class="col-xs-5 search-form-group">
                            <label class="control-label col-xs-3">截至日期</label>
                            <div class="col-xs-9">
                                <input type="text" id="endDate" class="form-control" placeholder="要查询交易截至日期" onClick="WdatePicker({minDate:'#F{$dp.$D(\'startDate\',{d:1})}'})" name="endDate"/>
                            </div>
                        </div>
                        <div class="col-xs-2 search-form-group">
                            <div class="col-xs-12">
                                <button id="searchBtn" class="btn btn-primary" type="button">查询</button>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="row" style="margin-left: 20px;">
                <table id="datatable" class="table table-striped table-bordered" style="width:100%">
                    <thead>
                    <tr>
                        <th>产业代码</th>
                        <th>业主姓名</th>
                        <th>所属小区</th>
                        <th>楼栋</th>
                        <th>单元</th>
                        <th>层号</th>
                        <th>房号</th>
                        <th>面积</th>
                        <th>交易类型</th>
                        <th>交易时间</th>
                        <th>处理人</th>
                        <th>交易金额(元)</th>
                    </tr>
                    </thead>
                </table>
            </div>
        </div>
    </div>
</div>
<div id="popWin" style="display: none;">
    <div class="printDiv" style="width: 95%;margin: auto;padding-top: 10px;">
        <table id="summaryTable" class="table table-striped table-bordered" style="width:100%">
            <thead>
            <tr>
                <th>所属小区</th>
                <th>楼栋</th>
                <th>单元</th>
                <th>产业代码</th>
                <th>合计金额(元)</th>
            </tr>
            </thead>
        </table>
    </div>
</div>
</body>
</html>