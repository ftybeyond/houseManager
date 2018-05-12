;(function () {

    jQuery.fn.mySelect2 = function (settings) {
        var baseOpt = {
            language: "zh-CN",
            placeholder: '--请选择--'
        }
        $.extend(true, settings, baseOpt);
        var select = $(this).select2(settings);
        select.select2("val", "all")
        return select;
    }

    jQuery.fn.loadStreetSelect = function (region, settings, backfun) {
        var baseOpt = {
            language: "zh-CN",
            placeholder: '--请选择--'
        }
        var _this = $(this);
        if(!region){
            baseOpt.data = [];
            _this.select2(baseOpt);
        }
        $.ajax({
            url: '/rest/selectStreetByRegion/' + region + '.action',
            dataType: 'json',
            type: 'post',
            success: function (data) {
                baseOpt.data = data;
                if (settings) {
                    $.extend(true, settings, baseOpt);
                    _this.html("");
                    _this.select2(settings);
                } else {
                    _this.html("");
                    _this.select2(baseOpt);
                }
                _this.select2("val", "all");
                if (backfun && data) {
                    backfun(data);
                }
            }
        })
    }
    jQuery.fn.loadBuildingSelect = function (residentialArea, settings, backfun, withall) {
        var baseOpt = {
            language: "zh-CN",
            placeholder: '--请选择--'
        }
        var _this = $(this);
        if(!residentialArea){
            baseOpt.data = [];
            _this.select2(baseOpt);
        }
        $.ajax({
            url: '/rest/selectBuildingByResidentialArea/' + residentialArea + '.action',
            dataType: 'json',
            type: 'post',
            success: function (data) {
                if (withall != null && withall == true) {
                    data.unshift({"id": "", "text": "全部"});
                }
                baseOpt.data = data;
                if (settings) {
                    $.extend(true, settings, baseOpt);
                    _this.html("");
                    _this.select2(settings);
                } else {
                    _this.html("");
                    _this.select2(baseOpt);
                }
                _this.select2("val", "all");
                if (backfun && data) {
                    backfun(data);
                }
            }
        })
    }
    jQuery.fn.loadUnitSelect = function (building, settings, backfun, withall) {
        var baseOpt = {
            language: "zh-CN",
            placeholder: '--请选择--'
        }
        var _this = $(this);
        if(!building){
            baseOpt.data = [];
            _this.select2(baseOpt);
        }
        $.ajax({
            url: '/rest/selectUnitByBuilding/' + building + '.action',
            dataType: 'json',
            type: 'post',
            success: function (data) {
                if (withall != null && withall == true) {
                    data.unshift({"id": "", "text": "全部"});
                }
                baseOpt.data = data;
                if (settings) {
                    $.extend(true, settings, baseOpt);
                    _this.html("");
                    _this.select2(settings);
                } else {
                    _this.html("");
                    _this.select2(baseOpt);
                }
                _this.select2("val", "all");
                if (backfun && data) {
                    backfun(data);
                }
            }
        })
    }
    jQuery.fn.loadFloorSelect = function (unit, settings, backfun, withall) {
        var baseOpt = {
            language: "zh-CN",
            placeholder: '--请选择--'
        }
        var _this = $(this);
        if(!unit){
            baseOpt.data = [];
            _this.select2(baseOpt);
        }
        $.ajax({
            url: '/rest/selectFloorByUnit/' + unit + '.action',
            dataType: 'json',
            type: 'post',
            success: function (data) {
                if (withall != null && withall == true) {
                    data.unshift({"id": "", "text": "全部"});
                }
                baseOpt.data = data;
                if (settings) {
                    $.extend(true, settings, baseOpt);
                    _this.html("");
                    _this.select2(settings);
                } else {
                    _this.html("");
                    _this.select2(baseOpt);
                }
                _this.select2("val", "all");
                if (backfun && data) {
                    backfun(data);
                }
            }
        })
    }
    jQuery.fn.loadHouseNameSelect = function (unit, floor, settings, backfun, withall) {
        var baseOpt = {
            language: "zh-CN",
            placeholder: '--请选择--'
        }
        var _this = $(this);
        if(!unit||!floor){
            baseOpt.data = [];
            _this.select2(baseOpt);
        }
        $.ajax({
            url: '/rest/selectHouseNameByUnitFloor/get.action',
            dataType: 'json',
            data: {unit: unit, floor: floor},
            type: 'post',
            success: function (data) {
                if (withall != null && withall == true) {
                    data.unshift({"id": "", "text": "全部"});
                }
                // console.log(data);
                baseOpt.data = data;
                if (settings) {
                    $.extend(true, settings, baseOpt);
                    _this.html("");
                    _this.select2(settings);
                } else {
                    _this.html("");
                    _this.select2(baseOpt);
                }
                _this.select2("val", "all");
                if (backfun && data) {
                    backfun(data);
                }
            }
        })
    }
})
(jQuery)