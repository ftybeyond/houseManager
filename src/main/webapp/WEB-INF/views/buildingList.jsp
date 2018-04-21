<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>

    <title>楼栋管理</title>

    <link rel="stylesheet" type="text/css" href="<%=path%>/vendors/datatable/datatables-bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="<%=path%>/vendors/select2/select2.min.css"/>
    <script src="<%=path%>/vendors/requireJS/require.js"></script>
    <script type="text/javascript" src="<%=path%>/vendors/requireJS/require-config.js"></script>
    <script type="text/javascript">
        require(["common", "mySelect", "select2"], function (main) {
            $(function () {
                //var select = $("#natureSelect").mySelect('CompanyNature.json');
                main.loadDeps(["hasornot.json"], function (data) {
                    $("#hasElevatorSelect").select2({data: data["hasornot.json"]});
                    $("#hasUndergroundSelect").select2({data: data["hasornot.json"]});
                    $("#regionSelect").mySelect('region', null, function (data) {
                        if (data && data.length > 0 && data[0].id) {
                            $("#streetSelect").loadStreetSelect(data[0].id, null, function (data) {
                                if (data && data.length > 0 && data[0].id) {
                                    $("#residentialAreaSelect").loadResidentialAreaSelect(data[0].id);
                                }
                            });
                        }
                    });
                    $("#regionSelect").change(function () {
                        $("#streetSelect").loadStreetSelect(this.value, null, function (data) {
                            if (data && data.length > 0 && data[0].id) {
                                $("#residentialAreaSelect").loadResidentialAreaSelect(data[0].id);
                            }
                        });
                    });
                    $("#streetSelect").change(function () {
                        $("#residentialAreaSelect").loadResidentialAreaSelect(this.value);
                    });

                    $("#queryResidentialAreaSelect").loadResidentialAreaSelect(0);

                    var config = {
                        popArea: ['400px', '500px'],
                        domain: {
                            name: 'building',
                            props: [
                                {name: 'id', type: 'string', showable: false},
                                {name: 'name', type: 'string', showable: true},
                                {name: 'residentialArea', type: 'string', showable: false},
                                {name: 'residentialAreaName', type: 'string', showable: true},
                                {name: 'units', type: 'string', showable: true},
                                {name: 'hasElevator',type: 'string',showable: false},
                                {name: 'hasElevatorName',type: 'string',showable: true},
                                {name: 'hasUnderground',type: 'string',showable: false},
                                {name: 'hasUndergroundName',type: 'string',showable: true}
                            ]
                        }
                    }
                    var table = main.init(config);
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
            <label class="control-label col-md-3 col-sm-3 col-xs-3">小区</label>
            <div class="col-md-9 col-sm-9 col-xs-9">
                <select id="queryResidentialAreaSelect" name="residentialArea" class="form-control"
                        style="width:100%;"></select>
            </div>
        </div>
        <div class="col-xs-5 form-group">
            <label class="control-label col-xs-3">楼栋编码</label>
            <div class="col-xs-9">
                <input type="text" class="form-control" placeholder="要查询的楼栋编码" name="name"/>
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
            <th>楼栋编码</th>
            <th>小区</th>
            <th>单元数</th>
            <th>有无电梯</th>
            <th>有无地下</th>
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
                    <label class="control-label col-md-3 col-sm-3 col-xs-3">所属区域</label>
                    <div class="col-md-9 col-sm-9 col-xs-9">
                        <select id="regionSelect" name="region" class="form-control" style="width:100%;"></select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-3">所属街道</label>
                    <div class="col-md-9 col-sm-9 col-xs-9">
                        <select id="streetSelect" name="street" class="form-control" style="width:100%;"></select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-3">小区</label>
                    <div class="col-md-9 col-sm-9 col-xs-9">
                        <select id="residentialAreaSelect" name="residentialArea" class="form-control"
                                style="width:100%;"></select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-3">楼栋编码</label>
                    <div class="col-md-9 col-sm-9 col-xs-9">
                        <input type="text" class="form-control" name="name" placeholder="请输入楼栋编码......">
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-3">单元数</label>
                    <div class="col-md-9 col-sm-9 col-xs-9">
                        <input type="text" class="form-control" name="units" placeholder="请输入单元数......">
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-3">有无电梯</label>
                    <div class="col-md-9 col-sm-9 col-xs-9">
                        <select id="hasElevatorSelect" name="hasElevator" class="form-control" style="width:100%;"></select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-3">有无地下室</label>
                    <div class="col-md-9 col-sm-9 col-xs-9">
                        <select id="hasUndergroundSelect" name="hasUnderground" class="form-control"
                                style="width:100%;"></select>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>