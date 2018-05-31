<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>

    <title>角色管理</title>

    <link rel="stylesheet" type="text/css" href="<%=path%>/vendors/datatable/datatables-bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="<%=path%>/vendors/select2/select2.min.css"/>
    <!--[if lt IE 8 ]><script src="<%=path%>/vendors/json2.min.js"></script><![endif]-->
    <script src="<%=path%>/vendors/requireJS/require.js"></script>
    <script type="text/javascript" src="<%=path%>/vendors/requireJS/require-config.js"></script>
    <script type="text/javascript">
        require(["common","select2-cn"],function (main) {
            main.loadDeps(["authority"],function (data) {
                data["authority"].unshift({id:"0",text:"全部权限"});
                $("#authoritySelect").select2({data:data["authority"]});
                $(function(){
                    var config = {
                        popArea:['400px','350px'],
                        domain:{
                            name:'role',
                            props:[
                                {name:'id',type:'string',showable:false},
                                {name:'name',type:'string',showable:true},
                                {name:'remark',type:'string',showable:true}
                            ]
                        },
                        beforeSaveOrUpdate:function(param){
                            param.authority = JSON.stringify($("#authoritySelect").val());
                        },
                        afterSyncFormData:function (rsp) {
                            $("#authoritySelect").val(eval(rsp.data.authority)).trigger("change");
                        },
                        validateRules:{
                            name:{
                                required:true,
                                maxlength:20
                            }
                        }
                    };
                    var table = main.init(config);
                })
            })
        })
    </script>
</head>
<body>
<!--查询表单-->
<div class="container" >
    <button id="addBtn" class="btn btn-primary" type="button" style="float: right">新增</button>
</div>
<!-- 数据表格 -->
<div class="container">
    <table id="datatable" class="table table-striped table-bordered" style="width:100%">
        <thead>
        <tr>
            <th>角色名称</th>
            <th>角色描述</th>
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
                    <label class="control-label col-md-3 col-sm-3 col-xs-3">角色名称</label>
                    <div class="col-md-9 col-sm-9 col-xs-9">
                        <input type="text" class="form-control" name="name" placeholder="请输入角色名称......">
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-3">角色描述</label>
                    <div class="col-md-9 col-sm-9 col-xs-9">
                        <input type="text" class="form-control" name="remark" placeholder="请输入角色描述......">
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-3">角色权限</label>
                    <div class="col-md-9 col-sm-9 col-xs-9">
                        <select id="authoritySelect" name="authority" multiple="multiple" class="form-control" style="width:100%;"></select>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>