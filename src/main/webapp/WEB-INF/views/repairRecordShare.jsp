`<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>

    <title>维修记录</title>

    <link rel="stylesheet" type="text/css" href="<%=path%>/vendors/datatable/datatables-bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="<%=path%>/vendors/select2/select2.min.css"/>
    <style>

    </style>
    <!--[if lt IE 8 ]><script src="<%=path%>/vendors/json2.min.js"></script><![endif]-->
    <script src="<%=path%>/vendors/requireJS/require.js"></script>
    <script type="text/javascript" src="<%=path%>/vendors/requireJS/require-config.js"></script>
    <script type="text/javascript">
        var table;
        require(["common", "mySelect"], function (main) {
            $(function () {
                main.loadDeps(["residential_area", "ShareType.json"], function (data) {
                    $("select[name='residentialArea']").mySelect2({data: data["residential_area"]})
                    var config = {
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
                        editable:function(data, type, full, meta){
                            return false;
                        },
                        deleteable:function(data, type, full, meta){
                            return false;
                        },
                        customBtns:[
                            {label:'分摊', callback:function(rowId){
                                layer.open({
                                    title:'选择分摊单位',
                                    area: ['700px', '550px'],
                                    offset:'t',
                                    type: 2,
                                    content: '/page/shareDetail.action?record='+rowId //这里content是一个URL，如果你不想让iframe出现滚动条，你还可以content: ['http://sentsin.com', 'no']
                                });
                            }}
                        ]
                    }
                    table = main.init(config);
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
                                        '<td width="80px">项目地址:</td>'+
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
        <div class="col-xs-5 form-group">
            <label class="control-label col-xs-3">项目名称</label>
            <div class="col-xs-9">
                <select name="residentialArea" class="form-control" style="width:100%;"></select>
            </div>
        </div>
        <input type="hidden" name="state" value="0"/>
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
    <table id="datatable" class="table table-striped table-bordered" style="width:100%;">
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

</body>
</html>