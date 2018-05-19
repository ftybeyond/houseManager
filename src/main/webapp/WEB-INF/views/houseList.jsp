<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>

    <title>房屋管理</title>

    <link rel="stylesheet" type="text/css" href="<%=path%>/vendors/datatable/datatables-bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="<%=path%>/vendors/select2/select2.min.css"/>
    <script src="<%=path%>/vendors/requireJS/require.js"></script>
    <script type="text/javascript" src="<%=path%>/vendors/requireJS/require-config.js"></script>
    <script type="text/javascript">
        require(["common", "mySelect"], function (main,select,treeSelect) {
            $(function () {
                $("#infoForm select").mySelect2({data:[]});
                $("#searchForm select").mySelect2({data:[]});
                main.loadDeps(["residential_area","HasOrNot.json", "hasornotWithall.json", "HouseNature.json", "HouseType.json"], function (data) {
                    $("#queryResidentialAreaSelect").mySelect2({data:data["residential_area"]});
                    $("#queryResidentialAreaSelect").change(function () {
                        $("#queryBuildingSelect").loadBuildingSelect(this.value, null, null, true);
                    });
                    $("#queryBuildingSelect").change(function () {
                        $("#queryUnitSelect").loadUnitSelect(this.value, null, null, true);
                    });
                    $("#queryUnitSelect").change(function () {
                        $("#queryFloorSelect").loadFloorSelect(this.value, null, null, true);
//                        $("#queryNameSelect").loadHouseNameSelect($("#queryUnitSelect").val(), $("#queryFloorSelect").val(), null, null, true);
                    });
                    $("#queryFloorSelect").change(function () {
                        $("#queryNameSelect").loadHouseNameSelect($("#queryUnitSelect").val(), $("#queryFloorSelect").val(), null, null, true);
                    });


                    $("#hasElevatorSelect").mySelect2({data: data["HasOrNot.json"]});
                    $("#natureSelect").mySelect2({data: data["HouseNature.json"]});
                    $("#typeSelect").mySelect2({data: data["HouseType.json"]});

                    $("#residentialAreaSelect").mySelect2({data:data["residential_area"]});

                    $("#residentialAreaSelect").change(function () {
                        $("#buildingSelect").loadBuildingSelect(this.value, null, function (data) {
                            if(main.getHandleObj()){
                                $("#buildingSelect").val(main.getHandleObj()["building"]).change();
                            }
                            refreshCode();
                        });
                    });
                    $("#buildingSelect").change(function () {
                        $("#unitSelect").loadUnitSelect(this.value, null, function (data) {
                            if(main.getHandleObj()){
                                $("#unitSelect").val(main.getHandleObj()["unit"]).change();
                            }
                            refreshCode();
                        });
                    });
                    $("#unitSelect").change(function () {
                        refreshCode();
                    });
                    $("#floor").change(function () {
                        refreshCode();
                    });
                    $("#name").change(function () {
                        refreshCode();
                    });

                    var config = {
                        popArea: ['400px', '550px'],
                        domain: {
                            name: 'house',
                            props: [
                                {name: 'id', type: 'string', showable: false},
                                {name: 'code', type: 'string', showable: true},
                                {name: 'residentialArea', type: 'string', showable: false},
                                {name: 'residentialAreaName', type: 'string', showable: true},
                                {name: 'building', type: 'string', showable: false},
                                {name: 'buildingName', type: 'string', showable: true},
                                {name: 'unit', type: 'string', showable: false},
                                {name: 'unitName', type: 'string', showable: true},
                                {name: 'floor', type: 'string', showable: true},
                                {name: 'name', type: 'string', showable: true},
                                {name: 'area', type: 'string', showable: true},
                                {name: 'unitPrice', type: 'string', showable: true},
                                {name: 'hasElevator', type: 'string', showable: true,render:function (row) {
                                    var dic = main.findArrayValue(row,data["HasOrNot.json"])
                                    return dic&&dic.text?dic.text:"";
                                }},
                                {name: 'nature', type: 'string', showable: true,render:function (row) {
                                    var dic = main.findArrayValue(row,data["HouseNature.json"])
                                    return dic&&dic.text?dic.text:"";
                                }},
                                {name: 'type', type: 'string', showable: true,render:function (row) {
                                    var dic = main.findArrayValue(row,data["HouseType.json"])
                                    return dic&&dic.text?dic.text:"";
                                }}
                            ]
                        }
                    }
                    var table = main.init(config);
                })
            })
        })

        function addBtnAfter() {
            refreshCode();
        }

        function editRecordAfter() {
            refreshCode();
        }

        function refreshCode() {
            try {
                var residentialArea = $("#residentialAreaSelect").val()?$("#residentialAreaSelect").val():'';
                var building = $("#buildingSelect").val()?$("#buildingSelect").val():'';
                var unit = $("#unitSelect").val()?$("#unitSelect").val():'';
                var floor = $("#floor").val();
                var name = $("#name").val();
                $("#code").val(residentialArea + building + unit + floor + name);
                $("#codeName").val(residentialArea + building + unit + floor + name);
            } catch (e) {
                console.log(e)
            }
        }

    </script>
