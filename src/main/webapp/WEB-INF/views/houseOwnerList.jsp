<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>

    <title>业主变更</title>

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
                common.loadDeps(["HouseType.json"],function (data) {
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
                                {name:"ownerTel",showable:true},
                                {name:"ownerPsptid",showable:true},
                                {name:"area",showable:true},
                                {name:"type",showable:true,render:function(row){
                                    var dic = common.findArrayValue(row,data["HouseType.json"])
                                    return dic&&dic.text?dic.text:"";
                                }}
                            ]
                        },
                        editable:false,
                        deleteable:false,
                        customBtns:[
                            {label:'更改业主',callback:function (index,item ){
                                $("#infoForm input[name='id']").val(item.id);
                                $("#infoForm input[name='ownerName']").val(item.ownerName?item.ownerName:'');
                                $("#infoForm input[name='ownerPsptid']").val(item.ownerPsptid?item.ownerPsptid:'');
                                $("#infoForm input[name='ownerTel']").val(item.ownerTel?item.ownerTel:'');
                                var win = layer.open({
                                    type: 1,
                                    title: "业主信息",
                                    offset: '20px',
                                    content: $('#popWin'),
                                    area: ["400px","300px"],
                                    btn: ['确定', '取消'],
                                    yes: function () {
                                        layer.msg('拼命加载中......', {shade: [0.8, '#393D49'], time: 0, icon: 16});
                                        var formdata = $("#infoForm").serializeJSON()
                                        $.post("/rest/house/updateOwnerInfo.action",formdata,null,"json").done(function (data) {
                                            layer.alert(data.description);
                                            tableObj.ajax.reload();
                                            layer.close(win);
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
                <input type="hidden" name="id"/>
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
</body>
</html>