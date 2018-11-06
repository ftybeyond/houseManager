<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>

    <title>基金返还</title>

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
        .btn-dark{
            float: right;
        }
    </style>
    <script src="<%=path%>/vendors/requireJS/require.js"></script>
    <script type="text/javascript" src="<%=path%>/vendors/requireJS/require-config.js"></script>
    <script type="text/javascript">
        require(["houseTree","dataTables-bs","layer","common","datatables-buttons","my97date"],function (tree,table,layer,common) {
            layer.config({
                offset:"100px"
            })
            $(function () {
                common.loadDeps(["HouseType.json"],function (data) {
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
                                {name:"code",showable:true},
                                {name:"ownerName",showable:true},
                                {name:"ownerPsptid",showable:true},
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
                                    $( api.column( 4 ).footer() ).html(
                                        resp.data
                                    );
                                })
                            },
                            dom: 'Blrtip',
                            buttons: [
                                {
                                    text: '批量返还',// 显示文字
                                    className:'btn btn-dark',
                                    action:function(e, dt, node, config){
                                        var namePaths = tree.getAllSelectPath();
                                        if(!namePaths){
                                            layer.alert("请选择要批量返还基金的范围!");
                                           return;
                                        }
                                        if(($(dt.column( 4 ).footer()).html()<=0)){
                                            layer.alert("范围内无基金可返还");
                                            return;
                                        }
                                        layer.confirm('<p>批量返还（' + tree.getAllSelectPath() + '）余额？</p><p>合计：'+$(dt.column( 4 ).footer()).html()+'元</p><p>返还后，余额将被清零！</p>',{icon:3,yes:function(){
                                            var loadingMask = layer.msg('拼命计算中......', {shade: [0.8, '#393D49'], time: 0, icon: 16});
                                            $.post("/rest/house/balanceBackBatch.action",$("#searchForm").serializeJSON(),null,"json").done(function (resp) {
                                                $("#exportForm input[name='seq']").val(resp.data);
                                                $("#exportForm input[name='supplement']").val($(dt.column( 4 ).footer()).html());
                                                $("#exportForm").submit();
                                                layer.close(loadingMask);
                                            })
                                            layer.closeAll();
                                        }})
                                    }
                                }
                            ]
                        },
                        customBtns:[
                            {label:'基金返还',callback:function (index,item ){
                                if(item.accountBalance<=0){
                                    layer.alert("账户下无可返还基金!");
                                    return;
                                }
                                layer.confirm('<p>产业编码：'+item.code+'</p><p>返还金额：'+item.accountBalance+'</p>',{title:"返还确认",yes:function () {
                                    var loadingMask = layer.msg('拼命计算中......', {shade: [0.8, '#393D49'], time: 0, icon: 16});
                                    $.post("/rest/house/balanceBack.action",{house:item.id},null,"json").done(function (data) {
                                        layer.close(loadingMask);
                                        layer.alert(data.description);
                                        tableObj.ajax.reload();
                                    })
                                }});
                            }}
                        ]
                    }
                    tableObj = common.init(table_config);
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
                </div>
                <div class="row clearfix" style="padding: 10px;">
                    <div class="col-xs-5 search-form-group">
                        <label class="control-label col-xs-3">起始登账时间</label>
                        <div class="col-xs-9">
                            <input type="text" id="accountDateStart" class="form-control" placeholder="要查询的房屋的起始登账时间" onClick="WdatePicker({maxDate:'#F{$dp.$D(\'accountDateEnd\',{d:0})}'})" name="accountDateStart"/>
                        </div>
                    </div>
                    <div class="col-xs-5 search-form-group">
                        <label class="control-label col-xs-3">截止登账时间</label>
                        <div class="col-xs-9">
                            <input type="text" id="accountDateEnd" class="form-control" placeholder="要查询的房屋的截止登账时间" onClick="WdatePicker({minDate:'#F{$dp.$D(\'accountDateStart\',{d:0})}'})" name="accountDateEnd"/>
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
                        <th>产业代码</th>
                        <th>业主姓名</th>
                        <th>业主证件</th>
                        <th>房屋类型</th>
                        <th>账户余额(单位:元)</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tfoot>
                    <tr>
                        <th colspan="4" style="text-align:right" rowspan="1">合计余额:</th>
                        <th></th>
                        <th></th>
                    </tr>
                    </tfoot>
                </table>
            </div>
        </div>
    </div>
</div>
<
<form id="exportForm" action="/export/balanceBack.action" method="post" style="display: none">
    <input type="hidden" name="seq" />
    <input type="hidden" name="supplement" />
    <input type="hidden" name="start" value="0"/>
    <input type="hidden" name="length" value="-1"/>
</form>
</body>
</html>