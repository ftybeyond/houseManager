package com.qth.action;

import com.qth.model.AccountLog;
import com.qth.model.RepairItem;
import com.qth.model.House;
import com.qth.model.common.CommonRsp;
import com.qth.model.common.DataTableRspWrapper;
import com.qth.service.IAccountLogService;
import com.qth.service.IRepairItemService;
import com.qth.service.IRepairRecordService;
import com.qth.service.IShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ShareController extends BaseController{

    @Autowired
    IShareService shareService;

    @Autowired
    IRepairRecordService recordService;

    @Autowired
    IRepairItemService repairItemService;

    @Autowired
    IAccountLogService accountLogService;

    @RequestMapping(value = "/page/shareDetail")
    public ModelAndView shareDetail(Integer record){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("shareDetail");
        List<RepairItem> items = repairItemService.selectByRecord(record);
        modelAndView.addObject("items",items);
        modelAndView.addObject("record",recordService.findRepairRecordById(record));
        return modelAndView;
    }

    @RequestMapping(value = "/rest/share/calculateResult")
    @ResponseBody
    public CommonRsp calculateResult(String paths){
        CommonRsp rsp = new CommonRsp();
        if (paths!=null&&paths.length()>0) {
            BigDecimal sumAres = shareService.sumShareArea(paths);
            Integer sumHouses = shareService.sumShareHouses(paths);
            rsp.setResultCode("0000");
            rsp.setSuccess(true);
            Map map = new HashMap();
            map.put("sumArea",sumAres);
            map.put("sumHouses",sumHouses);
            rsp.setAttr(map);
        } else {
            rsp.setSuccess(false);
            rsp.setResultCode("1001");
            rsp.setDescription("无效参数");
        }
        return rsp;
    }

    @RequestMapping(value = "/rest/share/shareCheck")
    @ResponseBody
    public CommonRsp shareCheck(String paths, Integer shareType, BigDecimal sumArea, Integer totalHouse, BigDecimal cost,Integer record, HttpSession session){
        CommonRsp rsp = new CommonRsp();
        if (paths!=null&&paths.length()>0) {
            List<House> unBalance = shareService.checkShare(paths,shareType,sumArea,totalHouse,cost,record);
            if(unBalance.size()>0){
                rsp.setData(unBalance);
            }else{
                rsp.setSuccess(false);
                rsp.setResultCode("1001");
                rsp.setDescription("无效参数");
            }
        } else {
            rsp.setSuccess(false);
            rsp.setResultCode("1001");
            rsp.setDescription("无效参数");
        }
        return rsp;
    }

    @RequestMapping(value = "/rest/share/doShare")
    @ResponseBody
    public CommonRsp doShare(String paths, Integer shareType, BigDecimal sumArea, Integer totalHouse, BigDecimal cost,Integer record, HttpSession session){
        CommonRsp rsp = new CommonRsp();
        if (paths!=null&&paths.length()>0) {
            int result = shareService.share(paths,shareType,sumArea,totalHouse,cost,record,getHandler(session));
            rsp.setSuccess(true);
            rsp.setDescription("分摊" +result + "户");
            rsp.setResultCode("0000");
        } else {
            rsp.setSuccess(false);
            rsp.setResultCode("1001");
            rsp.setDescription("无效参数");
        }
        return rsp;
    }

    @RequestMapping(value = "/rest/share/doShareAccount")
    @ResponseBody
    public CommonRsp doShareAccount(Integer record, HttpSession session){
        CommonRsp rsp = new CommonRsp();
        int effect = shareService.shareAccount(record,getHandler(session));
        return dbEffect2Rsp(effect);
    }

    @RequestMapping(value = "/rest/share/doShareBack")
    @ResponseBody
    public CommonRsp doShareBack(Integer record,HttpSession session){
        int effect = shareService.shareBackAccount(record,getHandler(session));
        return dbEffect2Rsp(effect);
    }

    @RequestMapping(value = "/rest/share/shareBackInfo")
    @ResponseBody
    public CommonRsp shareBackInfo(Long seq){
        Map<String,BigDecimal> result = shareService.shareBackInfo(seq);
        return data2Rsp(result);
    }

    @RequestMapping(value = "/rest/share/shareSumAccountDetail")
    @ResponseBody
    public CommonRsp shareSumAccountDetail(AccountLog record){
        double result = shareService.shareSumAccountDetail(record);
        return data2Rsp(result);
    }

    @RequestMapping(value = "/rest/share/shareAccountDetail")
    @ResponseBody
    public DataTableRspWrapper<AccountLog> shareAccountDetail(AccountLog record){
        DataTableRspWrapper<AccountLog> rspWrapper = shareService.shareAccountDetail(record);
        rspWrapper.setDraw(1);
        return shareService.shareAccountDetail(record);
    }
}
