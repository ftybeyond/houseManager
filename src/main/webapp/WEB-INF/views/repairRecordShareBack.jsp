`<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>

    <title>维修基金返还</title>

    <link rel="stylesheet" type="text/css" href="<%=path%>/vendors/datatable/datatables-bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="<%=path%>/vendors/select2/select2.min.css"/>
    <!--[if lt IE 8 ]><script src="<%=path%>/vendors/json2.min.js"></script><![endif]-->
    <script src="<%=path%>/vendors/requireJS/require.js"></script>
    <script type="text/javascript" src="<%=path%>/vendors/requireJS/require-config.js"></script>
    <script type="text/javascript">
        require(["common", "mySelect"], function (main) {
            layer.config({
                offset:"100px"
            })
            $(function () {
                main.loadDeps(["residential_area", "ShareType.json"], function (data) {
                    $("select[name='residentialArea']").mySelect2({data: data["residential_area"]})
                    var config = {
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
                                {name: 'developer', type: 'string', showable: true},
                                {name: 'shareType', type: 'string', showable: true,render: function(row){
                                    var dic = main.findArrayValue(row, data["ShareType.json"])
                                    return dic && dic.text ? dic.text : "";
                                 }},
                                {name: 'stamp', type: 'string', showable: true}
                            ]
                        },
                        editable:function(data, type, full, meta){
                            return false;
                        },
                        deleteable:function(data, type, full, meta){
                            return false;
                        },
                        customBtns:[
                            {label:'回退', callback:function(rowId,row){
                                var loadingMask = layer.msg('拼命计算中......', {shade: [0.8, '#393D49'], time: 0, icon: 16});
                                $.post("/rest/share/shareBackInfo.action",{seq:row.shareSeq},null,"json").done(function(data){
//                                    console.log(data.data.cost)
                                    content = '<p>回退费用：'+data.data.cost+'</p>';
                                    content +=  '<p>回退户数：'+data.data.houseCount+'</p>';
                                    layer.confirm(content, {icon: 3, title:'提示'}, function(index){
                                        loadingMask = layer.msg('拼命计算中......', {shade: [0.8, '#393D49'], time: 0, icon: 16});
                                        $.post("/rest/share/doShareBack.action",{record:row.id},null,"json").done(function(data){
                                            layer.alert(data.description);
                                            table.ajax.reload();
                                        }).fail(function (xhr) {
                                            layer.alert("服务器内部错误!"+xhr.statusText)
                                        })

                                    });
                                }).fail(function (xhr) {
                                    layer.alert("服务器内部错误!"+xhr.statusText)
                                })
                            }}
                        ]
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
                                        '<td width="80px">开发商:</td>'+
                                        '<td>'+rowData.developer+'</td>'+
                                        '</tr>'+
                                        '<tr>'+
                                        '<td>地址:</td>'+
                                        '<td>'+rowData.address+'</td>'+
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
            <label class="control-label col-xs-3">所属小区</label>
            <div class="col-xs-9">
                <select name="residentialArea" class="form-control" style="width:100%;"></select>
            </div>
        </div>
        <input type="hidden" name="state" value="1"/>
        <div class="col-xs-2 form-group">
            <div class="col-xs-6">
                <button id="searchBtn" class="btn btn-primary" type="button">查询</button>
            </div>
        </div>
    </form>
</div>
<div class="ln_solid"></div>
<!-- 数据表格 -->
<div class="container">
    <table id="datatable" class="table table-striped table-bordered" style="width:100%;">
        <thead>
        <tr>
            <th></th>
            <th>所属小区</th>
            <th>开发商</th>
            <th>分摊方式</th>
            <th>登记日期</th>
            <th>操作</th>
        </tr>
        </thead>
    </table>
</div>

</body>
</html>