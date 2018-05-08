<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>

    <title>基金计息</title>

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
        var treeObj
        require(["houseTree","layer","my97date"],function (tree) {
            layer.config({
                offset:"100px"
            })
            $(function () {
                treeObj = tree.genTree("selectTree");

                $("#calculateBtn").on("click",function () {
                    var param = tree.getPathParam();
                    if(!param){
                        layer.alert("请选择一个计息范围!");
                        return
                    }
                    $.post("/rest/accrual/lastDate.action",{paths:param},null,"json").done(function (data) {
                        if(!data.success){
                            layer.alert(data.description);
                            return;
                        }
                        layer.confirm('<p>'+data.description+'</p><p>请选择计息截至日期：</p><p><input onClick="WdatePicker({maxDate:\'%y-%M-%d\'})" style="width: 100%"/></p>',{title:"结息状态",area:["400px"],btn:["开始计息","取消"]},function (index,layObj) {
                            var toDate = layObj.find("input").val();
                            if(!toDate){
                                layer.alert("请选择计息结束时间!");
                                return
                            }
                            $.post("/rest/accrual/accrualCalculate.action",{paths:param,toDate:toDate},null,"json").done(function(data){
                                layer.alert(data.description);
                            }).fail(function(xhr){
                                layer.alert(xhr.statusText)
                            });
                        })
                    }).fail(function (xhr) {
                        layer.alert(xhr.statusText)
                    })
                });
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
                    <h4><i class="fa fa-align-left"></i> 计息范围 </h4>
                    <div class="clearfix"></div>
                </div>
                <div class="x_content">
                    <div id="selectTree" class="ztree"></div>
                </div>
            </div>
        </div>
        <div class="col-md-8 column">
            <div class="row" style="margin-left: 20px;">
                <h3 >
                    已选计息单位
                </h3>
                <ul  id="selectedNodes">

                </ul>
            </div>
        </div>
    </div>
    <div class="row clearfix" style="padding: 10px;">
        <button id="calculateBtn" style="float:right" class="btn btn-primary" type="button">开始计息</button>
    </div>
</div>

</body>
</html>