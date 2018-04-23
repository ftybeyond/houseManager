package com.qth.action;

import com.qth.model.common.CommonRsp;
import com.qth.model.InvoiceLog;
import com.qth.service.IInvoiceLogService;
import com.qth.model.common.DataTableRspWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "rest/invoiceLog")
@ResponseBody
public class InvoiceLogController extends BaseController{

@Autowired
IInvoiceLogService invoiceLogService;

/**
* 区域表格数据请求
* @param invoiceLog
* @return
*/
@RequestMapping(path="table")
public DataTableRspWrapper<InvoiceLog> table(InvoiceLog invoiceLog){

    DataTableRspWrapper<InvoiceLog> rspWrapper = invoiceLogService.selectDataTable2Rsp(invoiceLog);
        //赋值绘制时序标识
        rspWrapper.setDraw(invoiceLog.getDraw());
        return rspWrapper;
    }

    /**
    * 添加区域信息
    * @param invoiceLog
    * @return
    */
    @RequestMapping(value = "insert")
    public CommonRsp insert(InvoiceLog invoiceLog){
        int effect = invoiceLogService.insertInvoiceLog(invoiceLog);
        return dbEffect2Rsp(effect);
    }

    @RequestMapping(value = "update")
    public CommonRsp update(InvoiceLog invoiceLog){
        int effect = invoiceLogService.updateInvoiceLog(invoiceLog);
        return dbEffect2Rsp(effect);
    }

    @RequestMapping(value = "one")
    public CommonRsp selectOne(Integer id){
        InvoiceLog invoiceLog = invoiceLogService.findInvoiceLogById(id);
        return data2Rsp(invoiceLog);
    }

    @RequestMapping(value = "delete")
    public CommonRsp deleteById(Integer id){
        int effect = invoiceLogService.deleteInvoiceLogById(id);
        return dbEffect2Rsp(effect);
    }

}
