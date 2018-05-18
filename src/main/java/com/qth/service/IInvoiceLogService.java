package com.qth.service;

import com.qth.model.InvoiceLog;
import com.qth.model.common.DataTableRspWrapper;
import com.qth.model.dto.InvoiceForm;

import java.util.List;

public interface IInvoiceLogService extends IBaseService<InvoiceLog>
{
    public List<InvoiceLog> selectAll();

    public int insertInvoiceLog(InvoiceLog invoiceLog);

    public int updateInvoiceLog(InvoiceLog invoiceLog);

    public InvoiceLog findInvoiceLogById(int id);

    public int deleteInvoiceLogById(int id);

    public DataTableRspWrapper<InvoiceLog> selectTable(InvoiceForm invoiceForm);

    public List<InvoiceLog> selectByBill(Integer billId);
}
