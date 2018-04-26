<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>

    <title>维修记录</title>

    <link rel="stylesheet" type="text/css" href="<%=path%>/vendors/datatable/datatables-bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="<%=path%>/vendors/select2/select2.min.css"
    <!--[if lt IE 8 ]><script src="<%=path%>/vendors/json2.min.js"></script><![endif]-->
    <script src="<%=path%>/vendors/requireJS/require.js"></script>
    <script type="text/javascript" src="<%=path%>/vendors/requireJS/require-config.js"></script>
    <script type="text/javascript">
        require(["common", "mySelect"], function (main) {
            $(function () {
                main.loadDeps(["residential_area", "ShareType.json", "RepairState.json"], function (data) {
                    $("select[name='residentialArea']").mySelect2({data: data["residential_area"]})
                    $("#shareTypeSelect").mySelect2({data: data["ShareType.json"]})
                    var config = {
                        popArea: ['700px', '550px'],
                        domain: {
                            name: 'repairRecord',
                            props: [
                                {name: 'id', type: 'string', showable: false},
                                {
                                    name: 'residentialArea',
                                    type: 'string',
                                    showable: true,
                                    render: function (row, type, full, meta) {
                                        var dic = main.findArrayValue(row, data["residential_area"])
                                        return dic && dic.text ? dic.text : "";
                                    }
                                },
                                {name: 'address', type: 'string', showable: true},
                                {name: 'developer', type: 'string', showable: true},
                                {name: 'propertyCompany', type: 'string', showable: true},
                                {name: 'propertyCompanyTel', type: 'string', showable: true},
                                {name: 'owners', type: 'string', showable: true},
                                {name: 'ownersTel', type: 'string', showable: true},
                                {name: 'shareType', type: 'string', showable: true},
                                {
                                    name: 'state',
                                    type: 'string',
                                    showable: true,
                                    render: function (row, type, full, meta) {
                                        var dic = main.findArrayValue(row, data["RepairState.json"])
                                        return dic && dic.text ? dic.text : "";
                                    }
                                }
                            ]
                        },
                        beforePopWin:function(type){
                            if(type == 1){
                                $("#infoForm div.addItemDiv").remove();
                            }
                        },
                        afterSyncFormData:function (rsp) {
                            $("#infoForm div.addItemDiv").remove();
                            //展示表单数据，载入维修事项列表
                            if(rsp.attr&& rsp.attr.repairItems){
                                $.each(rsp.attr.repairItems,function (index,item) {
                                    //添加维修事项
                                    $("#infoForm").append(genItem(item))
                                })
                            }
                        },
                        beforeSaveOrUpdate:function (param,type) {
                            //追加维修项目参数
                            var repairItems = new Array();
                            $("#infoForm div.addItemDiv").each(function (index) {
                                var remark = $(this).find("textarea[name=remark]").val()
                                var price = $(this).find("input[name=price]").val()
                                repairItems.push({remark:remark,price:price})
                            })
                            if (repairItems.length>0) {
                                var supplement = JSON.stringify(repairItems);
                                param.supplement = supplement
                            }
                        }
                    }
                    var table = main.init(config);
                    $(".x_content").on("click",".delItemBtn",function () {
                        $(this).parents(".addItemDiv").remove();
                    })
                    $("#addItemBtn").on("click", function () {
                        //添加维修事项
                        $("#infoForm").append(genItem())
                    })

                    function genItem(item){
                        var div = '<div class="addItemDiv">' +
                            '               <div class="col-xs-6 row" style="margin-left:3px">' +
                            '                    <label class="control-label col-xs-3">事项描述</label>' +
                            '                    <div class="col-xs-9">' +
                            '                        <textarea type="text" class="form-control" name="remark" placeholder="具体维修内容...">'+(item?item.remark:'')+'</textarea>' +
                            '                    </div>' +
                            '                </div>' +
                            '                <div class="col-xs-6 form-group">' +
                            '                    <label class="control-label col-xs-3">项目价格</label>' +
                            '                    <div class="col-xs-7">' +
                            '                        <input type="text" class="form-control" name="price" placeholder="项目价格..." value="'+(item?item.price:'')+'">' +
                            '                    </div>' +
                            '                    <div class="col-xs-2">' +
                            '                        <button class="btn btn-primary btn-xs delItemBtn" type="button">删除事项</button>' +
                            '                    </div>' +
                            '                </div>' +
                            '</div>';
                        return div;
                    }
                })
            })
        })
    </script>
