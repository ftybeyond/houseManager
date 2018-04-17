;(function(){
    jQuery.fn.mySelect = function(table,settings){
        var baseOpt = {
            language: "zh-CN"
        }
        var _this = $(this)
        $.ajax({
            url: '/rest/select/'+table+'.action',
            dataType: 'json',
            type:'post',
            success:function (data) {
                baseOpt.data = data;
                if (settings) {
                    $.extend(true, settings, baseOpt);
                    return _this.select2(settings);
                }else{
                    return _this.select2(baseOpt);
                }
            }
        })
    }
})(jQuery)