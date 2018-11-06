package com.qth.action;

import com.qth.model.ChargeBill;
import com.qth.model.common.CommonRsp;
import com.qth.model.common.DataTableRspWrapper;
import com.qth.model.dto.ChargeBillForm;
import com.qth.model.dto.HouseTreeModel;
import com.qth.service.IChargeBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping(value = "rest/chargeBill")
@ResponseBody
public class ChargeBillController extends BaseController {

    @Autowired
    IChargeBillService chargeBillService;

    /**
     * 添加区域信息
     *
     * @param chargeBill
     * @return
     */
    @RequestMapping(value = "insert")
    public CommonRsp insert(ChargeBill chargeBill) {
        int effect = chargeBillService.insertChargeBill(chargeBill);
        return dbEffect2Rsp(effect);
    }

    @RequestMapping(value = "update")
    public CommonRsp update(ChargeBill chargeBill) {
        int effect = chargeBillService.updateChargeBill(chargeBill);
        return dbEffect2Rsp(effect);
    }

    @RequestMapping(value = "one")
    public CommonRsp selectOne(Integer id) {
        ChargeBill chargeBill = chargeBillService.findChargeBillById(id);
        return data2Rsp(chargeBill);
    }

    @RequestMapping(value = "delete")
    public CommonRsp deleteById(Integer id) {
        int effect = chargeBillService.deleteChargeBillById(id);
        return dbEffect2Rsp(effect);
    }

    @RequestMapping(value = "selectByTreeNode")
    public DataTableRspWrapper<ChargeBill> treeTable(HouseTreeModel model) {
        return chargeBillService.treeTable(model);
    }

    @RequestMapping(value = "back")
    public CommonRsp back(ChargeBill chargeBill, HttpSession session) {
        //收缴单状态置-1
        int effect = chargeBillService.updateState(chargeBill,-1,getHandler(session));
        return dbEffect2Rsp(effect);
    }

    @RequestMapping(value = "account")
    public CommonRsp account(ChargeBill chargeBill,HttpSession session) {
        //如果收缴单状态为0则置2 1则置3
        int effect = 0;
        //accountLog
        if (chargeBill.getState()==0) {
            effect =chargeBillService.updateState(chargeBill,1,getHandler(session));
        }
        if(chargeBill.getState()==2){
            effect = chargeBillService.updateState(chargeBill,3,getHandler(session));
        }
        return dbEffect2Rsp(effect);

    }

    @RequestMapping(value = "table")
    public DataTableRspWrapper<ChargeBill> selectByForm(ChargeBillForm billForm){
        DataTableRspWrapper<ChargeBill> rspWrapper = chargeBillService.selectByForm(billForm);
        rspWrapper.setDraw(billForm.getDraw());
        return rspWrapper;
    }

    @RequestMapping(value = "sumByForm")
    public CommonRsp selectSumByForm(ChargeBillForm billForm){
        Double result = chargeBillService.selectSumByForm(billForm);
        return data2Rsp(result);
    }

    @RequestMapping(path = "updateInvoiceNum")
    public CommonRsp updateInvoiceNum(ChargeBill chargeBill,HttpSession session){
        int count = chargeBillService.selectCountByInvoiceNum(chargeBill.getInvoiceNum());
        if(count>0){
            CommonRsp rsp = new CommonRsp();
            rsp.setSuccess(false);
            rsp.setResultCode("8000");
            rsp.setDescription("已存在发票编号，不可重复!");
            return rsp;
        }
        String handler = getHandler(session);
        int effect = chargeBillService.updateInvoiceNum(chargeBill,handler);
        return dbEffect2Rsp(effect);
    }

    @RequestMapping(path = "abolishInvoiceNum")
    public CommonRsp abolishInvoiceNum(ChargeBill chargeBill,HttpSession session){
        String handler = getHandler(session);
        int effect = chargeBillService.abolishInvoiceNum(chargeBill,handler);
        return dbEffect2Rsp(effect);
    }

}
