package com.qth.action;

import com.qth.model.common.CommonRsp;
import com.qth.model.ChargeBill;
import com.qth.service.IChargeBillService;
import com.qth.model.common.DataTableRspWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "rest/chargeBill")
@ResponseBody
public class ChargeBillController extends BaseController{

@Autowired
IChargeBillService chargeBillService;

/**
* 区域表格数据请求
* @param chargeBill
* @return
*/
@RequestMapping(path="table")
public DataTableRspWrapper<ChargeBill> table(ChargeBill chargeBill){

    DataTableRspWrapper<ChargeBill> rspWrapper = chargeBillService.selectDataTable2Rsp(chargeBill);
        //赋值绘制时序标识
        rspWrapper.setDraw(chargeBill.getDraw());
        return rspWrapper;
    }

    /**
    * 添加区域信息
    * @param chargeBill
    * @return
    */
    @RequestMapping(value = "insert")
    public CommonRsp insert(ChargeBill chargeBill){
        int effect = chargeBillService.insertChargeBill(chargeBill);
        return dbEffect2Rsp(effect);
    }

    @RequestMapping(value = "update")
    public CommonRsp update(ChargeBill chargeBill){
        int effect = chargeBillService.updateChargeBill(chargeBill);
        return dbEffect2Rsp(effect);
    }

    @RequestMapping(value = "one")
    public CommonRsp selectOne(Integer id){
        ChargeBill chargeBill = chargeBillService.findChargeBillById(id);
        return data2Rsp(chargeBill);
    }

    @RequestMapping(value = "delete")
    public CommonRsp deleteById(Integer id){
        int effect = chargeBillService.deleteChargeBillById(id);
        return dbEffect2Rsp(effect);
    }

}
