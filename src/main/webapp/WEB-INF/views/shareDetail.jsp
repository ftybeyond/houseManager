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
        require(["zTree","layer"],function (main) {
            $(function () {
                var setting = {
                    async:{
                        autoParam:["id","level","name","checked"],
                        enable:true,
                        otherParam:{"param":${record.residentialArea}},
                        url:"/rest/tree/selectHouseTree.action"
                    },
                    check: {
                        enable: true
                    },
                    callback: {
                        onAsyncSuccess: function (e,treeId,treeNode,msg) {

                        },
                        onCheck:showOnDiv
                    }
                };

                treeObj = $.fn.zTree.init($("#selectTree"), setting);
                //绑定计算按钮时间，计算分摊结果
                $("#calculateBtn").on("click",function () {
                    var addCheckedNodes = loadAllChecksNodes();
                    var param = "";
                    for(key in addCheckedNodes){
                        param += getIdPath(addCheckedNodes[key]);
                        param+=","
                    }
                    if(param.length==0){
                        layer.alert("请至少选择一个分摊单位!")
                        return;
                    }
                    param = param.substring(0,param.lastIndexOf(","));
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
                                //分摊入账
                                $.ajax({
                                    url:'/rest/share/doShare.action',
                                    dataType:'json',
                                    type:"POST",
                                    data:{paths:param,sumArea:data.attr.sumArea,totalHouse:data.attr.sumHouses,shareType:${record.shareType},cost:${record.moneySum},record:${record.id}}
                                }).done(function (data) {
                                    layer.alert(data.description)
                                }).fail(function (xhr) {
                                    layer.alert(xhr.statusText);
                                })
                            }
                            layer.close(index);
                        });
                    }).fail(function(xhr){
                        layer.alert(xhr.statusText);
                    })
                })
                function loadAllChecksNodes(){
                    var checkedNodes = treeObj.getNodesByParam("checked",true);//获取所有已选中的节点
                    var result = new Object()//结果对象
                    $.each(checkedNodes,function(index,item){
                        if(item.check_Child_State == 2 || item.check_Child_State == -1){
                            //当前是一个全选节点,判断其父节点是否也是全选节点
                            var parentNode = item.getParentNode()
                            if(parentNode == null){
                                //当前是根节点被选中，无需向父级查找
                                result[item.tId] = item
                            }else{
                                //向父节点递归，直到祖先节点是半选状态
                                var finalNode = getAllCheckedAncestors(item);
                                result[finalNode.tId] = finalNode;
                            }
                        }
                    });
                    return result;
                }

                function showOnDiv() {
                    var allCheckedNodes = loadAllChecksNodes();
                    $("#selectedNodes").empty();
                    //显示到页面
                    for(key in allCheckedNodes){
                        $("#selectedNodes").append('<li>'+getNamePath(allCheckedNodes[key])+'</li>')
                        console.log();
                    }
                }

                /**
                 * 查找指定上节点分支上，最高级的全选节点
                 * @param treeNode
                 */
                function getAllCheckedAncestors(treeNode) {
                    var parentNode = treeNode.getParentNode();
                    if(parentNode != null && parentNode.check_Child_State==2){
                        return getAllCheckedAncestors(parentNode)
                    }else{
                        return treeNode;
                    }
                }

                function getNamePath(treeNode){
                    var result = "";
                    var pathNodes = treeNode.getPath();
                    $.each(pathNodes,function(index,item){
                        result +=item.name;
                        if(index !=(pathNodes.length-1)){
                            result += "-"
                        }
                    })
                    return result;
                }

                function getIdPath(treeNode){
                    var result = "";
                    var pathNodes = treeNode.getPath();
                    $.each(pathNodes,function(index,item){
                        if (item.level == 3) {
                            result +=item.name;
                        } else {
                            result +=item.id;
                        }
                        if(index !=(pathNodes.length-1)){
                            result += "-"
                        }
                    })
                    return result;
                }
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