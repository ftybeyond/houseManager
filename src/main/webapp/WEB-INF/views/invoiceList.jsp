<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>

    <title>票据管理</title>

    <link rel="stylesheet" type="text/css" href="<%=path%>/vendors/datatable/datatables-bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="<%=path%>/vendors/select2/select2.min.css"/>
    <script src="<%=path%>/vendors/requireJS/require.js"></script>
    <script type="text/javascript" src="<%=path%>/vendors/requireJS/require-config.js"></script>
    <script type="text/javascript">
        require(["common","mySelect"],function (main) {
            $(function(){
                main.loadDeps(["residential_area","InvoiceEvent.json"],function (data) {
                    $("#queryResidentialAreaSelect").mySelect2({data:data["residential_area"]})
                    var config = {
                        domain:{
                            name:'invoiceLog',
                            props:[
                                {name:'id',type:'string',showable:false},
                                {
                                    className: 'details-control',
                                    showable: true,
                                    defaultContent: ''
                                },
                                {name:'residentialAreaName',type:'string',showable:true},
                                {name:'ownerName',type:'string',showable:true},
                                {name:'ownerTel',type:'string',showable:true},
                                {name:'accountBalance',type:'string',showable:true},
                                {name:'money',type:'string',showable:true},
                                {name:'stamp',type:'string',showable:true},
                                {name:'invoiceNum',type:'string',showable:true}
                            ]
                        },
                        editable:false,
                        deleteable:false,
                        customBtns:[
                            {label:'修改',callback:function (index,item) {

                            }},
                            {label:'作废',callback:function (index,item) {

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
                                    url:"/rest/invoiceLog/selectByBill.action",
                                    type:"POST",
                                    data:{bill:rowData.bill},
                                    dataType:"json"
                                }).done(function(rsp){
                                    var rows ='';
                                    $.each(rsp.data,function(index,item){
                                        var eventType = main.findArrayValue(item.eventType,data["InvoiceEvent.json"]);
                                        rows += '<tr>' +
                                            '<td>'+(eventType?eventType.text:'')+'</td>' +
                                            '<td>'+item.invoiceNum+'</td>' +
                                            '<td>'+item.money+'</td>' +
                                            '<td>'+item.stamp+'</td>' +
                                            '<td>'+item.payor+'</td>' +
                                            '<td>'+item.handler+'</td>' +
                                            '</tr>'
                                    });
                                    var showInfo =  '<table class="table inner">'+
                                        '<tr>'+
                                        '<th>操作类型</th>'+
                                        '<th>发票号码</th>'+
                                        '<th>开票金额</th>'+
                                        '<th>开票时间</th>'+
                                        '<th>业主姓名</th>'+
                                        '<th>操作员</th>'+
                                        '</tr>'+
                                        rows+
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
<div class="container" style="padding-left: 0px;" >
    <form id="searchForm" class="form-horizontal" role="form">
        <div class="row clearfix">
            <div class="col-xs-4 search-form-group">
                <label class="control-label col-xs-3">票据号</label>
                <div class="col-xs-9">
                    <input type="text" class="form-control" placeholder="要查询的票据号" name="invoiceNum"/>
                </div>
            </div>
            <div class="col-xs-4 search-form-group">
                <label class="control-label col-xs-3">业主姓名</label>
                <div class="col-md-9 col-sm-9 col-xs-9">
                    <input type="text" class="form-control" placeholder="要查询的业主姓名" name="ownerName"/>
                </div>
            </div>
            <div class="col-xs-4 search-form-group">
                <label class="control-label col-xs-3">业主电话</label>
                <div class="col-xs-9">
                    <input type="text" class="form-control" placeholder="要查询的票据号" name="ownerTel"/>
                </div>
            </div>
        </div>
        <div class="row clearfix">
            <div class="col-xs-4 search-form-group">
                <label class="control-label col-md-3 col-sm-3 col-xs-3">所属小区</label>
                <div class="col-md-9 col-sm-9 col-xs-9">
                    <select id="queryResidentialAreaSelect" name="residentialArea" class="form-control"
                            style="width:100%;"></select>
                </div>
            </div>
            <div class="col-xs-8 search-form-group">
                <div class="col-xs-12">
                    <button id="searchBtn" class="btn btn-primary" type="button" style="float: right">查询</button>
                </div>
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
            <th>明细</th>
            <th>所属小区</th>
            <th>业主姓名</th>
            <th>业主电话</th>
            <th>账户余额</th>
            <th>票据金额</th>
            <th>收款日期</th>
            <th>票据号</th>
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