</head>
<body>
<!--查询表单-->
<div class="container" style="padding-left: 0px;">
    <form id="searchForm" class="form-horizontal" role="form">
        <div class="col-xs-5 form-group">
            <label class="control-label col-xs-3">所属小区</label>
            <div class="col-xs-9">
                <select name="residentialArea" class="form-control" style="width:100%;"></select>
            </div>
        </div>
        <div class="col-xs-2 form-group">
            <div class="col-xs-6">
                <button id="searchBtn" class="btn btn-primary" type="button">查询</button>
            </div>
            <div class="col-xs-6">
                <button id="addBtn" class="btn btn-primary" type="button">新增</button>
            </div>
        </div>
    </form>
</div>
<div class="ln_solid"></div>
<!-- 数据表格 -->
<div class="container">
    <table id="datatable" class="table table-striped table-bordered" style="width:100%">
        <thead>
        <tr>
            <th>所属小区</th>
            <th>地址</th>
            <th>开发商</th>
            <th>物业公司</th>
            <th>物业公司电话</th>
            <th>业委会</th>
            <th>业委会电话</th>
            <th>分摊方式</th>
            <th>状态</th>
            <th>操作</th>
        </tr>
        </thead>
    </table>
</div>

<div id="popWin" style="display: none;">
    <div class="x_panel">
        <div class="x_content">
            <form id="infoForm" class="form-horizontal form-label-left input_mask">
                <div class="col-xs-6 form-group">
                    <label class="control-label col-xs-3">所属小区</label>
                    <div class="col-xs-9">
                        <select name="residentialArea" class="form-control" style="width:100%;"></select>
                    </div>
                </div>
                <div class="col-xs-6 form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-3">地址</label>
                    <div class="col-md-9 col-sm-9 col-xs-9">
                        <input type="text" class="form-control" name="address" placeholder="请输入项目地址......">
                    </div>
                </div>
                <div class="col-xs-6 form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-3">开发商</label>
                    <div class="col-md-9 col-sm-9 col-xs-9">
                        <input type="text" class="form-control" name="developer" placeholder="请输入开发商名称......">
                    </div>
                </div>
                <div class="col-xs-6 form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-3">物业公司</label>
                    <div class="col-md-9 col-sm-9 col-xs-9">
                        <input type="text" class="form-control" name="propertyCompany" placeholder="请输入物业公司名称......">
                    </div>
                </div>
                <div class="col-xs-6 form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-3">物业电话</label>
                    <div class="col-md-9 col-sm-9 col-xs-9">
                        <input type="text" class="form-control" name="propertyCompanyTel" placeholder="请输入物业联系电话......">
                    </div>
                </div>
                <div class="col-xs-6 form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-3">业委会</label>
                    <div class="col-md-9 col-sm-9 col-xs-9">
                        <input type="text" class="form-control" name="owners" placeholder="请输入业委会成员......">
                    </div>
                </div>
                <div class="col-xs-6 form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-3">业委会电话</label>
                    <div class="col-md-9 col-sm-9 col-xs-9">
                        <input type="text" class="form-control" name="ownersTel" placeholder="请输入业委会成员电话......">
                    </div>
                </div>
                <div class="col-xs-6 form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-3">分摊方式</label>
                    <div class="col-md-9 col-sm-9 col-xs-9">
                        <select id="shareTypeSelect" name="shareType" class="form-control" style="width:100%;"></select>
                    </div>
                </div>
                <div class="col-xs-12 form-group">
                    <button id="addItemBtn" class="btn btn-primary btn-sm" style="float: right;" type="button">新增维修项
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>