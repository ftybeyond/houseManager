package com.qth.service.impl;

import com.qth.dao.InvoiceLogMapper;
import com.qth.model.ChargeBill;
import com.qth.model.InvoiceLog;
import com.qth.model.common.DataTableRspWrapper;
import com.qth.model.dto.InvoiceForm;
import com.qth.service.IInvoiceLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class InvoiceLogService extends BaseService<InvoiceLog> implements IInvoiceLogService{

    @Autowired
    InvoiceLogMapper invoiceLogMapper;

    @Override
    public List<InvoiceLog> selectAll() {
        return invoiceLogMapper.selectAll();
    }

    @Override
    public int insertInvoiceLog(InvoiceLog invoiceLog) {
        return invoiceLogMapper.insert(invoiceLog);
    }

    @Override
    public int updateInvoiceLog(InvoiceLog invoiceLog) {
        return invoiceLogMapper.updateByPrimaryKey(invoiceLog);
    }

    @Override
    public InvoiceLog findInvoiceLogById(int id) {
        return invoiceLogMapper.selectByPrimaryKey(id);
    }

    @Override
    public int deleteInvoiceLogById(int id) {
        return invoiceLogMapper.deleteByPrimaryKey(id);
    }

    @Override
    public DataTableRspWrapper<InvoiceLog> selectTable(InvoiceForm invoiceForm) {
        DataTableRspWrapper<InvoiceLog> rspWrapper = new DataTableRspWrapper<>();
        rspWrapper.setData(invoiceLogMapper.selectByForm(invoiceForm));
        rspWrapper.setRecordsTotal(invoiceLogMapper.selectCountByForm(invoiceForm));
        return rspWrapper;
    }

    @Override
    public List<InvoiceLog> selectByBill(Integer billId) {
        return invoiceLogMapper.selectByBill(billId);
    }

}
