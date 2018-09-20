<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>

    <title>个人账户查询</title>

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
        require(["houseTree","dataTables-bs","layer","common"],function (tree,table,layer,common) {
            layer.config({
                offset:"100px"
            })
            $(function () {
                common.loadDeps(["HouseType.json","TradeType.json"],function (data) {
                    var treeObj;
                    var tableObj;
                    treeObj = tree.genTree("selectTree",{
                        onCheck:function () {
                            $("#searchForm input[name='paths']").val(tree.getPathParam());
                            tableObj.ajax.reload();
                        }
                    });
                    var table_config ={
                        url:'/rest/house/selectByTreeNode.action',
                        domain:{
                            props:[
                                {name:"id",showable:false},
                                {
                                    className: 'details-control',
                                    showable: true,
                                    defaultContent: ''
                                },
                                {name:"residentialAreaName",showable:true},
                                {name:"buildingName",showable:true},
                                {name:"unitName",showable:true},
                                {name:"floor",showable:true},
                                {name:"name",showable:true},
                                {name:"ownerName",showable:true},
                                {name:"ownerPsptid",showable:true},
                                {name:"ownerTel",showable:true},
                                {name:"type",showable:true,render:function(row){
                                    var dic = common.findArrayValue(row,data["HouseType.json"])
                                    return dic&&dic.text?dic.text:"";
                                }},
                                {name:"accountBalance",showable:true}
                            ]
                        },
                        editable:false,
                        deleteable:false,
                        tableSettings:{
                            "footerCallback": function ( row, data, start, end, display ) {
                                var api = this.api();
                                $.post("/rest/house/balanceSum.action",$("#searchForm").serializeJSON(),null,"json").done(function (resp) {
                                    $( api.column( 10 ).footer() ).html(
                                        resp.data
                                    );
                                })

                            }
                        }
                    }
                    tableObj = common.init(table_config);
                    tableObj.on('click', 'td.details-control', function () {
                        var tr = $(this).closest('tr');
                        var row = tableObj.row( tr );
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
                                    url:"/rest/accountLog/selectByHouseCode.action",
                                    type:"POST",
                                    data:{code:rowData.code},
                                    dataType:"json"
                                }).done(function(rsp){
                                    var rows ='';
                                    $.each(rsp.data,function(index,item){
                                        var tradeType = common.findArrayValue(item.tradeType,data["TradeType.json"]);
                                        rows += '<tr>' +
                                            '<td>'+(tradeType?tradeType.text:'')+'</td>' +
                                            '<td>'+item.tradeTime+'</td>' +
                                            '<td>'+item.tradeMoney+'</td>' +
                                            '<td>'+item.balance+'</td>' +
                                            '<td>'+(item.houseOwner?item.houseOwner:'')+'</td>' +
                                            '<td>'+item.handler+'</td>' +
                                            '</tr>'
                                    });
                                    var showInfo =  '<table class="table inner">'+
                                        '<tr>'+
                                        '<th>交易类型</th>'+
                                        '<th>交易时间</th>'+
                                        '<th>交易金额(元)</th>'+
                                        '<th>交易前余额</th>'+
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
<div class="container">
    <div class="row clearfix">
        <div class="col-md-3 column" style="min-width: 250px;">
            <div class="x_panel">
                <div class="x_title">
                    <h4><i class="fa fa-align-left"></i> 房屋选择 </h4>
                    <div class="clearfix"></div>
                </div>
                <div class="x_content">
                    <div id="selectTree" class="ztree"></div>
                </div>
            </div>
        </div>
        <div class="col-md-9 column">
            <form id="searchForm" class="form-horizontal" role="form">
                <input type="hidden" name="paths"/>
                <div class="row clearfix" style="padding: 10px;">
                    <div class="col-xs-5 search-form-group">
                        <label class="control-label col-xs-3">业主姓名</label>
                        <div class="col-xs-9">
                            <input type="text" class="form-control" placeholder="要查询的房屋的业主姓名" name="ownerName"/>
                        </div>
                    </div>
                    <div class="col-xs-5 search-form-group">
                        <label class="control-label col-xs-3">产业代码</label>
                        <div class="col-xs-9">
                            <input type="text" class="form-control" placeholder="要查询的房屋的产业代码" name="houseCode"/>
                        </div>
                    </div>
                    <div class="col-xs-2 search-form-group" style="float: right">
                        <div class="col-xs-6">
                            <button id="searchBtn" class="btn btn-primary" type="button">查询</button>
                        </div>
                    </div>
                </div>
            </form>
            <div class="row" style="margin-left: 20px;">
                <table id="datatable" class="table table-striped table-bordered" style="width:100%">
                    <thead>
                    <tr>
                        <th>明细</th>
                        <th>小区</th>
                        <th>楼栋</th>
                        <th>单元</th>
                        <th>楼层</th>
                        <th>房号</th>
                        <th>业主姓名</th>
                        <th>业主证件</th>
                        <th>业主电话</th>
                        <th>房屋类型</th>
                        <th>账户余额(单位:元)</th>
                    </tr>
                    </thead>
                    <tfoot>
                    <tr>
                        <th colspan="10" style="text-align:right" rowspan="1">合计余额:</th>
                        <th></th>
                    </tr>
                    </tfoot>
                </table>
            </div>
        </div>
    </div>
</div>

</body>
</html>