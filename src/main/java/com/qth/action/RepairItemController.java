package com.qth.action;

import com.qth.model.common.CommonRsp;
import com.qth.model.RepairItem;
import com.qth.service.IRepairItemService;
import com.qth.model.common.DataTableRspWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "rest/repairItem")
@ResponseBody
public class RepairItemController extends BaseController{

@Autowired
IRepairItemService repairItemService;

/**
* 区域表格数据请求
* @param repairItem
* @return
*/
@RequestMapping(path="table")
public DataTableRspWrapper<RepairItem> table(RepairItem repairItem){

    DataTableRspWrapper<RepairItem> rspWrapper = repairItemService.selectDataTable2Rsp(repairItem);
        //赋值绘制时序标识
        rspWrapper.setDraw(repairItem.getDraw());
        return rspWrapper;
    }

    /**
    * 添加区域信息
    * @param repairItem
    * @return
    */
    @RequestMapping(value = "insert")
    public CommonRsp insert(RepairItem repairItem){
        int effect = repairItemService.insertRepairItem(repairItem);
        return dbEffect2Rsp(effect);
    }

    @RequestMapping(value = "update")
    public CommonRsp update(RepairItem repairItem){
        int effect = repairItemService.updateRepairItem(repairItem);
        return dbEffect2Rsp(effect);
    }

    @RequestMapping(value = "one")
    public CommonRsp selectOne(Integer id){
        RepairItem repairItem = repairItemService.findRepairItemById(id);
        return data2Rsp(repairItem);
    }

    @RequestMapping(value = "delete")
    public CommonRsp deleteById(Integer id){
        int effect = repairItemService.deleteRepairItemById(id);
        return dbEffect2Rsp(effect);
    }

}
