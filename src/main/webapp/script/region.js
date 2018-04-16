require(['dataTables-bs'],function () {
    layer.config({
        path: '/vendors/layer/',
        offset:"100px"
    })
    var handleRegion;//当前编辑的Region对象
    var table ;//表格对象
    $(document).ready(function() {
        table = $('#regionTable').table({
            ajax:{
                url:"/rest/region/table.action"
            },
            searchForm:'regionSearchForm',
            columns:[
                {'data':"id"},
                {'data':'name'},
                {
                    render:function(data, type, full, meta ){
                        //最后一列操作按钮渲染
                        var btnEidt = '<button type="button" data-handle="edit" data-index="'+full.id+'"   class="btn btn-primary btn-xs">编辑</button>';
                        var btnDel = '<button type="button"  data-handle="del" data-index="'+full.id+'"  class="btn btn-primary btn-xs">删除</button>'
                        return btnEidt +' '+ btnDel
                    }
                }
            ]
        });
        //查询按钮事件
        $("#doSearch").on("click",function () {
            table.ajax.reload();
        })

        //添加按钮时间
        $("#doAdd").on("click",function () {
            popWin(1);
        })

        table.on("draw",function () {
            $("#regionTable td").on("click","button",function () {
                var handle = $(this).attr("data-handle");
                var index = $(this).attr("data-index");
                if(handle == "edit"){
                    selectRegionById(index);
                }else if(handle == "del"){
                    deleteRegion(index)
                }
            })
        })

    } );

    function deleteRegion(id){
        layer.confirm('确定删除数据？', {icon: 3, title:'提示'}, function(index){
            //删除请求
            $.ajax({
                url:'/rest/region/delete.action',
                type:'post',
                data:{id:id},
                dataType:'json',
                success:function (rsp) {
                    if(rsp.success){
                        layer.alert("删除成功",{closeBtn:0})
                        table.ajax.reload();
                    }else {
                        layer.alert(rsp.description,{closeBtn:0})
                    }
                },
                error:function () {
                    layer.alert("服务器内部错误!",{closeBtn:0})
                }
            })
            layer.close(index);
        });

    }

    function selectRegionById(id) {
        $.ajax({
            url:'/rest/region/one.action',
            type:'post',
            data:{id:id},
            dataType:'json',
            success:function (rsp) {
                if(rsp.success){
                    handleRegion = rsp.data;
                    //同步表单
                    // console.log(handleRegion)
                    $("#regionForm input[name='name']").val(handleRegion.name);
                    popWin(2);
                }else {
                    layer.alert(rsp.description,{closeBtn:0})
                }
            },
            error:function () {
                layer.alert("服务器内部错误!",{closeBtn:0})
            }
        })
    }
    /**
     *
     * @param type 1 :添加 2：修改
     */
    function popWin(type) {
        //声明添加和修改页面影响的变量
        var title,url,param;
        if(type==1){
            $("#regionForm")[0].reset();
            title = "添加区域";
            url = '/rest/region/insert.action';
        }else if(type == 2){
            title = "修改区域";
            url = '/rest/region/update.action'
        }else{
            layer.alert("非法弹窗参数!");
            return
        }
        //弹窗
        var win = layer.open({
            type: 1,
            title:title,
            offset:'100px',
            content: $('#popWin'),
            area: ['400px', '250px'],
            btn:['确定','取消'],
            yes:function () {
                var formdata = $("#regionForm").serializeJSON();
                if(type == 1){
                    //添加时，直接序列化form作为参数
                    param = formdata;
                }else {
                    //更新数据时，需要合并formdata到处理对象,避免未显示数据（如id这类）不能传到后台而被赋值为null
                    $.extend(handleRegion,formdata);
                    param = handleRegion
                }
                $.ajax({
                    url:url,
                    type:'post',
                    data:param,
                    dataType:'json',
                    success:function (rsp) {
                        if(rsp.success){
                            layer.alert("处理成功",{closeBtn:0})
                            table.ajax.reload();
                        }else {
                            layer.alert(rsp.description,{closeBtn:0})
                        }
                        layer.close(win);
                    },
                    error:function () {
                        layer.alert("服务器内部错误!",{closeBtn:0});
                        layer.close(win);
                    }
                })
            },
            btn2:function () {
                layer.close(win);
            }
        });
    }
})

