define(["zTree"],function(){
    var houseTree;
    var base = {
        async:{
            autoParam:["id","level","name","checked"],
            enable:true,
            url:"/rest/tree/selectHouseTree.action"
        },
        check: {
            enable: true
        },
        callback: {

        },
        showSelectedDiv:'selectedNodes'
    };

    var genTree = function(container,setting){

        if (setting) {
            $.extend(true,base,setting);
        }
        if (typeof (base.onCheck) == "function") {
            base.callback.onCheck = setting.onCheck
        } else {
            base.callback.onCheck = showOnDiv
        }
        houseTree = $.fn.zTree.init($("#" + container), base);
        return houseTree;
    }

    var showOnDiv = function() {
        var allCheckedNodes = loadAllChecksNodes();
        $("#"+base.showSelectedDiv).empty();
        //显示到页面
        for(key in allCheckedNodes){
            $("#"+base.showSelectedDiv).append('<li>'+getNamePath(allCheckedNodes[key])+'</li>')
        }
    }

    var getAllSelectPath = function(){
        var allCheckedNodes = loadAllChecksNodes();
        var result = '';
        //显示到页面
        for(key in allCheckedNodes){
            result += getNamePath(allCheckedNodes[key]);
            result +=","
        }
        if(result.length > 0){
            result = result.substr(0,result.lastIndexOf(","));
        }
        return result;
    }

    var loadAllChecksNodes = function (){
        var checkedNodes = treeObj.getNodesByParam("checked",true);//获取所有已选中的节点
        var result = new Object()//结果对象
        $.each(checkedNodes,function(index,item){
            if(item.check_Child_State == 2 || item.check_Child_State == -1){
                //当前是一个全选节点,判断其父节点是否也是全选节点
                var parentNode = item.getParentNode()
                if(parentNode == null){
                    //当前是根节点被选中，无需向父级查找
                    result[item.tId] = item
                }else{
                    //向父节点递归，直到祖先节点是半选状态
                    var finalNode = getAllCheckedAncestors(item);
                    result[finalNode.tId] = finalNode;
                }
            }
        });
        return result;
    }

    /**
     * 查找指定上节点分支上，最高级的全选节点
     * @param treeNode
     */
    var getAllCheckedAncestors = function(treeNode) {
        var parentNode = treeNode.getParentNode();
        if(parentNode != null && parentNode.check_Child_State==2){
            return getAllCheckedAncestors(parentNode)
        }else{
            return treeNode;
        }
    }

    var getNamePath = function(treeNode){
        var result = "";
        var pathNodes = treeNode.getPath();
        $.each(pathNodes,function(index,item){
            result +=item.name;
            if(index !=(pathNodes.length-1)){
                result += "-"
            }
        });
        return result;
    }

    var getIdPath = function(treeNode){
        var result = "";
        var pathNodes = treeNode.getPath();
        $.each(pathNodes,function(index,item){
            if (item.level == 3) {
                result +=item.name;
            } else {
                result +=item.id;
            }
            if(index !=(pathNodes.length-1)){
                result += "-"
            }
        });
        return result;
    }

    var getPathParam = function(){
        var addCheckedNodes = loadAllChecksNodes();
        var param = "";
        for (key in addCheckedNodes) {
            param += getIdPath(addCheckedNodes[key]);
            param += ","
        }
        if (param.length == 0) {
            return null;
        }
        param = param.substring(0, param.lastIndexOf(","));
        return param;
    }

    return {
        genTree:genTree,
        getPathParam:getPathParam,
        getIdPath:getIdPath,
        getNamePath:getNamePath,
        loadAllChecksNodes:loadAllChecksNodes,
        getAllSelectPath:getAllSelectPath
    }
})