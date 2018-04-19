;(function(){
    jQuery.fn.mySelect = function(domain,settings){
        var baseOpt = {
            language: "zh-CN"
        }
        var _this = $(this)
        if (settings.dictionary) {
            $.ajax({
                url: '/dictionary/' + domain ,
                dataType: 'json',
                type: 'post',
                success: function (data) {
                    baseOpt.data = data;
                    if (settings) {
                        $.extend(true, settings, baseOpt);
                        return _this.select2(settings);
                    } else {
                        return _this.select2(baseOpt);
                    }
                }
            })
        }else {
            $.ajax({
                url: '/rest/select/' + domain + '.action',
                dataType: 'json',
                type: 'post',
                success: function (data) {
                    baseOpt.data = data;
                    if (settings) {
                        $.extend(true, settings, baseOpt);
                        return _this.select2(settings);
                    } else {
                        return _this.select2(baseOpt);
                    }
                }
            })
        }
    }
})(jQuery)