;(function () {
    jQuery.fn.mySelect = function (table, settings, backfun) {
        var baseOpt = {
            language: "zh-CN",
            placeholder: '--请选择--'
        }
        var _this = $(this)
        $.ajax({
            url: '/rest/select/' + table + '.action',
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
                if (backfun && data) {
                    backfun(data);
                }
            }
        })
    }

    jQuery.fn.genSelectWithAll = function (table, settings, backfun) {
        var baseOpt = {
            language: "zh-CN"
        }
        var _this = $(this)
        $.ajax({
            url: '/rest/select/' + table + '.action',
            dataType: 'json',
            type: 'post',
            success: function (data) {
                data.unshift({"id": "", "text": "全部"});
                baseOpt.data = data;
                if (settings) {
                    $.extend(true, settings, baseOpt);
                    _this.html("");
                    _this.select2(settings);
                } else {
                    _this.html("");
                    _this.select2(baseOpt);
                }
                if (backfun && data) {
                    backfun(data);
                }
            }
        })
    }

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
            language: "zh-CN"
        }
        var _this = $(this)
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
                if (backfun && data) {
                    backfun(data);
                }
            }
        })
    }
    jQuery.fn.loadResidentialAreaSelect = function (street, settings, backfun, withall) {
        var baseOpt = {
            language: "zh-CN"
        }
        var _this = $(this)
        $.ajax({
            url: '/rest/selectResidentialAreaByStreet/' + street + '.action',
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
                if (backfun && data) {
                    backfun(data);
                }
            }
        })
    }
    jQuery.fn.loadBuildingSelect = function (residentialArea, settings, backfun, withall) {
        var baseOpt = {
            language: "zh-CN"
        }
        var _this = $(this)
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
                if (backfun && data) {
                    backfun(data);
                }
            }
        })
    }
    jQuery.fn.loadBuildingSelectWithAll = function (residentialArea, settings, backfun) {
        var baseOpt = {
            language: "zh-CN"
        }
        var _this = $(this)
        $.ajax({
            url: '/rest/selectBuildingByResidentialArea/' + residentialArea + '.action',
            dataType: 'json',
            type: 'post',
            success: function (data) {
                data.unshift({"id": "", "text": "全部"});
                baseOpt.data = data;
                if (settings) {
                    $.extend(true, settings, baseOpt);
                    _this.html("");
                    _this.select2(settings);
                } else {
                    _this.html("");
                    _this.select2(baseOpt);
                }
                if (backfun && data) {
                    backfun(data);
                }
            }
        })
    }
    jQuery.fn.loadUnitSelect = function (building, settings, backfun, withall) {
        var baseOpt = {
            language: "zh-CN"
        }
        var _this = $(this)
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
                if (backfun && data) {
                    backfun(data);
                }
            }
        })
    }
    jQuery.fn.loadFloorSelect = function (unit, settings, backfun, withall) {
        var baseOpt = {
            language: "zh-CN"
        }
        var _this = $(this)
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
                if (backfun && data) {
                    backfun(data);
                }
            }
        })
    }
    jQuery.fn.loadHouseNameSelect = function (unit, floor, settings, backfun, withall) {
        var baseOpt = {
            language: "zh-CN"
        }
        var _this = $(this)
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
                if (backfun && data) {
                    backfun(data);
                }
            }
        })
    }
    jQuery.fn.loadConfigSelect = function (type, settings) {
        var baseOpt = {
            language: "zh-CN"
        }
        var _this = $(this)
        $.ajax({
            url: '/rest/selectConfigSelect/' + type + '.action',
            dataType: 'json',
            type: 'post',
            success: function (data) {
                baseOpt.data = data;
                if (settings) {
                    $.extend(true, settings, baseOpt);
                    _this.html("");
                    return _this.select2(settings);
                } else {
                    _this.html("");
                    return _this.select2(baseOpt);
                }
            }
        })
    }
})
(jQuery)