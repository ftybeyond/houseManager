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
                                {name:'houseOwner',type:'string',showable:true},
                                {name:'houseTel',type:'string',showable:true},
                                {name:'accountBalance',type:'string',showable:true},
                                {name:'actualSum',type:'string',showable:true},
                                {name:'createTime',type:'string',showable:true},
                                {name:'invoiceNum',type:'string',showable:true}
                            ]
                        },
                        editable:false,
                        deleteable:false,
                        customBtns:[
                            {label:'修改',callback:function (index,item) {
                                layer.confirm('<p>新发票号：</p><p><input style="width: 100%"/></p>',{title:"修改票据",area:["400px"],btn:["确认","取消"]},function (index,layObj) {
                                    var invoiceNum = layObj.find("input").val();
                                    if(!invoiceNum){
                                        layer.alert("请填入发票号!");
                                        return
                                    }
                                    item.invoiceNum = invoiceNum;
                                    var loadMask = layer.msg('拼命加载中......', {shade: [0.8, '#393D49'], time: 0, icon: 16});
                                    $.post("/rest/chargeBill/updateInvoiceNum.action",item,null,"json").done(function(data){
                                        layer.alert(data.description);
                                        table.ajax.reload();
                                    }).fail(function(xhr){
                                        layer.alert(xhr.statusText)
                                    });
                                })
                            }},
                            {label:'作废',callback:function (index,item) {
                                var loadMask = layer.msg('拼命加载中......', {shade: [0.8, '#393D49'], time: 0, icon: 16});
                                $.post("/rest/chargeBill/abolishInvoiceNum.action",item,null,"json").done(function(data){
                                    layer.alert(data.description);
                                    table.ajax.reload();
                                }).fail(function(xhr){
                                    layer.alert(xhr.statusText)
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
                                    url:"/rest/invoiceLog/selectByBill.action",
                                    type:"POST",
                                    data:{bill:rowData.id},
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
                                        '<th>开票金额(元)</th>'+
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
                    <input type="text" class="form-control" placeholder="要查询的缴费业主姓名" name="ownerName"/>
                </div>
            </div>
            <div class="col-xs-4 search-form-group">
                <label class="control-label col-xs-3">业主电话</label>
                <div class="col-xs-9">
                    <input type="text" class="form-control" placeholder="要查询的缴费业主电话" name="ownerTel"/>
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
            <th>开票明细</th>
            <th>所属小区</th>
            <th>业主姓名</th>
            <th>业主电话</th>
            <th>账户余额</th>
            <th>收缴金额(元)</th>
            <th>收缴日期</th>
            <th>票据号</th>
            <th>操作</th>
        </tr>
        </thead>
    </table>
</div>

</body>
</html>