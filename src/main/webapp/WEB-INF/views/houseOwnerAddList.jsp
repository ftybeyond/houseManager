<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>

    <title>业主添加</title>

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
        function testid(id) {
            // 1 "验证通过!", 0 //校验不通过 // id为身份证号码
            var format = /^(([1][1-5])|([2][1-3])|([3][1-7])|([4][1-6])|([5][0-4])|([6][1-5])|([7][1])|([8][1-2]))\d{4}(([1][9]\d{2})|([2]\d{3}))(([0][1-9])|([1][0-2]))(([0][1-9])|([1-2][0-9])|([3][0-1]))\d{3}[0-9xX]$/;
            //号码规则校验
            if(!format.test(id)){
                return {'status':0,'msg':'身份证号码不合规'};
            }
            //区位码校验
            //出生年月日校验  前正则限制起始年份为1900;
            var year = id.substr(6,4),//身份证年
                month = id.substr(10,2),//身份证月
                date = id.substr(12,2),//身份证日
                time = Date.parse(month+'-'+date+'-'+year),//身份证日期时间戳date
                now_time = Date.parse(new Date()),//当前时间戳
                dates = (new Date(year,month,0)).getDate();//身份证当月天数
            if(time>now_time||date>dates){
                return {'status':0,'msg':'出生日期不合规'}
            }
            //校验码判断
            var c = new Array(7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2);  //系数
            var b = new Array('1','0','X','9','8','7','6','5','4','3','2'); //校验码对照表
            var id_array = id.split("");
            var sum = 0;
            for(var k=0;k<17;k++){
                sum+=parseInt(id_array[k])*parseInt(c[k]);
            }
            if(id_array[17].toUpperCase() != b[sum%11].toUpperCase()){
                return {'status':0,'msg':'身份证校验码不合规'}
            }
            return {'status':1,'msg':'校验通过'}
        }
        require(["houseTree","dataTables-bs","layer","common"],function (tree,table,layer,common) {
            layer.config({
                offset:"100px"
            })
            $(function () {
                // 身份证号码验证
                jQuery.validator.addMethod("isIdCardNo", function(value, element) {
                    return this.optional(element) || testid(value).status==1;
                }, "请输入正确的身份证号码。");
                var validator = $("#infoForm").validate({
                    rules:{
                        ownerName:{
                            required:true,
                            maxlength:50
                        },
                        ownerPsptid:{
                            isIdCardNo:true
                        },
                        area: {
                            number: true,
                            required: true
                        },
                        unitPrice: {
                            number: true
                        },
                        totalPrice: {
                            number: true,
                            required: true
                        },
                    }
                });
                $("#infoForm input[name='unitPrice'],#infoForm input[name='area']").on("keyup",function(){
                    var unitPrice = $("#infoForm input[name='unitPrice']").val();
                    var area = $("#infoForm input[name='area']").val();
                    if(!isNaN(unitPrice)&&!isNaN(area)){
                        $("#infoForm input[name='totalPrice']").val((unitPrice*area).toFixed(2));
                    }
                })
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
                                $("#searchForm input[name='name']").val(treeNode.name);
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
                                {name:"ownerTel",showable:true},
                                {name:"ownerPsptid",showable:true},
                                {name:"area",showable:true},
                                {name:"type",showable:true,render:function(row){
                                    var dic = common.findArrayValue(row,baseData["HouseType.json"])
                                    return dic&&dic.text?dic.text:"";
                                }}
                            ]
                        },
                        editable:false,
                        deleteable:false,
                        customBtns:[
                            {label:'添加业主',callback:function (index,item ){
                                $("#infoForm")[0].reset();
                                $("#infoForm input[name='unitPrice']").val(item.unitPrice);
                                $("#infoForm input[name='area']").val(item.area);
                                $("#infoForm input[name='totalPrice']").val(item.totalPrice);
                                $("#infoForm input[name='ownerName']").val(item.ownerName?item.ownerName:'');
                                $("#infoForm input[name='ownerPsptid']").val(item.ownerPsptid?item.ownerPsptid:'');
                                $("#infoForm input[name='ownerTel']").val(item.ownerTel?item.ownerTel:'');
                                validator.resetForm();
                                var win = layer.open({
                                    type: 1,
                                    title: " 业主信息",
                                    offset: '20px',
                                    content: $('#popWin'),
                                    area: ["400px","300x"],
                                    btn: ['确定', '取消'],
                                    yes: function () {
                                        if(!$("#infoForm").valid()){
                                            return false;
                                        }
                                        var loadMask = layer.msg('拼命加载中......', {shade: [0.8, '#393D49'], time: 0, icon: 16});
                                        var formdata = $("#infoForm").serializeJSON();
                                        formdata.id = item.id
                                        $.post("/rest/house/updateOwnerInfo.action",formdata,null,"json").done(function (data) {
                                            layer.close(loadMask)
                                            if(data.success){
                                                item.unitPrice=$("#infoForm input[name='unitPrice']").val();
                                                item.area=$("#infoForm input[name='area']").val();
                                                item.totalPrice = $("#infoForm input[name='totalPrice']").val();
                                                var chargeAlert = layer.alert(data.description,{btn:["缴费"],closeBtn:0,btn1:function () {
                                                    var loadMask = layer.msg('拼命加载中......', {shade: [0.8, '#393D49'], time: 0, icon: 16});
                                                    $.post("/rest/house/chargeInfo.action",{house:item.id},null,"json").done(function (rsp) {
                                                        layer.close(loadMask)
                                                        if(rsp.success){
                                                            if(rsp.attr.chargeBillCount>0){
                                                                layer.alert("已存在缴费信息,补缴请使用补缴功能!",{icon:0});
                                                                return;
                                                            }
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
                                                                var divUnitPrice = '<div class="form-group">' +
                                                                    '                    <label class="control-label col-md-3 col-sm-3 col-xs-3">房屋单价(元/㎡)</label>' +
                                                                    '                    <div class="col-md-9 col-sm-9 col-xs-9">' +
                                                                    '                        <div style="width: 100%;padding-top: 8px;">'+item.unitPrice+'</div>' +
                                                                    '                    </div>' +
                                                                    '                </div>';
                                                                var chargeMoneyDiv = $("#chargeMoney").parents(".form-group");
                                                                $("#chargeRatio").html(rsp.attr.chargeCriterion.priceRatio);
                                                                chargeMoneyDiv.before(divUnitPrice);
                                                                chargeMoney = (item.totalPrice*rsp.attr.chargeCriterion.priceRatio).toFixed(2);
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
                                                            var win2 = layer.open({
                                                                type: 1,
                                                                title: "缴费信息",
                                                                offset: '20px',
                                                                content: $('#popWin2'),
                                                                area: ["400px","500px"],
                                                                btn: ['生成缴费单', '取消'],
                                                                btn1:function(){
                                                                    param.houseCode = item.code;
                                                                    param.houseArea = item.area;
                                                                    param.houseUnitPrice = item.unitPrice;
                                                                    param.houseOwner = $("#infoForm input[name='ownerName']").val();
                                                                    param.houseTel = $("#infoForm input[name='ownerTel']").val();
                                                                    param.houseTotalPrice = $("#infoForm input[name='totalPrice']").val();
                                                                    param.actualSum = chargeMoney;
                                                                    layer.msg('拼命加载中......', {shade: [0.8, '#393D49'], time: 0, icon: 16});
                                                                    $.post("/rest/house/genChargeBill.action",param,null,"json").done(function(data){
                                                                        layer.closeAll();
                                                                        tableObj.ajax.reload();
                                                                        layer.alert(data.description,{btn:["打印缴费单"],btn1:function(){
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
                                                        }else{
                                                            layer.alert(rsp.description)
                                                        }
                                                    }).fail(function (xhr) {
                                                        layer.alert("服务器内部错误!")
                                                    })
                                                }});
                                                tableObj.ajax.reload();
                                                layer.close(win);
                                            }else{
                                                layer.alert(data.description)
                                            }

                                        }).fail(function (xhr) {
                                            layer.alert("服务器内部错误!")
                                        })
                                    },
                                    btn2: function () {
                                        layer.close(win);
                                    }
                                })
                            }}
                        ]
                    }
                    tableObj = common.init(table_config)
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
            <form id="searchForm" class="form-horizontal" role="form" style="display: none">
                <input type="hidden" name="id"/>
                <input type="hidden" name="level"/>
                <input type="hidden" name="name"/>
                <%--<input type="hidden" name="hasOwner" value="false">--%>
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
                        <th>业主电话</th>
                        <th>业主证件</th>
                        <th>房屋面积</th>
                        <th>房屋类型</th>
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
                <div class="form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-3">房屋面积</label>
                    <div class="col-md-9 col-sm-9 col-xs-9">
                        <input type="text" class="form-control" name="area" placeholder="请输入房屋面积......">
                    </div>
                </div>

                <div class="form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-3">房屋单价</label>
                    <div class="col-md-9 col-sm-9 col-xs-9">
                        <input type="text" class="form-control" name="unitPrice" placeholder="请输入房屋单价......">
                    </div>
                </div>

                <div class="form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-3">房屋总价</label>
                    <div class="col-md-9 col-sm-9 col-xs-9">
                        <input type="text" class="form-control" name="totalPrice" placeholder="请输入房屋总价......">
                    </div>
                </div>

                <div class="form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-3">业主姓名</label>
                    <div class="col-md-9 col-sm-9 col-xs-9">
                        <input type="text" class="form-control" name="ownerName" placeholder="请输入业主姓名......">
                    </div>
                </div>

                <div class="form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-3">业主电话</label>
                    <div class="col-md-9 col-sm-9 col-xs-9">
                        <input type="text" class="form-control" name="ownerTel" placeholder="请输入业主电话......">
                    </div>
                </div>

                <div class="form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-3">业主证件</label>
                    <div class="col-md-9 col-sm-9 col-xs-9">
                        <input type="text" class="form-control" name="ownerPsptid" placeholder="请输入业主证件......">
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

<div id="popWin2" style="display: none">
    <div class="x_panel">
        <div class="x_content">
            <form id="infoForm2" class="form-horizontal form-label-left input_mask">
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
                    <label class="control-label col-md-3 col-sm-3 col-xs-3">房屋面积</label>
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