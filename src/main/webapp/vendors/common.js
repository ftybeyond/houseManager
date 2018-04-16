define(["dataTables-bs"],function () {
    layer.config({
        path: '/vendors/layer/',
        offset:"100px"
    });

    var baseConfig = {
        baseUrl:'/rest/',//请求前置URL
        domain:{
            name:'',//实体名称,
            props:[]//实体属性集合 {name:'name',editable:true,searchable:true,tableShow:true,formType:'datetime|select|string|number|file|date|',showType:'',dateFormat:'yyyy-MM-dd HH:ss:mm',selectKey:'id',selectValue:'name',numberFormate:''}
        },//实体信息
        multiSelect:false,
        deleteable:true,
        editable:true,
        suffix:'.action',//请求后缀
        tableId:'datatable',//数据表格ID
        searchForm:'searchForm',//搜索表单ID
        infoFrom:'infoForm',//添加和修改表单ID
        searchBtn:'searchBtn',//查询按钮
        addBtn:'addBtn',//添加按钮
        beforeSaveOrUpdate:null,//保存或更新前回调方法，提供ajax 即将提交的param参数,用于表单校验和参数补充
        afterSyncFormData:null //selectById后同步表单后回调方法，此回调用于不能自动同步到表单项的值的组件,传入当前查询回的对象
    };
    var handleObj;//当前编辑的Region对象
    var table ;//表格对象
    var init = function(config){
        $.extend(true,baseConfig,config);
        table = $(baseConfig.tableId).table({
            ajax:{
                url:getUrl("table")
            },
            searchForm:baseConfig.searchForm,
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
        $("#"+baseConfig.searchBtn).on("click",function () {
            table.ajax.reload();
        })

        //添加按钮时间
        $("#" + baseConfig.addBtn).on("click",function () {
            popWin(1);
        })

        table.on("draw",function () {
            $("#"+baseConfig.tableId+" td").on("click","button",function () {
                var handle = $(this).attr("data-handle");
                var index = $(this).attr("data-index");
                if(handle == "edit"){
                    selectRegionById(index);
                }else if(handle == "del"){
                    deleteRegion(index)
                }
            })
        })

        /**
         * restUrl 具体操作部分
         * @param handle add one delete selectById
         */
        function getUrl(handle) {
            return baseConfig.baseUrl +baseConfig.domain.name + "/" + handle+baseConfig.suffix;
        }

        function deleteRegion(id){
            layer.confirm('确定删除数据？', {icon: 3, title:'提示'}, function(index){
                //删除请求
                $.ajax({
                    url:getUrl("delete"),
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

        /**
         * 根据handleObj更新表单信息
         */
        function obj2Form(){
            for (key in handleObj){
                $("#"+baseConfig.infoFrom+" *[name='"+key+"']").val(handleObj.key);
            }
            if(baseConfig.afterSyncFormData){
                baseConfig.afterSyncFormData(handleObj)
            }
        }

        function selectRegionById(id) {
            $.ajax({
                url:getUrl("one"),
                type:'post',
                data:{id:id},
                dataType:'json',
                success:function (rsp) {
                    if(rsp.success){
                        handleObj = rsp.data;
                        //同步表单
                        obj2Form()
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
                $("#" + baseConfig.infoFrom)[0].reset();
                title = "添加区域";
                url = getUrl("insert");
            }else if(type == 2){
                title = "修改区域";
                url = getUrl("update")
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
                    var formdata = $("#" + baseConfig.infoFrom).serializeJSON();
                    if(type == 1){
                        //添加时，直接序列化form作为参数
                        param = formdata;
                    }else {
                        //更新数据时，需要合并formdata到处理对象,避免未显示数据（如id这类）不能传到后台而被赋值为null
                        $.extend(handleObj,formdata);
                        param = handleObj
                    }
                    if(baseConfig.beforeSaveOrUpdate){
                        baseConfig.beforeSaveOrUpdate(param)
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
    }


    return {
        init:init,
        handleObj:handleObj,
        tableObj:table
    }
})