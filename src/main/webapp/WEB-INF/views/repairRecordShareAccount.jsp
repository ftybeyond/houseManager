<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>

    <title>维修资金分摊登账</title>

    <link rel="stylesheet" type="text/css" href="<%=path%>/vendors/datatable/datatables-bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="<%=path%>/vendors/select2/select2.min.css"/>
    <!--[if lt IE 8 ]><script src="<%=path%>/vendors/json2.min.js"></script><![endif]-->
    <script src="<%=path%>/vendors/requireJS/require.js"></script>
    <script type="text/javascript" src="<%=path%>/vendors/requireJS/require-config.js"></script>
    <script type="text/javascript">
        require(["common", "mySelect","my97date"], function (main) {
            $(function () {
                main.loadDeps(["residential_area", "ShareType.json", "RepairState.json","TradeType.json"], function (data) {
                    $("select[name='residentialArea']").mySelect2({data: data["residential_area"]})
                    $("select[name='state']").mySelect2({data: data["RepairState.json"]})
                    $("#shareTypeSelect").mySelect2({data: data["ShareType.json"]})
                    var config = {
                        popArea: ['720px', '550px'],
                        scrollX:true,
                        domain: {
                            name: 'repairRecord',
                            props: [
                                {name: 'id', type: 'string', showable: false},
                                {
                                    className: 'details-control',
                                    orderable:false,
                                    data:null,
                                    showable: true,
                                    defaultContent: ''
                                },
                                {
                                    name: 'residentialArea',
                                    type: 'string',
                                    showable: true,
                                    render: function (row, type, full, meta) {
                                        var dic = main.findArrayValue(row, data["residential_area"])
                                        return dic && dic.text ? dic.text : "";
                                    }
                                },
                                {name: 'owners', type: 'string', showable: true},
                                {name: 'ownersTel', type: 'string', showable: true},
                                {name: 'workTime', type: 'string', showable: true},
                                {name: 'moneySum', type: 'string', showable: true},
                                {name: 'shareType', type: 'string', showable: true,render: function(row){
                                    var dic = main.findArrayValue(row, data["ShareType.json"])
                                    return dic && dic.text ? dic.text : "";
                                }},
                                {name: 'stamp', type: 'string', showable: true}
                            ]
                        },
                        editable:false,
                        deleteable:false,
                        customBtns:[
                            {label:'登账', callback:function(rowId){
                                layer.confirm("确认登帐此次分摊结果？登帐后将会扣除相应账户余额！",{icon:3,yes:function () {
                                    var loadingMask = layer.msg('入账中......', {shade: [0.8, '#393D49'], time: 0, icon: 16});
                                    $.post("/rest/share/doShareAccount.action",{record:rowId.toString()},null,"json").done(function(rsp){
                                        console.log(rsp)
                                        layer.close(loadingMask);
                                        layer.alert(rsp.description);
                                        table.ajax.reload();
                                    }).fail(function (xhr) {
                                        layer.close(loadingMask);
                                        layer.alert(xhr.statusText);
                                    })
                                }})
                            }},
                            {label:'明细', callback:function(rowId){
                                var detailTable = $("#accountDetail").table({
                                    ajax:{
                                        url:'/rest/share/shareAccountDetail.action',
                                        data:function (a) {
                                            delete  a.columns;
                                            delete  a.order;
                                            delete a.search;
                                            a.remark=rowId;
                                        }
                                    },
                                    columns:[
                                        {data:"buildingName"},
                                        {data:"unitName"},
                                        {data:"houseFloor"},
                                        {data:"houseName"},
                                        {data:"houseArea"},
                                        {data:"tradeTime"},
                                        {data:"handler"},
                                        {data:"tradeMoney"}
                                    ],
                                    "footerCallback": function ( row, data, start, end, display ) {
                                        var api = this.api();
                                        $.post("/rest/share/shareSumAccountDetail.action",{remark:rowId.toString()},null,"json").done(function (resp) {
                                            $( api.column( 7 ).footer() ).html(
                                                resp.data
                                            );
                                        })

                                    },
                                });
                                layer.open({
                                    title:'账户分摊明细',
                                    area: ['700px', '550px'],
                                    offset: '20px',
                                    type: 1,
                                    content: $("#popWin"),
                                    cancel:function(index, layero){
                                        detailTable.destroy();
                                    }
                                    });
                            }}
                        ]
                    }
                    var table = main.init(config);
                    table.on('click', 'td.details-control', function () {
                        var tr = $(this).closest('tr');
                        var row = table.row( tr );
                        var rowData = row.data();

                        if ( row.child.isShown() ) {
                            // This row is already open - close it
                            row.child.hide();
                            tr.removeClass('shown');
                        }
                        else {
                            if(tr.hasClass("dataLoaded")){
                                row.child.show();
                            }else{
                                $.ajax({
                                    url:"/rest/repairItem/selectByRecord.action",
                                    type:"POST",
                                    data:{record:rowData.id},
                                    dataType:"json"
                                }).done(function(data){
                                    var repairItems = "";
                                    $.each(data.data,function(index,item){
                                        repairItems += ('<tr><td>事项说明：</td><td>'+item.remark+'&nbsp;&nbsp;&nbsp;&nbsp;费用：'+item.price+'</td></tr>');
                                    })
                                    var showInfo =  '<table class="table inner">'+
                                        '<tr>'+
                                        '<td width="100px">项目地址:</td>'+
                                        '<td>'+(rowData.address?rowData.address:'')+'</td>'+
                                        '</tr>'+
                                        '<tr>'+
                                        '<td>申请单位1:</td>'+
                                        '<td>'+(rowData.org1?rowData.org1:'')+'</td>'+
                                        '</tr>'+
                                        '<tr>'+
                                        '<td>申请单位1电话:</td>'+
                                        '<td>'+(rowData.tel1?rowData.tel1:'')+'</td>'+
                                        '</tr>'+
                                        '<tr>'+
                                        '<td>申请单位2:</td>'+
                                        '<td>'+(rowData.org2?rowData.org2:'')+'</td>'+
                                        '</tr>'+
                                        '<tr>'+
                                        '<td>申请单位2电话:</td>'+
                                        '<td>'+(rowData.tel2?rowData.tel2:'')+'</td>'+
                                        '</tr>'+
                                        '<tr>'+
                                        '<td>施工单位:</td>'+
                                        '<td>'+(rowData.worker?rowData.worker:'')+'</td>'+
                                        '</tr>'+
                                        '<tr>'+
                                        '<td>施工单位电话:</td>'+
                                        '<td>'+(rowData.workerTel?rowData.workerTel:'')+'</td>'+
                                        '</tr>'+
                                        '<tr>'+
                                        '<td colspan="2">维修项目(合计费用:'+rowData.moneySum+')</td>'+
                                        '<td></td>'+
                                        '</tr>'+
                                        repairItems+
                                        '</table>';
                                    row.child(showInfo).show();
                                    tr.addClass('shown');
                                    tr.addClass("dataLoaded")
                                }).fail(function(xhr){
                                    layer.alert(xhr.statusText, {closeBtn: 0});
                                })
                            }
                        }
                    } );

                })
            })
        })
    </script>
