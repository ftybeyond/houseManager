package com.qth.action;

import com.qth.model.common.CommonRsp;
import com.qth.model.MoneyRate;
import com.qth.service.IMoneyRateService;
import com.qth.model.common.DataTableRspWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "rest/moneyRate")
@ResponseBody
public class MoneyRateController extends BaseController{

@Autowired
IMoneyRateService moneyRateService;

/**
* 区域表格数据请求
* @param moneyRate
* @return
*/
@RequestMapping(path="table")
public DataTableRspWrapper<MoneyRate> table(MoneyRate moneyRate){
    DataTableRspWrapper<MoneyRate> rspWrapper = moneyRateService.selectDataTable2Rsp(moneyRate);
        //赋值绘制时序标识
        rspWrapper.setDraw(moneyRate.getDraw());
        return rspWrapper;
        }

        /**
        * 添加区域信息
        * @param moneyRate
        * @return
        */
        @RequestMapping(value = "insert")
        public CommonRsp insert(MoneyRate moneyRate){
        int effect = moneyRateService.insertMoneyRate(moneyRate);
        return dbEffect2Rsp(effect);
        }

        @RequestMapping(value = "update")
        public CommonRsp update(MoneyRate moneyRate){
        int effect = moneyRateService.updateMoneyRate(moneyRate);
        return dbEffect2Rsp(effect);
        }

        @RequestMapping(value = "one")
        public CommonRsp selectOne(Integer id){
        MoneyRate moneyRate = moneyRateService.findMoneyRateById(id);
        return data2Rsp(moneyRate);
        }

        @RequestMapping(value = "delete")
        public CommonRsp deleteById(Integer id){
        int effect = moneyRateService.deleteMoneyRateById(id);
        return dbEffect2Rsp(effect);
        }

        }
