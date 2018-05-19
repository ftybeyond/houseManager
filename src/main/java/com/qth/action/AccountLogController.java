package com.qth.action;

import com.qth.model.common.CommonRsp;
import com.qth.model.AccountLog;
import com.qth.service.IAccountLogService;
import com.qth.model.common.DataTableRspWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "rest/accountLog")
@ResponseBody
public class AccountLogController extends BaseController {

    @Autowired
    IAccountLogService accountLogService;

    /**
     * 区域表格数据请求
     *
     * @param accountLog
     * @return
     */
    @RequestMapping(path = "table")
    public DataTableRspWrapper<AccountLog> table(AccountLog accountLog) {

        DataTableRspWrapper<AccountLog> rspWrapper = accountLogService.selectDataTable2Rsp(accountLog);
        //赋值绘制时序标识
        rspWrapper.setDraw(accountLog.getDraw());
        return rspWrapper;
    }

    /**
     * 添加区域信息
     *
     * @param accountLog
     * @return
     */
    @RequestMapping(value = "insert")
    public CommonRsp insert(AccountLog accountLog) {
        int effect = accountLogService.insertAccountLog(accountLog);
        return dbEffect2Rsp(effect);
    }

    @RequestMapping(value = "update")
    public CommonRsp update(AccountLog accountLog) {
        int effect = accountLogService.updateAccountLog(accountLog);
        return dbEffect2Rsp(effect);
    }

    @RequestMapping(value = "one")
    public CommonRsp selectOne(Integer id) {
        AccountLog accountLog = accountLogService.findAccountLogById(id);
        return data2Rsp(accountLog);
    }

    @RequestMapping(value = "delete")
    public CommonRsp deleteById(Integer id) {
        int effect = accountLogService.deleteAccountLogById(id);
        return dbEffect2Rsp(effect);
    }

    @RequestMapping(value = "selectByHouseCode")
    public CommonRsp selectByHouseCode(String code){
        List<AccountLog> list = accountLogService.selectByHouseCode(code);
        return data2Rsp(list);
    }

}
