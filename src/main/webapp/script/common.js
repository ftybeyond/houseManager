define(["dataTables-bs"], function () {
    layer.config({
        path: '/vendors/layer/',
        offset: "100px"
    });

    var baseConfig = {
        baseUrl: '/rest/',//请求前置URL
        popArea: ['500px', '550px'],
        domain: {
            name: '',//实体名称,
            props: []//实体属性集合 {data:'name',editable:true,searchable:true,tableShow:true,formType:'datetime|select|string|number|file|date|',showType:'',dateFormat:'yyyy-MM-dd HH:ss:mm',selectKey:'id',selectValue:'name',numberFormate:''}
        },//实体信息
        multiSelect: false,
        deleteable: true,
        editable: true,
        suffix: '.action',//请求后缀
        tableId: 'datatable',//数据表格ID
        searchForm: 'searchForm',//搜索表单ID
        infoFrom: 'infoForm',//添加和修改表单ID
        searchBtn: 'searchBtn',//查询按钮
        addBtn: 'addBtn',//添加按钮
        customBtns: [],//表格上自定义按钮{label:'自定义按钮',callback:function(index){//行数据id}}
        beforeSaveOrUpdate: null,//保存或更新前回调方法，提供ajax 即将提交的param参数,用于表单校验和参数补充
        afterSyncFormData: null, //selectById后同步表单后回调方法，此回调用于不能自动同步到表单项的值的组件,传入当前查询回的对象
        beforePopWin:null
    };
    var handleObj;//当前编辑的Region对象
    var table;//表格对象
    var init = function (config) {
        $.extend(true, baseConfig, config);
        table = $("#" + baseConfig.tableId).table({
            ajax: {
                url: getUrl("table")
            },
            searchForm: baseConfig.searchForm,
            columns: generateColumns()
        });
        //查询按钮事件
        $("#" + baseConfig.searchBtn).on("click", function () {
            table.ajax.reload();
        })

        //添加按钮时间
        $("#" + baseConfig.addBtn).on("click", function () {
            if (typeof(addBtnBefore) == "function") {
                if (addBtnBefore()) {
                    popWin(1);
                }
            } else {
                popWin(1);
            }
            if (typeof(addBtnAfter) == "function") {
                addBtnAfter();
            }
        })

        table.on("draw", function () {
            $("#" + baseConfig.tableId + " td").on("click", "button", function () {
                var handle = $(this).attr("data-handle");
                var index = $(this).attr("data-index");
                var handleIndex = $(this).attr("data-handle-index");

                if (handle == "edit") {
                    selectOneById(index);
                } else if (handle == "del") {
                    deleteOneById(index)
                } else if (handle == "custom") {
                    $.each(baseConfig.customBtns, function (customIndex, item) {
                        if (customIndex == handleIndex) {
                            item.callback(index);
                        }
                    })
                }
            })
        })

        /**
         * restUrl 具体操作部分
         * @param handle add one delete selectById
         */
        function getUrl(handle) {
            return baseConfig.baseUrl + baseConfig.domain.name + "/" + handle + baseConfig.suffix;
        }

        function deleteOneById(id) {
            layer.confirm('确定删除数据？', {icon: 3, title: '提示'}, function (index) {
                //删除请求
                $.ajax({
                    url: getUrl("delete"),
                    type: 'post',
                    data: {id: id},
                    dataType: 'json',
                    success: function (rsp) {
                        if (rsp.success) {
                            layer.alert("删除成功", {closeBtn: 0})
                            table.ajax.reload();
                        } else {
                            layer.alert(rsp.description, {closeBtn: 0})
                        }
                    },
                    error: function () {
                        layer.alert("服务器内部错误!", {closeBtn: 0})
                    }
                })
                layer.close(index);
            });

        }

        /**
         * 根据handleObj更新表单信息
         */
        function obj2Form(rsp) {
            for (key in handleObj) {
                if ($("#" + baseConfig.infoFrom + " input[name='" + key + "']").size() > 0) {
                    $("#" + baseConfig.infoFrom + " input[name='" + key + "']").val(handleObj[key]);
                } else if ($("#" + baseConfig.infoFrom + " select[name='" + key + "']").size() > 0) {
                    $("#" + baseConfig.infoFrom + " select[name='" + key + "']").val(handleObj[key]);
                    $("#" + baseConfig.infoFrom + " select[name='" + key + "']").change();
                } else {
                    //..陆续补充
                }
            }
            if (typeof (baseConfig.afterSyncFormData)=='function') {
                //表单同步数据后的回调，用于补充不支持自动填充的表单项,传递ajax返回对象
                baseConfig.afterSyncFormData(rsp)
            }
            if (typeof(obj2FormBackfun) == "function") {
                obj2FormBackfun(baseConfig, handleObj);
            }
        }

        /**
         * 根据domain配置生成datatables columns属性
         */
        function generateColumns() {
            var columns = new Array();
            if (baseConfig.multiSelect) {
                //添加复选框
                columns.push({
                    render: function (data, type, full, meta) {
                        var checkbox = '<input type="checkbox" data-index="' + full.id + '" name="selectRow">删除</input>'
                        return checkbox
                    }
                });
            }
            $.each(baseConfig.domain.props, function (index, item) {
                if (item.showable) {
                    item.data = item.name;
                    columns.push(item)
                }
            })

            if (baseConfig.editable || baseConfig.deleteable || baseConfig.customBtns.length > 0) {
                columns.push({
                    render: function (data, type, full, meta) {
                        var btns = '';
                        //最后一列操作按钮渲染
                        if (baseConfig.editable) {
                            var btnEidt = ' <button type="button" data-handle="edit" data-index="' + full.id + '"   class="btn btn-primary btn-xs">编辑</button> ';
                            btns += btnEidt;
                        }
                        if (baseConfig.deleteable) {
                            var btnDel = ' <button type="button"  data-handle="del" data-index="' + full.id + '"  class="btn btn-primary btn-xs">删除</button> ';
                            btns += btnDel;
                        }
                        if (baseConfig.customBtns.length > 0) {
                            $.each(baseConfig.customBtns, function (index, item) {
                                var btnCustom = ' <button type="button"  data-handle="custom" data-handle-index="' + index + '" data-index="' + full.id + '"  class="btn btn-primary btn-xs">' + item.label + '</button> ';
                                btns += btnCustom;
                            })
                        }
                        return btns;
                    }
                });
            }
            return columns;
        }

        function selectOneById(id) {
            $.ajax({
                url: getUrl("one"),
                type: 'post',
                data: {id: id},
                dataType: 'json',
                success: function (rsp) {
                    if (rsp.success) {
                        handleObj = rsp.data;
                        //同步表单
                        obj2Form(rsp)
                        popWin(2);
                        if (typeof(editRecordAfter) == "function") {
                            editRecordAfter();
                        }
                    } else {
                        layer.alert(rsp.description, {closeBtn: 0})
                    }
                },
                error: function () {
                    layer.alert("服务器内部错误!", {closeBtn: 0})
                }
            })
        }

        /**
         *
         * @param type 1 :添加 2：修改
         */
        function popWin(type) {
            //声明添加和修改页面影响的变量
            var title, url, param;
            if (type == 1) {
                $("#" + baseConfig.infoFrom)[0].reset();
                $("#" + baseConfig.infoFrom +" select").select2("val","all")
                title = "添加";
                url = getUrl("insert");
            } else if (type == 2) {
                title = "修改";
                url = getUrl("update")
            } else {
                layer.alert("非法弹窗参数!");
                return
            }
            if(typeof (baseConfig.beforePopWin) == 'function' ){
                baseConfig.beforePopWin(type)
            }
            //弹窗
            var win = layer.open({
                type: 1,
                title: title,
                offset: '20px',
                content: $('#popWin'),
                area: baseConfig.popArea,
                btn: ['确定', '取消'],
                yes: function () {
                    var formdata = $("#" + baseConfig.infoFrom).serializeJSON();
                    if (type == 1) {
                        //添加时，直接序列化form作为参数
                        param = formdata;
                    } else {
                        //更新数据时，需要合并formdata到处理对象,避免未显示数据（如id这类）不能传到后台而被赋值为null
                        $.extend(handleObj, formdata);
                        param = handleObj
                    }
                    if (typeof (baseConfig.beforeSaveOrUpdate)=='function') {
                        baseConfig.beforeSaveOrUpdate(param,type)
                    }
                    $.ajax({
                        url: url,
                        type: 'post',
                        data: param,
                        dataType: 'json',
                        success: function (rsp) {
                            if (rsp.success) {
                                layer.alert("处理成功", {closeBtn: 0})
                                table.ajax.reload();
                            } else {
                                layer.alert(rsp.description, {closeBtn: 0})
                            }
                            layer.close(win);
                        },
                        error: function () {
                            layer.alert("服务器内部错误!", {closeBtn: 0});
                            layer.close(win);
                        }
                    })
                },
                btn2: function () {
                    layer.close(win);
                }
            });
        }
        return table;
    }

    var getHandleObj = function () {
        return handleObj;
    }

    var loadedDatas = {};
    var taskComplated = 0;
    var waitTask;
    var loadingMask;
    /**
     * 加载依赖远程数据，加载dictionary目录以*.json结尾、 通用下拉列表数据 "表名"、 自主调用数据/rest/xxx
     * @param deps eg("CompanyNature","region","/rest/getStreetByRegion.action")
     * @callback 依赖数据加载完毕回调
     */
    var loadDeps = function (deps, callback) {
        loadingMask = layer.msg('拼命加载中......', {shade: [0.8, '#393D49'], time: 3000, icon: 16});
        if (deps && (deps instanceof Array)) {
            var url;
            $.each(deps, function (index, item) {
                if (item.indexOf(".json") > 0) {
                    url = "/dictionary/" + item;
                } else if (item.indexOf("/") > 0) {
                    url = item;
                } else {
                    url = '/rest/select/' + item + '.action';
                }
                $.ajax({
                    url: url,
                    dataType: 'json',
                    type: 'post',
                    success: function (data) {
                        loadedDatas[item] = data;
                        taskComplated++;
                    }
                })
            })

            waitTask = setInterval(waitForSynch, 500)
        }

        function waitForSynch() {
            console.log("等待异步任务" + deps.length + "个，当前完成" + taskComplated + "个");
            if (taskComplated == deps.length) {
                taskComplated = 0;
                if (callback) {
                    callback(loadedDatas)
                }
                clearInterval(waitTask);
                layer.close(loadingMask)
            }
        }
    }

    var findArrayValue = function (id, arr) {
        var result;
        $.each(arr, function (index, item) {
            if (item.id == id) {
                result = item;
                return false;
            }
        })
        return result
    }

    return {
        init: init,
        loadDeps: loadDeps,
        getHandleObj: getHandleObj,
        findArrayValue: findArrayValue
    }
})