package com.qth.action;

import com.qth.model.common.CommonRsp;
import com.qth.model.RepairRecord;
import com.qth.service.IRepairRecordService;
import com.qth.model.common.DataTableRspWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "rest/repairRecord")
@ResponseBody
public class RepairRecordController extends BaseController{

@Autowired
IRepairRecordService repairRecordService;

/**
* 区域表格数据请求
* @param repairRecord
* @return
*/
@RequestMapping(path="table")
public DataTableRspWrapper<RepairRecord> table(RepairRecord repairRecord){

    DataTableRspWrapper<RepairRecord> rspWrapper = repairRecordService.selectDataTable2Rsp(repairRecord);
        //赋值绘制时序标识
        rspWrapper.setDraw(repairRecord.getDraw());
        return rspWrapper;
    }

    /**
    * 添加区域信息
    * @param repairRecord
    * @return
    */
    @RequestMapping(value = "insert")
    public CommonRsp insert(RepairRecord repairRecord){
        int effect = repairRecordService.insertRepairRecord(repairRecord);
        return dbEffect2Rsp(effect);
    }

    @RequestMapping(value = "update")
    public CommonRsp update(RepairRecord repairRecord){
        int effect = repairRecordService.updateRepairRecord(repairRecord);
        return dbEffect2Rsp(effect);
    }

    @RequestMapping(value = "one")
    public CommonRsp selectOne(Integer id){
        RepairRecord repairRecord = repairRecordService.findRepairRecordById(id);
        return data2Rsp(repairRecord);
    }

    @RequestMapping(value = "delete")
    public CommonRsp deleteById(Integer id){
        int effect = repairRecordService.deleteRepairRecordById(id);
        return dbEffect2Rsp(effect);
    }

}
