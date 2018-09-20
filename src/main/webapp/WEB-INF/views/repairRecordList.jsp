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
    <link rel="stylesheet" type="text/css" href="<%=path%>/vendors/select2/select2.min.css"/>
    <!--[if lt IE 8 ]><script src="<%=path%>/vendors/json2.min.js"></script><![endif]-->
    <script src="<%=path%>/vendors/requireJS/require.js"></script>
    <script type="text/javascript" src="<%=path%>/vendors/requireJS/require-config.js"></script>
    <script type="text/javascript">
        require(["common", "mySelect","my97date"], function (main) {
            $(function () {
                main.loadDeps(["residential_area", "ShareType.json", "RepairState.json"], function (data) {
                    $("select[name='residentialArea']").mySelect2({data: data["residential_area"]})
                    $("select[name='state']").mySelect2({data: data["RepairState.json"]})
                    $("#shareTypeSelect").mySelect2({data: data["ShareType.json"]})
                    var config = {
                        popArea: ['720px', '550px'],
                        scrollX:true,
                        domain: {
                            name: 'repairRecord',
                            props: [
                                {name: 'id', type: 'string', showable: false},
                                {
                                    className: 'details-control',
                                    orderable:false,
                                    data:null,
                                    showable: true,
                                    defaultContent: ''
                                },
                                {
                                    name: 'residentialArea',
                                    type: 'string',
                                    showable: true,
                                    render: function (row, type, full, meta) {
                                        var dic = main.findArrayValue(row, data["residential_area"])
                                        return dic && dic.text ? dic.text : "";
                                    }
                                },
                                {name: 'owners', type: 'string', showable: true},
                                {name: 'ownersTel', type: 'string', showable: true},
                                {name: 'workTime', type: 'string', showable: true},
                                {name: 'moneySum', type: 'string', showable: true},
                                {name: 'shareType', type: 'string', showable: true,render: function(row){
                                    var dic = main.findArrayValue(row, data["ShareType.json"])
                                    return dic && dic.text ? dic.text : "";
                                }},
                                {name: 'stamp', type: 'string', showable: true},
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
                        editable:function(data, type, full, meta){
                          if(full.state==0){
                              return true
                          }else{
                              return false;
                          }
                        },
                        deleteable:function(data, type, full, meta){
                            if(full.state==0){
                                return true
                            }else{
                                return false;
                            }
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
                            var errMsg;
                            $("#infoForm div.addItemDiv").each(function (index) {
                                var remark = $(this).find("textarea[name=remark]").val();
                                if(!remark){
                                    errMsg = "事项描述不能为空";
                                    return false;
                                }
                                var price = $(this).find("input[name=price]").val();
                                if(!price || isNaN(price)){
                                    errMsg = "事项价格必须为有效数字";
                                    return false;
                                }
                                repairItems.push({remark:remark,price:price})
                            })
                            if(errMsg){
                                return errMsg
                            }
                            if (repairItems.length>0) {
                                var supplement = JSON.stringify(repairItems);
                                param.supplement = supplement
                            }
                        },
                        validateRules:{
                            residentialArea:'required',
                            developer:'required',
                            shareType:'required',
                            owners:'required',
                            propertyCompany:'required'
                        }
                    }
                    var table = main.init(config);
                    table.on('click', 'td.details-control', function () {
                        var tr = $(this).closest('tr');
                        var row = table.row( tr );
                        var rowData = row.data();

                        if ( row.child.isShown() ) {
                            // This row is already open - close it
                            row.child.hide();
                            tr.removeClass('shown');
                        }
                        else {
                            if(tr.hasClass("dataLoaded")){
                                row.child.show();
                            }else{
                                $.ajax({
                                    url:"/rest/repairItem/selectByRecord.action",
                                    type:"POST",
                                    data:{record:rowData.id},
                                    dataType:"json"
                                }).done(function(data){
                                    var repairItems = "";
                                    $.each(data.data,function(index,item){
                                        repairItems += ('<tr><td>事项说明：</td><td>'+item.remark+'&nbsp;&nbsp;&nbsp;&nbsp;费用：'+item.price+'</td></tr>');
                                    })
                                    var showInfo =  '<table class="table inner">'+
                                        '<tr>'+
                                        '<td width="100px">项目地址:</td>'+
                                        '<td>'+(rowData.address?rowData.address:'')+'</td>'+
                                        '</tr>'+
                                        '<tr>'+
                                        '<td>申请单位1:</td>'+
                                        '<td>'+(rowData.org1?rowData.org1:'')+'</td>'+
                                        '</tr>'+
                                        '<tr>'+
                                        '<td>申请单位1电话:</td>'+
                                        '<td>'+(rowData.tel1?rowData.tel1:'')+'</td>'+
                                        '</tr>'+
                                        '<tr>'+
                                        '<td>申请单位2:</td>'+
                                        '<td>'+(rowData.org2?rowData.org2:'')+'</td>'+
                                        '</tr>'+
                                        '<tr>'+
                                        '<td>申请单位2电话:</td>'+
                                        '<td>'+(rowData.tel2?rowData.tel2:'')+'</td>'+
                                        '</tr>'+
                                        '<tr>'+
                                        '<td>施工单位:</td>'+
                                        '<td>'+(rowData.worker?rowData.worker:'')+'</td>'+
                                        '</tr>'+
                                        '<tr>'+
                                        '<td>施工单位电话:</td>'+
                                        '<td>'+(rowData.workerTel?rowData.workerTel:'')+'</td>'+
                                        '</tr>'+
                                        '<tr>'+
                                        '<td colspan="2">维修项目(合计费用:'+rowData.moneySum+')</td>'+
                                        '<td></td>'+
                                        '</tr>'+
                                        repairItems+
                                        '</table>';
                                    row.child(showInfo).show();
                                    tr.addClass('shown');
                                    tr.addClass("dataLoaded")
                                }).fail(function(xhr){
                                    layer.alert(xhr.statusText, {closeBtn: 0});
                                })
                            }
                        }
                    } );
                    $(".x_content").on("click",".delItemBtn",function () {
                        $(this).parents(".addItemDiv").remove();
                    })
                    $("#addItemBtn").on("click", function () {
                        //添加维修事项
                        $("#infoForm").append(genItem())
                    })

                    function genItem(item){
                        var div = '<div class="addItemDiv row clearfix">' +
                            '               <div class="col-xs-6 row" style="margin-left:3px">' +
                            '                    <label class="control-label col-xs-4">维修项目</label>' +
                            '                    <div class="col-xs-8">' +
                            '                        <textarea type="text" class="form-control" name="remark" placeholder="具体维修内容...">'+(item?item.remark:'')+'</textarea>' +
                            '                    </div>' +
                            '                </div>' +
                            '                <div class="col-xs-6 form-group">' +
                            '                    <label class="control-label col-xs-4">金额（元）</label>' +
                            '                    <div class="col-xs-6">' +
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
<div class="container">
    <form id="searchForm" class="form-horizontal" role="form">
        <div class="col-xs-5 form-group">
            <label class="control-label col-xs-3">项目名称</label>
            <div class="col-xs-9">
                <select name="residentialArea" class="form-control" style="width:100%;"></select>
            </div>
        </div>
        <div class="col-xs-5 form-group">
            <label class="control-label col-xs-3">状态</label>
            <div class="col-xs-9">
                <select name="state" class="form-control" style="width:100%;"></select>
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
    <table id="datatable" class="table table-striped table-bordered" style="width:1200px;">
        <thead>
        <tr>
            <th></th>
            <th>项目名称</th>
            <th>业主委员会</th>
            <th>业主委员会电话</th>
            <th>申请施工时间</th>
            <th>合计金额</th>
            <th>分摊方式</th>
            <th>登记日期</th>
            <th>状态</th>
            <th>操作</th>
        </tr>
        </thead>
    </table>
</div>

<div id="popWin" style="display: none;">
    <div class="x_panel">
        <div class="x_content" style="width: 95%">
            <form id="infoForm" class="form-horizontal form-label-left input_mask">
                <div class="row clearfix">
                    <div class="col-xs-6 form-group">
                        <label class="control-label col-xs-4">项目名称</label>
                        <div class="col-xs-8">
                            <select name="residentialArea" class="form-control" style="width:100%;"></select>
                        </div>
                    </div>
                    <div class="col-xs-6 form-group">
                        <label class="control-label col-xs-4">项目地址</label>
                        <div class="col-xs-8">
                            <input type="text" class="form-control" name="address" placeholder="请输入项目地址......">
                        </div>
                    </div>
                </div>

                <div class="row clearfix">
                    <div class="col-xs-6 form-group">
                        <label class="control-label col-xs-4">申请单位1</label>
                        <div class="col-xs-8">
                            <input type="text" class="form-control" name="org1" placeholder="请输入申请单位1名称......">
                        </div>
                    </div>
                    <div class="col-xs-6 form-group">
                        <label class="control-label col-xs-4">申请单位1电话</label>
                        <div class="col-xs-8">
                            <input type="text" class="form-control" name="tel1" placeholder="请输入申请单位1电话......">
                        </div>
                    </div>
                </div>

                <div class="row clearfix">
                    <div class="col-xs-6 form-group">
                        <label class="control-label col-xs-4">申请单位2</label>
                        <div class="col-xs-8">
                            <input type="text" class="form-control" name="org2" placeholder="请输入申请单位2名称......">
                        </div>
                    </div>
                    <div class="col-xs-6 form-group">
                        <label class="control-label col-xs-4">申请单位2电话</label>
                        <div class="col-xs-8">
                            <input type="text" class="form-control" name="tel2" placeholder="请输入申请单位2电话......">
                        </div>
                    </div>
                </div>

                <div class="row clearfix">
                    <div class="col-xs-6 form-group">
                        <label class="control-label col-xs-4">施工单位</label>
                        <div class="col-xs-8">
                            <input type="text" class="form-control" name="worker" placeholder="请输入施工单位名称......">
                        </div>
                    </div>
                    <div class="col-xs-6 form-group">
                        <label class="control-label col-xs-4">施工单位电话</label>
                        <div class="col-xs-8">
                            <input type="text" class="form-control" name="workerTel" placeholder="请输入施工单位电话......">
                        </div>
                    </div>
                </div>

                <div class="row clearfix">
                    <div class="col-xs-6 form-group">
                        <label class="control-label col-xs-4">业主委员会</label>
                        <div class="col-xs-8">
                            <input type="text" class="form-control" name="owners" placeholder="请输入业委会成员......">
                        </div>
                    </div>
                    <div class="col-xs-6 form-group">
                        <label class="control-label col-xs-4">业主委员会电话</label>
                        <div class="col-xs-8">
                            <input type="text" class="form-control" name="ownersTel" placeholder="请输入业委会成员电话......">
                        </div>
                    </div>
                </div>


                <div class="row clearfix">
                    <div class="col-xs-6 form-group">
                        <label class="control-label col-xs-4">申请施工日期</label>
                        <div class="col-xs-8">
                            <input type="text" class="form-control" placeholder="申请施工日期" onClick="WdatePicker()" name="workTime"/>
                        </div>
                    </div>
                    <div class="col-xs-6 form-group">
                        <label class="control-label col-xs-4">分摊方式</label>
                        <div class="col-xs-8">
                            <select id="shareTypeSelect" name="shareType" class="form-control" style="width:100%;"></select>
                        </div>
                    </div>
                </div>

                <div class="row clearfix">
                    <div class="col-xs-12 form-group">
                        <button id="addItemBtn" class="btn btn-primary btn-sm" style="float: right;" type="button">新增维修事宜
                        </button>
                    </div>
                </div>

            </form>
        </div>
    </div>
</div>
</body>
</html>