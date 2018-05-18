package com.qth.action;

import com.qth.model.common.CommonRsp;
import com.qth.model.InvoiceLog;
import com.qth.model.dto.InvoiceForm;
import com.qth.service.IInvoiceLogService;
import com.qth.model.common.DataTableRspWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "rest/invoiceLog")
@ResponseBody
public class InvoiceLogController extends BaseController {

    @Autowired
    IInvoiceLogService invoiceLogService;

    /**
     * 区域表格数据请求
     *
     * @param invoiceForm
     * @return
     */
    @RequestMapping(path = "table")
    public DataTableRspWrapper<InvoiceLog> table(InvoiceForm invoiceForm) {
        DataTableRspWrapper<InvoiceLog> rspWrapper = invoiceLogService.selectTable(invoiceForm);
        //赋值绘制时序标识
        rspWrapper.setDraw(invoiceForm.getDraw());
        return rspWrapper;
    }

    @RequestMapping(path = "selectByBill")
    public CommonRsp selectByBill(Integer bill) {
        List<InvoiceLog> list = invoiceLogService.selectByBill(bill);
        return data2Rsp(list);
    }
}
