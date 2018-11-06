<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>

    <title>账户查询</title>

    <link rel="stylesheet" type="text/css" href="<%=path%>/vendors/datatable/datatables-bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="<%=path%>/vendors/zTree/css/metroStyle/metroStyle.css"/>
    <link rel="stylesheet" type="text/css" href="<%=path%>/vendors/select2/select2.min.css"/>
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
        require(["houseTree","dataTables-bs","layer","common","mySelect","datatables-print"],function (tree,table,layer,common) {
            layer.config({
                offset:"100px"
            })
            $(function () {
                common.loadDeps(["HouseType.json","ShareType.json","TradeType.json"],function (data) {
                    $("#shareTypeSelect").mySelect2({data: data["ShareType.json"]})
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

                            },
                            dom: 'Blrtip',
                            buttons: [
                                {
                                    className: 'btn btn-primary',
                                    text: '支出查询预警',
                                    action: function (e, dt, node, config) {
                                        var paths = tree.getPathParam();
                                        if(paths.length<1){
                                            layer.alert("请选择一个明确的支出单位!");
                                            return;
                                        }
                                        $("#payDomain").html(tree.getPathNames());
                                        var loadingMask = layer.msg('拼命计算中......', {shade: [0.8, '#393D49'], time: 0, icon: 16});
                                        $.ajax({
                                            url:'/rest/share/calculateResult.action',
                                            dataType:'json',
                                            type:"POST",
                                            data:{paths:paths}
                                        }).done(function(d){
                                            layer.close(loadingMask)
                                            $("#sumArea").html(d.attr.sumArea);
                                            $("#sumHouses").html(d.attr.sumHouses);
                                            layer.open({
                                                type: 1,
                                                title: "支出预警",
                                                offset: '20px',
                                                content: $('#popWin'),
                                                area: ["600px","400px"],
                                                btn: ['分摊'],
                                                btn1:function(index,layObj){
                                                    if(d.attr.sumArea==0&&$("#shareTypeSelect").val()==1){
                                                        layer.alert("没有可分摊的房屋面积!");
                                                        return;
                                                    }else if(d.attr.sumHouses==0&&$("#shareTypeSelect").val()==2){
                                                        layer.alert("没有可分摊的房屋!");
                                                        return;
                                                    }else if(!($("#shareTypeSelect").val()&&$("#shareTypeSelect").val().length>0&&$("#totalCost").val()&&$("#totalCost").val().length>0)){
                                                        layer.alert("请输入分摊金额和分摊类型!");
                                                        return;
                                                    }else{
                                                        var loadingMask = layer.msg('拼命计算中......', {shade: [0.8, '#393D49'], time: 0, icon: 16});
                                                        var reqParam = {paths:paths,sumArea:d.attr.sumArea,totalHouse:d.attr.sumHouses,shareType:$("#shareTypeSelect").val(),cost:$("#totalCost").val()}
                                                        //检测余额不足账户
                                                        $.post("/rest/share/shareCheck.action",reqParam,null,"json").done(function(checkData){
                                                            layer.close(loadingMask)
                                                            var warn = "<p>账户余额充足!</p>";
                                                            if(checkData.data&&checkData.data.length > 0){
                                                                warn = "<p>以下账户余额已不足</p>";
                                                                $.each(checkData.data,function(index,item){
                                                                    warn += '<p>产业代码：' + item.code + '，业主：'+item.ownerName+'，余额：' +item.accountBalance.toFixed(2)+ '</p>';
                                                                });
                                                            }
                                                            layer.confirm( warn,{btn:["确认","导出明细"],yes:function(){
                                                                layer.closeAll();
                                                            },btn2:function () {
                                                                $("#exportForm").empty();
                                                                for(var key in reqParam){
                                                                    $("#exportForm").append('<input type="hidden" name="'+key+'" value="'+reqParam[key]+'"/>')
                                                                }
                                                                $("#exportForm").append('<input type="hidden" name="names" value="'+tree.getPathNames()+'"/>')
                                                                $("#exportForm").submit();
                                                            }});
                                                        })
                                                    }
                                                }
                                            })
                                        }).fail(function(xhr){
                                            layer.alert(xhr.statusText);
                                        })
                                    }
                                }
                            ]
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
                                            '<td>'+eval(item.balance+item.tradeMoney)+'</td>' +
                                            '<td>'+(item.houseOwner?item.houseOwner:'')+'</td>' +
                                            '<td>'+item.handler+'</td>' +
                                            '</tr>'
                                    });
                                    var showInfo =  '<table class="table inner">'+
                                        '<tr>'+
                                        '<th>交易类型</th>'+
                                        '<th>交易时间</th>'+
                                        '<th>交易金额(元)</th>'+
                                        '<th>交易后余额</th>'+
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
<div id="popWin" style="display: none;">
    <div class="x_panel">
        <div class="x_content">
            <form id="infoForm" class="form-horizontal form-label-left input_mask">
                <div class="form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-3">支出范围</label>
                    <div id="payDomain" class="col-md-9 col-sm-9 col-xs-9" style="padding-top: 8px">

                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-3">合计面积</label>
                    <div id="sumArea" class="col-md-9 col-sm-9 col-xs-9" style="padding-top: 8px">

                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-3">合计户数</label>
                    <div id="sumHouses" class="col-md-9 col-sm-9 col-xs-9" style="padding-top: 8px">

                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-3">支出费用</label>
                    <div class="col-md-9 col-sm-9 col-xs-9">
                        <input type="text" class="form-control" id="totalCost" placeholder="请输入支出费用......" onkeyup="this.value=this.value.replace(/[^\0-9\.]/g,'');">
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-3">分摊类型</label>
                    <div class="col-md-9 col-sm-9 col-xs-9">
                        <select id="shareTypeSelect" class="form-control" style="width:100%;"></select>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<form id="exportForm" action="/export/shareCheck.action" method="post" style="display: none">
</form>
</body>
</html>