</head>
<body>
<!--查询表单-->
<div class="container">
    <form id="searchForm" class="form-horizontal" role="form">
        <input type="hidden" name="state" value="1"/>
        <div class="col-xs-5 form-group">
            <label class="control-label col-xs-3">项目名称</label>
            <div class="col-xs-9">
                <select name="residentialArea" class="form-control" style="width:100%;"></select>
            </div>
        </div>
        <div class="col-xs-2 form-group">
            <div class="col-xs-6">
                <button id="searchBtn" class="btn btn-primary" type="button">查询</button>
            </div>
        </div>
    </form>
</div>
<div class="ln_solid"></div>
<!-- 数据表格 -->
<div class="container">
    <table id="datatable" class="table table-striped table-bordered" style="width:1200px;">
        <thead>
        <tr>
            <th></th>
            <th>项目名称</th>
            <th>业主委员会</th>
            <th>业主委员会电话</th>
            <th>申请施工时间</th>
            <th>合计金额</th>
            <th>分摊方式</th>
            <th>登记日期</th>
            <th>操作</th>
        </tr>
        </thead>
    </table>
</div>

<div id="popWin" style="display: none;">
    <div class="x_panel">
        <div class="x_content" style="padding-left: 15px;">
            <table id="accountDetail" class="table table-striped table-bordered" style="width:100%">
                <thead>
                <tr>
                    <th>楼栋</th>
                    <th>单元</th>
                    <th>层号</th>
                    <th>房号</th>
                    <th>面积</th>
                    <th>交易时间</th>
                    <th>处理人</th>
                    <th>交易金额(元)</th>
                </tr>
                </thead>
                <tfoot>
                <tr>
                    <th colspan="7" style="text-align:right" rowspan="1">合计金额:</th>
                    <th></th>
                </tr>
                </tfoot>
            </table>
        </div>
    </div>
</div>
</body>
</html>