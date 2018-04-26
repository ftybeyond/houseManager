package com.qth.action;

import com.qth.model.RepairItem;
import com.qth.model.User;
import com.qth.model.common.CommonRsp;
import com.qth.model.RepairRecord;
import com.qth.service.IRepairItemService;
import com.qth.service.IRepairRecordService;
import com.qth.model.common.DataTableRspWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "rest/repairRecord")
@ResponseBody
public class RepairRecordController extends BaseController{

@Autowired
IRepairRecordService repairRecordService;

@Autowired
IRepairItemService repairItemService;

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
    public CommonRsp insert(RepairRecord repairRecord, HttpSession session){
        //todo
//        User user = (User) session.getAttribute("login_user");
//        repairRecord.setHandler(user.getRealName());
        repairRecord.setStamp(new Date());
        repairRecord.setState(0);
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
        //查询维修记录下的维修项目列表
        List<RepairItem> repairItems = repairItemService.selectByRecord(repairRecord.getId());
        CommonRsp rsp = data2Rsp(repairRecord);
        //添加参数
        Map<String,List<RepairItem>> map = new HashMap<>();
        map.put("repairItems",repairItems);
        rsp.setAttr(map);
        return rsp;
    }

    @RequestMapping(value = "delete")
    public CommonRsp deleteById(Integer id){
        int effect = repairRecordService.deleteRepairRecordById(id);
        return dbEffect2Rsp(effect);
    }

}
