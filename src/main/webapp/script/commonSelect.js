;(function(){
    jQuery.fn.mySelect = function(table,settings,backfun){
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
                if (backfun && data) {
                    backfun(data);
                }
                if (settings) {
                    $.extend(true, settings, baseOpt);
                    _this.html("");
                    return _this.select2(settings);
                }else{
                    _this.html("");
                    return _this.select2(baseOpt);
                }
            }
        })
    }
    jQuery.fn.loadStreetSelect = function(region,settings,backfun){
        var baseOpt = {
            language: "zh-CN"
        }
        var _this = $(this)
        $.ajax({
            url: '/rest/selectStreetByRegion/'+region+'.action',
            dataType: 'json',
            type:'post',
            success:function (data) {
                baseOpt.data = data;
                if (backfun && data) {
                    backfun(data);
                }
                if (settings) {
                    $.extend(true, settings, baseOpt);
                    _this.html("");
                    return _this.select2(settings);
                }else{
                    _this.html("");
                    return _this.select2(baseOpt);
                }
            }
        })
    }
    jQuery.fn.loadResidentialAreaSelect = function(street,settings,backfun){
        var baseOpt = {
            language: "zh-CN"
        }
        var _this = $(this)
        $.ajax({
            url: '/rest/selectResidentialAreaByStreet/'+street+'.action',
            dataType: 'json',
            type:'post',
            success:function (data) {
                if (street == 0) {
                    data.unshift({"id" : "", "text" : "全部"});
                }
                baseOpt.data = data;
                if (backfun && data) {
                    backfun(data);
                }
                if (settings) {
                    $.extend(true, settings, baseOpt);
                    _this.html("");
                    return _this.select2(settings);
                }else{
                    _this.html("");
                    return _this.select2(baseOpt);
                }
            }
        })
    }
    jQuery.fn.loadConfigSelect = function(type,settings){
        var baseOpt = {
            language: "zh-CN"
        }
        var _this = $(this)
        $.ajax({
            url: '/rest/selectConfigSelect/'+type+'.action',
            dataType: 'json',
            type:'post',
            success:function (data) {
                baseOpt.data = data;
                if (settings) {
                    $.extend(true, settings, baseOpt);
                    _this.html("");
                    return _this.select2(settings);
                }else{
                    _this.html("");
                    return _this.select2(baseOpt);
                }
            }
        })
    }
})(jQuery)