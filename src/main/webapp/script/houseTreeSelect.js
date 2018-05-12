define(["zTree","bootstrap"],function(tree){
    var houseTree;
    var base = {
        async:{
            autoParam:["id","level","name","checked"],
            enable:true,
            url:"/rest/tree/selectHouseTree.action"
        },
        callback: {

        }
    };

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
})