<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>

    <title>单元管理</title>

    <link rel="stylesheet" type="text/css" href="<%=path%>/vendors/datatable/datatables-bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="<%=path%>/vendors/select2/select2.min.css"/>
    <script src="<%=path%>/vendors/requireJS/require.js"></script>
    <script type="text/javascript" src="<%=path%>/vendors/requireJS/require-config.js"></script>
    <script type="text/javascript">
        require(["common", "mySelect"], function (main) {
            $(function () {
                //var select = $("#natureSelect").mySelect('CompanyNature.json');
//                $("#queryResidentialAreaSelect").loadResidentialAreaSelect(0);
//                $("#residentialAreaSelect").mySelect('residential_area', null, function (data) {
//                    if (data && data.length > 0 && data[0].id) {
//                        $("#buildingSelect").loadBuildingSelect(data[0].id);
//                    }
//                });
//                $("#residentialAreaSelect").change(function () {
//                    $("#buildingSelect").loadBuildingSelect(this.value);
//                });

                var config = {
                    popArea: ['400px', '500px'],
                    domain: {
                        name: 'unit',
                        props: [
                            {name: 'id', type: 'string', showable: false},
                            {name: 'residentialArea', type: 'string', showable: false},
                            {name: 'residentialAreaName', type: 'string', showable: true},
                            {name: 'building', type: 'string', showable: false},
                            {name: 'buildingName', type: 'string', showable: true},
                            {name: 'name', type: 'string', showable: true},
                            {name: 'totalFloor',type: 'string',showable: true},
                            {name: 'housePerFloor',type: 'string',showable: true}
                        ]
                    }
                }
                var table = main.init(config);
            })
        })
    </script>
</head>
<body>
<!--查询表单-->
<div class="container" style="padding-left: 0px;">
    <form id="searchForm" class="form-horizontal" role="form">
        <div class="col-xs-5 form-group">
            <label class="control-label col-md-3 col-sm-3 col-xs-3">小区</label>
            <div class="col-md-9 col-sm-9 col-xs-9">
                <select id="queryResidentialAreaSelect" name="residentialArea" class="form-control"
                        style="width:100%;"></select>
            </div>
        </div>
        <div class="col-xs-5 form-group">
            <label class="control-label col-xs-3">楼栋</label>
            <div class="col-md-9 col-sm-9 col-xs-9">
                <select id="queryBuildingSelect" name="building" class="form-control"
                        style="width:100%;"></select>
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
            <th>小区</th>
            <th>楼栋</th>
            <th>单元</th>
            <th>层数</th>
            <th>每层户数</th>
            <th>操作</th>
        </tr>
        </thead>
    </table>
</div>

<div id="popWin" style="display: none;">
    <div class="x_panel">
        <div class="x_content">
            <form id="infoForm" class="form-horizontal form-label-left input_mask">
                <div class="form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-3">小区</label>
                    <div class="col-md-9 col-sm-9 col-xs-9">
                        <select id="residentialAreaSelect" name="residentialArea" class="form-control"
                                style="width:100%;"></select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-3">楼栋</label>
                    <div class="col-md-9 col-sm-9 col-xs-9">
                        <select id="buildingSelect" name="building" class="form-control"
                                style="width:100%;"></select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-3">单元编号</label>
                    <div class="col-md-9 col-sm-9 col-xs-9">
                        <input type="text" class="form-control" name="name" placeholder="请输入单元编号......">
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-3">楼层数</label>
                    <div class="col-md-9 col-sm-9 col-xs-9">
                        <input type="text" class="form-control" name="totalFloor" placeholder="请输入楼层数......">
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-3">每层户数</label>
                    <div class="col-md-9 col-sm-9 col-xs-9">
                        <input type="text" class="form-control" name="roomsPerFloor" placeholder="请输入每层户数......">
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>