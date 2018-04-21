package com.qth.action;

import com.qth.model.common.CommonRsp;
import com.qth.model.ChargeCriterion;
import com.qth.service.IChargeCriterionService;
import com.qth.model.common.DataTableRspWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "rest/chargeCriterion")
@ResponseBody
public class ChargeCriterionController extends BaseController{

@Autowired
IChargeCriterionService chargeCriterionService;

/**
* 区域表格数据请求
* @param chargeCriterion
* @return
*/
@RequestMapping(path="table")
public DataTableRspWrapper<ChargeCriterion> table(ChargeCriterion chargeCriterion){
    DataTableRspWrapper<ChargeCriterion> rspWrapper = chargeCriterionService.selectDataTable2Rsp(chargeCriterion);
        //赋值绘制时序标识
        rspWrapper.setDraw(chargeCriterion.getDraw());
        return rspWrapper;
        }

        /**
        * 添加区域信息
        * @param chargeCriterion
        * @return
        */
        @RequestMapping(value = "insert")
        public CommonRsp insert(ChargeCriterion chargeCriterion){
        int effect = chargeCriterionService.insertChargeCriterion(chargeCriterion);
        return dbEffect2Rsp(effect);
        }

        @RequestMapping(value = "update")
        public CommonRsp update(ChargeCriterion chargeCriterion){
        int effect = chargeCriterionService.updateChargeCriterion(chargeCriterion);
        return dbEffect2Rsp(effect);
        }

        @RequestMapping(value = "one")
        public CommonRsp selectOne(Integer id){
        ChargeCriterion chargeCriterion = chargeCriterionService.findChargeCriterionById(id);
        return data2Rsp(chargeCriterion);
        }

        @RequestMapping(value = "delete")
        public CommonRsp deleteById(Integer id){
        int effect = chargeCriterionService.deleteChargeCriterionById(id);
        return dbEffect2Rsp(effect);
        }

        }
