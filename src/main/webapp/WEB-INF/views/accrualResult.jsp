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
        .dt-button{
            float: right;
        }
    </style>
    <script src="<%=path%>/vendors/requireJS/require.js"></script>
    <script type="text/javascript" src="<%=path%>/vendors/requireJS/require-config.js"></script>
    <script type="text/javascript">
        var treeObj;
        var table;
        var summaryTable;
        require(["houseTree","dataTables-bs","layer","my97date","datatables-print"],function (tree,table) {
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
                        {data:"startTime"},
                        {data:"endTime"},
                        {data:"balance"},
                        {data:"rate",render:function (row) {
                            return row.toFixed(12);
                        }},
                        {data:"accrual",render:function(row){
                            return row.toFixed(2);
                        }}
                    ],
                    "footerCallback": function ( row, data, start, end, display ) {
                        var api = this.api();
                        var param = $.extend({paths:tree.getPathParam()},{state:0})
                        $.post("/rest/accrual/sumResult.action",param,null,"json").done(function (resp) {
                            $( api.column( 5 ).footer() ).html(
                                resp.data
                            );
                        })

                    },
                    dom: 'Blrtip',
                    buttons: [
                        {
                            extend: 'print',
                            className:'btn btn-primary',
                            text: '打印',
                            footer:true,
                            exportOptions:{
                                format: {
                                    footer: function ( data, columnIdx ) {
                                        if(columnIdx>=3){
                                            return data;
                                        }else{
                                            return '';
                                        }
                                    }
                                }
                            }
                        },
                        {
                            className:'btn btn-primary',
                            text: '小区汇总',
                            action:function(e, dt, node, config){
                                if(summaryTable){
                                    summaryTable.ajax.reload();
                                }else{
                                    summaryTable = $("#summaryTable").table({
                                        info:false,
                                        paging:false,
                                        ajax:{
                                            url:'/rest/accrual/summaryResult.action',
                                            data:function (a) {
                                                delete  a.columns;
                                                delete  a.order;
                                                delete a.search;
                                                a.paths = tree.getPathParam();
                                                a.state=0;
                                            }
                                        },
                                        columns:[
                                            {data:"residentialAreaName"},
                                            {data:"endTime"},
                                            {data:"accrualSum"}
                                        ],
                                        "footerCallback": function ( row, data, start, end, display ) {
                                            var api = this.api(), data;
                                            // Remove the formatting to get integer data for summation
                                            var intVal = function ( i ) {
                                                return typeof i === 'string' ?
                                                    i.replace(/[\$,]/g, '')*1 :
                                                    typeof i === 'number' ?
                                                        i : 0;
                                            };
                                            // Total over all pages
                                            var total = api
                                                .column( 2 )
                                                .data()
                                                .reduce( function (a, b) {
                                                    return intVal(a) + intVal(b);
                                                }, 0 ).toFixed(2);
                                            $( api.column(2).footer() ).html(
                                                total
                                            );
                                        },
                                        dom: 'Blrtip',
                                        buttons: [
                                            {
                                                extend: 'print',
                                                className:'btn btn-dark',
                                                text: '打印',
                                                footer:true,
                                                exportOptions:{
                                                    format: {
                                                        footer: function ( data, columnIdx ) {
                                                            if(columnIdx>=1){
                                                                return data;
                                                            }else{
                                                                return '';
                                                            }
                                                        }
                                                    }
                                                }
                                            },
                                            {
                                                text: '导出',// 显示文字
                                                className:'btn btn-dark',
                                                action:function(e, dt, node, config){
                                                    $("#resultSummary").empty();
                                                    $("#resultSummary").append('<input type="hidden" name="state" value="0"/>');
                                                    $("#resultSummary").append('<input type="hidden" name="paths" value="'+tree.getPathParam()+'"/>')
                                                    $("#resultSummary").submit();
                                                }
                                            }
                                        ]
                                    })
                                };

                                layer.open({
                                    type: 1,
                                    title: "小区计息汇总",
                                    offset: '20px',
                                    content: $('#popWin'),
                                    area: ["800px","500px"],
                                    btn: ['关闭'],
                                    btn1:function(index,layObj){
                                        layer.close(index)
                                    }
                                })
                            }
                        },
                        {
                            className:'btn btn-primary',
                            text: '计息回退',
                            action:function(e, dt, node, config){
                                layer.confirm('确定回退计息记录？', {icon: 3, title: '提示'}, function (index) {
                                    var loadingMask = layer.msg('拼命计算中......', {shade: [0.8, '#393D49'], time: 0, icon: 16});
                                    if(table.data().length>0){
                                        $.post("/rest/accrual/accrualBack.action",{paths:tree.getPathParam(),state:0},null,"json").done(function (data) {
                                            layer.close(loadingMask);
                                            layer.alert(data.description);
                                            table.ajax.reload();
                                        });
                                    }else{
                                        layer.close(loadingMask);
                                        layer.alert("未找到可回退的计息记录!")
                                    }
                                })
                            }
                        },
                        {
                            className:'btn btn-primary',
                            text: '计息登帐',
                            action:function(e, dt, node, config){
                                layer.confirm('确定登帐计息记录？', {icon: 3, title: '提示'}, function (index) {
                                    layer.confirm('<p>请选择登帐日期：</p><p><input onClick="WdatePicker({maxDate:\'%y-%M-%d\'})" style="width: 100%"/></p>',{title:"登帐日期",area:["400px"],btn:["登帐","取消"]},function (index,layObj) {
                                        var toDate = layObj.find("input").val();
                                        if (!toDate) {
                                            layer.alert("请选择登帐时间!");
                                            return
                                        }
                                        var loadingMask = layer.msg('入账中......', {
                                            shade: [0.8, '#393D49'],
                                            time: 0,
                                            icon: 16
                                        });
                                        if(table.data().length>0){
                                            $.post("/rest/accrual/bill.action",{paths:tree.getPathParam(),state:0,accountDate:toDate},null,"json").done(function (data) {
                                                layer.close(loadingMask);
                                                layer.alert(data.description);
                                                table.ajax.reload();
                                            });
                                        }else{
                                            layer.close(loadingMask);
                                            layer.alert("未找到可登帐的计息记录!")
                                        }
                                    })

                                });
                            }
                        }
                        ]
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
            <div class="row" style="margin-left: 20px;">
                <table id="datatable" class="table table-striped table-bordered" style="width:100%">
                    <thead>
                    <tr>
                        <th>产业代码</th>
                        <th>起始时间</th>
                        <th>截至时间</th>
                        <th>计息金额(元)</th>
                        <th>利率(日)</th>
                        <th>利息(单位:元)</th>
                    </tr>
                    </thead>
                    <tfoot>
                    <tr>
                        <th colspan="5" style="text-align:right" rowspan="1">合计:</th>
                        <th></th>
                    </tr>
                    </tfoot>
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
                <th>计息时间</th>
                <th>利息(元)</th>
            </tr>
            </thead>
            <tfoot>
            <tr>
                <th colspan="2" style="text-align:right" rowspan="1">合计:</th>
                <th></th>
            </tr>
            </tfoot>
        </table>
    </div>
</div>

<form id="resultSummary" action="/export/accrualResultSummary.action" method="post" style="display: none">
</form>
</body>
</html>