<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>

    <title>基金分摊</title>

    <link rel="stylesheet" type="text/css" href="<%=path%>/vendors/datatable/datatables-bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="<%=path%>/vendors/zTree/css/metroStyle/metroStyle.css"/>
    <link rel="stylesheet" type="text/css" href="<%=path%>/vendors/gentelella/css/custom.min.css"/>
    <link rel="stylesheet" type="text/css" href="<%=path%>/vendors/layer/theme/default/layer.css"/>

    <style type="text/css">
        body{
            background: #F7F7F7;
            padding: 20px;
        }

    </style>
    <script src="<%=path%>/vendors/requireJS/require.js"></script>
    <script type="text/javascript" src="<%=path%>/vendors/requireJS/require-config.js"></script>
    <script type="text/javascript">
        var treeObj
        require(["houseTree","layer"],function (tree) {
            $(function () {
                var setting = {
                    async:{
                        otherParam:{"param":${record.residentialArea}},
                    }
                };

                treeObj = tree.genTree("selectTree", setting);
                //绑定计算按钮时间，计算分摊结果
                $("#calculateBtn").on("click",function () {
                    param = tree.getPathParam();
                    if(!param){
                        layer.alert("请至少选择一个分摊单位!");
                        return;
                    }
                    $.ajax({
                        url:'/rest/share/calculateResult.action',
                        dataType:'json',
                        type:"POST",
                        data:{paths:param}
                    }).done(function(data){
                        var info = '<p>分摊方式：${record.shareType==2?"按户":"按面积"}</p>';
                        info+= '<p>合计面积：'+data.attr.sumArea+' ㎡</p>';
                        info+= '<p>合计户数：'+data.attr.sumHouses+' 户</p>';
                        info+= '<p>合计费用：${record.moneySum} 元</p>';
                        layer.confirm(info,{icon: 7, title:'分摊详情',btn:['入账','取消']}, function(index){
                            //do something
                            if(data.attr.sumArea==0||data.attr.sumHouses==0){
                                layer.alert("没有可分摊的房屋信息!");
                                return;
                            }else{
                                var loadingMask = parent.layer.msg('拼命计算中......', {shade: [0.8, '#393D49'], time: 0, icon: 16});

                                //检测余额不足账户
                                $.post("/rest/share/shareCheck.action",{paths:param,sumArea:data.attr.sumArea,totalHouse:data.attr.sumHouses,shareType:${record.shareType},cost:${record.moneySum},record:${record.id}},null,"json").done(function(checkData){
                                    parent.layer.close(loadingMask)
                                    var warn = "<p>确认入账分摊信息？</p>";
                                    if(checkData.data&&checkData.data.length > 0){
                                        warn += "<p>以下账户余额已不足</p>";
                                        $.each(checkData.data,function(index,item){
                                            warn += '<p>产业代码：' + item.code + '，业主：'+item.ownerName+'，余额：' +item.accountBalance.toFixed(2)+ '</p>';
                                        });
                                    }
                                    layer.confirm( warn,{yes:function(){
                                        var loadingMask = parent.layer.msg('拼命计算中......', {shade: [0.8, '#393D49'], time: 0, icon: 16});
                                        //分摊入账
                                        $.ajax({
                                            url:'/rest/share/doShare.action',
                                            dataType:'json',
                                            type:"POST",
                                            data:{paths:param,sumArea:data.attr.sumArea,totalHouse:data.attr.sumHouses,shareType:${record.shareType},cost:${record.moneySum},record:${record.id}}
                                        }).done(function (data) {
                                            parent.layer.closeAll();
                                            layer.alert(data.description,{clostBtn:0},function(index){
                                                layer.close(index);
                                            });
                                            parent.table.ajax.reload();

                                        }).fail(function (xhr) {
                                            layer.alert(xhr.statusText);
                                        })
                                    }});
                                })
                            }
                            layer.close(index);
                        });
                    }).fail(function(xhr){
                        layer.alert(xhr.statusText);
                    })
                })
            })
        })
    </script>

</head>
<body>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-4 column" style="min-width: 250px;">
            <div class="x_panel">
                <div class="x_title">
                    <h4><i class="fa fa-align-left"></i> 分摊单位 (${record.shareType==2?"按户":"按面积"})</h4>
                    <div class="clearfix"></div>
                </div>
                <div class="x_content">
                    <div id="selectTree" class="ztree"></div>
                </div>
            </div>
        </div>
        <div class="col-md-8 column">
            <div class="row">
                <h5 style="font-weight: bold">
                    分摊事项(合计费用：${record.moneySum})
                </h5>
                <ul>
                    <c:forEach items="${items}" var="item">
                        <li>
                            事项：${item.remark}（${item.price}元）
                        </li>
                    </c:forEach>
                </ul>
            </div>
            <div class="row">
                <h5 style="font-weight: bold">
                    已选分摊单位
                </h5>
                <ul  id="selectedNodes">

                </ul>
            </div>
        </div>
    </div>
    <div class="row clearfix" style="padding: 10px;">
        <button id="calculateBtn" style="float:right" class="btn btn-primary" type="button">计算分摊结果</button>
    </div>
</div>

</body>
</html>