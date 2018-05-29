<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>

    <title>利率管理</title>

    <link rel="stylesheet" type="text/css" href="<%=path%>/vendors/datatable/datatables-bootstrap.min.css"/>
    <script src="<%=path%>/vendors/requireJS/require.js"></script>
    <script type="text/javascript" src="<%=path%>/vendors/requireJS/require-config.js"></script>
    <script type="text/javascript">
        require(["common", "my97date"], function (main) {
            $(function () {
                main.loadDeps(["AlgorithmSwitch.json"], function (data) {
                    var config = {
                        popArea: ['400px', '300px'],
                        domain: {
                            name: 'moneyRate',
                            props: [
                                {name: 'id', type: 'string', showable: false},
                                {name: 'term', type: 'date', showable: true},
                                {name: 'rate', type: 'string', showable: true,render:function (row) {
                                    return row.toFixed(12)
                                }}
                            ]
                        },
                        afterSyncFormData:function(rsp){
                            var rate = rsp.data.rate ;
                            $("#infoForm input[name='rate']").val(rate.toFixed(12))
                        },
                        validateRules:{
                            term:"required",
                            rate:{
                                number:true,
                                required:true
                            }

                        }
                    }
                    var table = main.init(config);
                })
            })
        })
    </script>
</head>
<body>

<div class="container" style="padding-left: 0px;">
    <button id="addBtn" class="btn btn-primary" style="float:right" type="button">新增</button>
</div>

<!-- 数据表格 -->
<div class="container">
    <table id="datatable" class="table table-striped table-bordered" style="width:100%">
        <thead>
        <tr>
            <th>利率生效日期</th>
            <th>利率</th>
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
                    <label class="control-label col-md-3 col-sm-3 col-xs-3">生效日期</label>
                    <div class="col-md-9 col-sm-9 col-xs-9">
                        <input type="text" class="form-control" id="moneny_rate_term" name="term" onClick="WdatePicker({})" placeholder="请选择利率生效日期......" onclick="">
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-3">利率</label>
                    <div class="col-md-9 col-sm-9 col-xs-9">
                        <input type="text" class="form-control" name="rate" placeholder="请输入利率......">
                    </div>
                </div>

            </form>
        </div>
    </div>
</div>
</body>
</html>