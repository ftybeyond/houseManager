<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>

    <title>收缴查询</title>

    <link rel="stylesheet" type="text/css" href="<%=path%>/vendors/datatable/datatables-bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="<%=path%>/vendors/select2/select2.min.css"/>
    <script src="<%=path%>/vendors/requireJS/require.js"></script>
    <script type="text/javascript" src="<%=path%>/vendors/requireJS/require-config.js"></script>
    <script type="text/javascript">
        require(["common","mySelect","my97date","datatables-print"],function (main) {
            $(function(){
                main.loadDeps(["residential_area","ChargeBillState.json"],function (data) {
                    $("#queryResidentialAreaSelect").mySelect2({data:data["residential_area"]})
                    var config = {
                        domain:{
                            name:'chargeBill',
                            props:[
                                {name:'id',type:'string',showable:false},
                                {name:'residentialAreaName',type:'string',showable:true},
                                {name:'houseOwner',type:'string',showable:true},
                                {name:'flowNum',type:'string',showable:true},
                                {name:'invoiceNum',type:'string',showable:true},
                                {name:'createTime',type:'string',showable:true},
                                {name:'handler',type:'string',showable:true},
                                {name:"state",showable:true,render:function(row){
                                    var dic = main.findArrayValue(row,data["ChargeBillState.json"])
                                    return dic&&dic.text?dic.text:"";
                                }},
                                {name:'actualSum',type:'string',showable:true}
                            ]
                        },
                        editable:false,
                        deleteable:false,
                        tableSettings:{
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
                                                if(columnIdx>=6){
                                                    return data;
                                                }else{
                                                    return '';
                                                }
                                            }
                                        }
                                    }
                                }
                            ],
                            "footerCallback": function ( row, data, start, end, display ) {
                                var api = this.api();
                                $.post("/rest/chargeBill/sumByForm.action",$("#searchForm").serializeJSON(),null,"json").done(function (resp) {
                                    $( api.column( 7 ).footer() ).html(
                                        resp.data
                                    );
                                })

                            },
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
<div class="container" >
    <form id="searchForm" class="form-horizontal" role="form">
        <div class="row clearfix">
            <div class="col-xs-4 search-form-group">
                <label class="control-label col-xs-3">收缴单号</label>
                <div class="col-xs-9">
                    <input type="text" class="form-control" placeholder="要查询的收缴单号" name="flowNum"/>
                </div>
            </div>
            <div class="col-xs-4 search-form-group">
                <label class="control-label col-xs-3">起始日期</label>
                <div class="col-md-9 col-sm-9 col-xs-9">
                    <input type="text" id="startDate" class="form-control" placeholder="要查询收缴单起始日期" onClick="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\',{d:-0})}'})" name="fromDate"/>
                </div>
            </div>
            <div class="col-xs-4 search-form-group">
                <label class="control-label col-xs-3">截至日期</label>
                <div class="col-xs-9">
                    <input type="text" id="endDate" class="form-control" placeholder="要查询收缴单截至日期" onClick="WdatePicker({minDate:'#F{$dp.$D(\'startDate\',{d:0})}'})" name="endDate"/>
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
            <div class="col-xs-4 search-form-group">
                <label class="control-label col-xs-3">业主姓名</label>
                <div class="col-xs-9">
                    <input type="text" class="form-control" placeholder="要查询收缴单业主姓名" name="ownerName"/>
                </div>
            </div>
            <div class="col-xs-4 search-form-group">
                <label class="control-label col-xs-3">收缴人</label>
                <div class="col-xs-9">
                    <input type="text" class="form-control" placeholder="要查询收缴单的收缴人" name="handler"/>
                </div>
            </div>
        </div>
        <div class="row clearfix">
            <div class="col-xs-12 search-form-group">
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
            <th>所属小区</th>
            <th>业主姓名</th>
            <th>收缴单号</th>
            <th>发票号</th>
            <th>收款日期</th>
            <th>收缴人</th>
            <th>状态</th>
            <th>收缴金额(元)</th>
        </tr>
        </thead>
        <tfoot>
        <tr>
            <th colspan="7" style="text-align:right" rowspan="1">合计:</th>
            <th></th>
        </tr>
        </tfoot>
    </table>
</div>

</body>
</html>