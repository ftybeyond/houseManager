<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>

    <title>收缴登帐</title>

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
        var handleItem;
        var tableObj;
        require(["houseTree","dataTables-bs","layer","common"],function (tree,table,layer,common) {
            layer.config({
                offset:"100px"
            })
            $(function () {
                common.loadDeps(["ChargeBillType.json"],function (data) {
                    var treeObj;
                    treeObj = tree.genTree("selectTree",{
                        check:{
                            enable:false
                        },
                        callback:{
                            onClick:function(event, treeId, treeNode){
                                $("#searchForm input[name='id']").val(treeNode.id);
                                $("#searchForm input[name='level']").val(treeNode.level);
                                $("#searchForm input[name='name']").val(treeNode.name);
                                tableObj.ajax.reload()
                            }
                        }
                    });
                    var table_config ={
                        url:'/rest/chargeBill/selectByTreeNode.action',
                        domain:{
                            props:[
                                {name:"id",showable:false},
                                {name:"houseCode",showable:true},
                                {name:"houseArea",showable:true},
                                {name:"houseUnitPrice",showable:true},
                                {name:"ratio",showable:true},
                                {name:"actualSum",showable:true},
                                {name:"chargeType",showable:true,render:function(row){
                                    var dic = common.findArrayValue(row,data["ChargeBillType.json"])
                                    return dic&&dic.text?dic.text:"";
                                }}
                            ]
                        },
                        editable:false,
                        deleteable:false,
                        customBtns:[
                            {label:'登帐',callback:function (index,item ){
                                handleItem = item;
                                layer.confirm('<p>产业编码：'+item.houseCode+'</p><p>登帐金额：'+item.actualSum+'(元)</p>',{title:"登帐确认",yes:function () {
                                    //开票
                                    layer.open({
                                        title:'开票',
                                        area: ['800px', '500px'],
                                        offset:'t',
                                        type: 2,
                                        content: '/page/invoice.action?chargeBillId='+item.id, //这里content是一个URL，如果你不想让iframe出现滚动条，你还可以content: ['http://sentsin.com', 'no']
                                        btn:["转入开票","强制登帐"],
                                        btn1:function(index,layObj){
                                            var iframeWin = window[layObj.find('iframe')[0]['name']];
                                            iframeWin.vbInvoice()
                                        },
                                        btn2:function () {
                                            layer.confirm("强制登帐会跳过开票过程，直接入帐，确认执行此操作？",{title:'提示',yes:function() {
                                                $.post("/rest/chargeBill/account.action",handleItem,null,"json").done(function (data) {
                                                    if (data.success) {
                                                        layer.alert(data.description);
                                                        tableObj.ajax.reload()
                                                    } else {
                                                        layer.alert(data.description)
                                                    }
                                                })
                                            }})
                                        }
                                    });
                                    return
                                }});
                            }},
                            {label:"补打缴费单",callback:function (index,item ) {
                                var win = layer.open({
                                    type:2,
                                    offset:"20px",
                                    area:["800px","500px"],
                                    content:'/forward/chargeBillPrint.action?id='+item.id,
                                    btn:["打印","取消"],
                                    btn1:function(index,layero){
                                        console.log(window[layero.find('iframe')[0]['name']].print());
                                    }
                                })
                            }}
                        ]
                    }
                    tableObj = common.init(table_config)
                })
            })
        })
        function updateChargeBill(id,invoiceNo) {
            if(handleItem&&handleItem.id == id){
                handleItem.invoiceNum = invoiceNo
                $.post("/rest/chargeBill/account.action",handleItem,null,"json").done(function (data) {
                    if (data.success) {
                        layer.alert(data.description,function () {
                            layer.closeAll()
                        });
                        tableObj.ajax.reload()
                    } else {
                        layer.alert(data.description)
                    }
                })
            }else{
                layer.alert("参数错误!")
            }
        }
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
                <input type="hidden" name="id"/>
                <input type="hidden" name="level"/>
                <input type="hidden" name="name"/>
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
                        <th>产业代码</th>
                        <th>房屋面积(㎡)</th>
                        <th>房屋单价(元/㎡)</th>
                        <th>收缴系数</th>
                        <th>收缴金额(元)</th>
                        <th>收缴类型</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                </table>
            </div>
        </div>
    </div>
</div>

</body>
</html>