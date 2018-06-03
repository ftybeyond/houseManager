<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>

    <title>业主补缴</title>

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
                common.loadDeps(["HouseType.json","HasOrNot.json","AlgorithmSwitch.json"],function (baseData) {
                    var treeObj;
                    var tableObj;
                    treeObj = tree.genTree("selectTree",{
                        check:{
                            enable:false
                        },
                        callback:{
                            onClick:function(event, treeId, treeNode){
                                $("#searchForm input[name='id']").val(treeNode.id);
                                $("#searchForm input[name='level']").val(treeNode.level);
                                tableObj.ajax.reload()
                            }
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
                                {name:"area",showable:true},
                                {name:"type",showable:true,render:function(row){
                                    var dic = common.findArrayValue(row,baseData["HouseType.json"])
                                    return dic&&dic.text?dic.text:"";
                                }},
                                {name:"accountBalance",showable:true}
                            ]
                        },
                        editable:false,
                        deleteable:false,
                        customBtns:[
                            {label:'补缴',callback:function (index, item) {
                                $("div.unitPrice").remove();
                                var loadMask = layer.msg('拼命加载中......', {shade: [0.8, '#393D49'], time: 0, icon: 16});
                                $.post("/rest/house/chargeInfo.action",{house:item.id,patch:true},null,"json").done(function (rsp) {
                                    layer.close(loadMask)
                                    if(rsp.success){
                                        if(rsp.attr.chargeBillCount>0){
                                            var confirmWin = layer.confirm("已存在缴费信息，确认继续补缴基金？",{yes:function(){
                                                handleCharge(rsp,item);
                                            },btn2:function () {
                                                layer.close(confirmWin);
                                            }})
                                        }else{
                                            handleCharge(rsp,item)
                                        }
                                    }else{
                                        layer.alert(rsp.description)
                                    }
                                }).fail(function (xhr) {
                                    layer.alert("服务器内部错误!")
                                })
                            }}
                        ]
                    };
                    $("#customSwitch").change(function(){
                        if($(this).prop("checked")){
                            $("#chargeMoney").hide();
                            $("#chargeMoney").before('<input class="form-control" placeholder="输入自定义补缴金额..." type="text" name="chargeMoney"/>')
                        }else{
                            $("#chargeMoney").show();
                            $("#infoForm input[name='chargeMoney']").remove();
                        }
                    });
                    tableObj = common.init(table_config)
                    var handleCharge = function(rsp,item){
                        var houseType = common.findArrayValue(item.type,baseData["HouseType.json"]);
                        var hasElevator = common.findArrayValue(item.hasElevator,baseData["HasOrNot.json"]);
                        var algorithmType = common.findArrayValue(rsp.attr.algorithmSwitch.chargeSwitch,baseData["AlgorithmSwitch.json"]);
                        $("#chargeHouseType").html(houseType?houseType.text:"");
                        $("#chargeHouseElevator").html(hasElevator?hasElevator.text:"");
                        $("#algorithmType").html(algorithmType?algorithmType.text:"");
                        $("#chargeHouseArea").html(item.area);
                        var chargeMoney=0;
                        var param = {};
                        if(rsp.attr.algorithmSwitch.chargeSwitch == 0){
                            //按房价 result = 单价x面积x房价系数
                            var divUnitPrice = '<div class="form-group unitPrice">' +
                                '                    <label class="control-label col-md-3 col-sm-3 col-xs-3">房屋单价(元/㎡)</label>' +
                                '                    <div class="col-md-9 col-sm-9 col-xs-9">' +
                                '                        <div style="width: 100%;padding-top: 8px;">'+item.unitPrice+'</div>' +
                                '                    </div>' +
                                '                </div>';
                            var chargeMoneyDiv = $("#chargeMoney").parents(".form-group");
                            $("#chargeRatio").html(rsp.attr.chargeCriterion.priceRatio);
                            chargeMoneyDiv.before(divUnitPrice);
                            chargeMoney = (item.unitPrice*item.area*rsp.attr.chargeCriterion.priceRatio).toFixed(2);
                            param.ratio = rsp.attr.chargeCriterion.priceRatio;
                            param.chargeType = 0;
                        }else if(rsp.attr.algorithmSwitch.chargeSwitch == 1){
                            //按面积 result = 面积x面积系数
                            $("#chargeRatio").html(rsp.attr.chargeCriterion.areaRatio);
                            chargeMoney = (item.area*rsp.attr.chargeCriterion.areaRatio).toFixed(2);
                            param.ratio = rsp.attr.chargeCriterion.areaRatio;
                            param.chargeType = 1;
                        }else{
                            layer.alert("缴费算法开关参数错误!");
                            return;
                        }
                        $("#chargeMoney").html(chargeMoney);
                        var win = layer.open({
                            type: 1,
                            title: "缴费信息",
                            offset: '20px',
                            content: $('#popWin'),
                            area: ["400px","500px"],
                            btn: ['生成缴费单', '取消'],
                            btn1:function(){
                                param.houseCode = item.code;
                                param.houseArea = item.area;
                                param.houseUnitPrice = item.unitPrice;
                                param.houseOwner = item.ownerName;
                                param.houseTel = item.ownerTel;
                                if($('#customSwitch').prop('checked')){
                                    param.chargeType = 2;
                                    param.ratio = 0;
                                    param.actualSum = $("#infoForm input[name='chargeMoney']").val()
                                    if(!param.actualSum || isNaN(param.actualSum)){
                                        layer.alert("请填入有效的缴费金额!");
                                        return;
                                    }
                                }else{
                                    param.actualSum = chargeMoney;
                                }
                                layer.msg('拼命加载中......', {shade: [0.8, '#393D49'], time: 0, icon: 16});
                                $.post("/rest/house/chargeBillPatch.action",param,null,"json").done(function(data){
                                    layer.closeAll();
                                    tableObj.ajax.reload();
                                    layer.alert(data.description,{btn:["打印补缴单"],btn1:function(){
                                        var win = layer.open({
                                            type:2,
                                            offset:"20px",
                                            area:["800px","500px"],
                                            content:'/forward/chargeBillPrint.action?id='+data.data.id,
                                            btn:["打印","取消"],
                                            btn1:function(index,layero){
                                                console.log(window[layero.find('iframe')[0]['name']].print());
                                            }
                                        })
                                    }})
                                }).fail(function(){
                                    layer.alert("服务器内部错误!");
                                })
                            },
                            btn2:function(){layer.closeAll()}
                        })
                    }

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
                <input type="hidden" name="id"/>
                <input type="hidden" name="level"/>
                <input type="hidden" name="hasOwner" value="true">
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
                        <th>业主姓名</th>
                        <th>业主证件</th>
                        <th>房屋面积(㎡)</th>
                        <th>房屋类型</th>
                        <th>账户余额(单位:元)</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                </table>
            </div>
        </div>
    </div>
</div>

<div id="popWin" style="display: none">
    <div class="x_panel">
        <div class="x_content">
            <form id="infoForm" class="form-horizontal form-label-left input_mask">
                <input type="hidden" name="id"/>

                <div class="form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-3">算法类型</label>
                    <div class="col-md-9 col-sm-9 col-xs-9">
                        <div style="width: 100%;padding-top: 8px;" id="algorithmType"></div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-3">住宅类型</label>
                    <div class="col-md-9 col-sm-9 col-xs-9">
                        <div style="width: 100%;padding-top: 8px;" id="chargeHouseType"></div>
                    </div>
                </div>

                <div class="form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-3">电梯</label>
                    <div class="col-md-9 col-sm-9 col-xs-9">
                        <div style="width: 100%;padding-top: 8px;" id="chargeHouseElevator"></div>
                    </div>
                </div>

                <div class="form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-3">房屋面积(㎡)</label>
                    <div class="col-md-9 col-sm-9 col-xs-9">
                        <div style="width: 100%;padding-top: 8px;" id="chargeHouseArea"></div>
                    </div>
                </div>

                <div class="form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-3">缴费系数</label>
                    <div class="col-md-9 col-sm-9 col-xs-9">
                        <div style="width: 100%;padding-top: 8px;" id="chargeRatio"></div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-3">自定义金额(元)</label>
                    <div class="col-md-9 col-sm-9 col-xs-9">
                        <input type="checkbox" style="width: 15px;height: 15px;margin-top: 8px;" id="customSwitch"/>
                    </div>
                </div>

                <div class="form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-3">应缴费</label>
                    <div class="col-md-9 col-sm-9 col-xs-9">
                        <div style="width: 100%;padding-top: 8px;" id="chargeMoney"></div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>