</head>
<body>
<!--查询表单-->
<div class="container" style="padding-left: 0px;padding-top:10px;">
    <form id="searchForm" class="form-horizontal" role="form">
        <div class="row clearfix">
            <div class="col-xs-4 search-form-group">
                <label class="control-label col-xs-3">产业代码</label>
                <div class="col-xs-9">
                    <input type="text" class="form-control" placeholder="要查询的产业代码" name="code"/>
                </div>
            </div>
            <div class="col-xs-4 search-form-group">
                <label class="control-label col-md-3 col-sm-3 col-xs-3">小区</label>
                <div class="col-md-9 col-sm-9 col-xs-9">
                    <select id="queryResidentialAreaSelect" name="residentialArea" class="form-control"
                            style="width:100%;"></select>
                </div>
            </div>
            <div class="col-xs-4 search-form-group">
                <label class="control-label col-xs-3">楼栋</label>
                <div class="col-md-9 col-sm-9 col-xs-9">
                    <select id="queryBuildingSelect" name="building" class="form-control"
                            style="width:100%;"></select>
                </div>
            </div>
        </div>

        <div class="row clearfix">
            <div class="col-xs-4 search-form-group">
                <label class="control-label col-xs-3">单元</label>
                <div class="col-md-9 col-sm-9 col-xs-9">
                    <select id="queryUnitSelect" name="unit" class="form-control"
                            style="width:100%;"></select>
                </div>
            </div>
            <div class="col-xs-4 search-form-group">
                <label class="control-label col-xs-3">楼层</label>
                <div class="col-md-9 col-sm-9 col-xs-9">
                    <select id="queryFloorSelect" name="floor" class="form-control"
                            style="width:100%;"></select>
                </div>
            </div>
            <div class="col-xs-4 search-form-group">
                <label class="control-label col-xs-3">房号</label>
                <div class="col-md-9 col-sm-9 col-xs-9">
                    <select id="queryNameSelect" name="name" class="form-control"
                            style="width:100%;"></select>
                </div>
            </div>
        </div>

        <div class="col-xs-2 search-form-group" style="float: right">
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
            <th>产业代码</th>
            <th>小区</th>
            <th>楼栋</th>
            <th>单元</th>
            <th>楼层</th>
            <th>房号</th>
            <th>面积</th>
            <th>单价</th>
            <th>电梯</th>
            <th>房屋性质</th>
            <th>住宅类型</th>
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
                    <label class="control-label col-md-3 col-sm-3 col-xs-3">产业代码</label>
                    <div class="col-md-9 col-sm-9 col-xs-9">
                        <input type="text" class="form-control" id="codeName" name="codeName" placeholder="自动生成"
                               disabled="ture">
                        <input type="hidden" id="code" name="code">
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
                    <label class="control-label col-md-3 col-sm-3 col-xs-3">楼栋</label>
                    <div class="col-md-9 col-sm-9 col-xs-9">
                        <select id="buildingSelect" name="building" class="form-control"
                                style="width:100%;"></select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-3">单元编号</label>
                    <div class="col-md-9 col-sm-9 col-xs-9">
                        <select id="unitSelect" id="unit" name="unit" class="form-control"
                                style="width:100%;"></select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-3">层号</label>
                    <div class="col-md-9 col-sm-9 col-xs-9">
                        <input type="text" class="form-control" id="floor" name="floor" placeholder="请输入层号......">
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-3">房号</label>
                    <div class="col-md-9 col-sm-9 col-xs-9">
                        <input type="text" class="form-control" id="name" name="name" placeholder="请输入房号......">
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-3">面积</label>
                    <div class="col-md-9 col-sm-9 col-xs-9">
                        <input type="text" class="form-control" name="area" placeholder="请输入面积......">
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-3">单价</label>
                    <div class="col-md-9 col-sm-9 col-xs-9">
                        <input type="text" class="form-control" name="unitPrice" placeholder="请输入单价......">
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-3">电梯</label>
                    <div class="col-md-9 col-sm-9 col-xs-9">
                        <select id="hasElevatorSelect" name="hasElevator" class="form-control"
                                style="width:100%;"></select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-3">房屋性质</label>
                    <div class="col-md-9 col-sm-9 col-xs-9">
                        <select id="natureSelect" name="nature" class="form-control"
                                style="width:100%;"></select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-3">住宅类型</label>
                    <div class="col-md-9 col-sm-9 col-xs-9">
                        <select id="typeSelect" name="type" class="form-control"
                                style="width:100%;"></select>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>