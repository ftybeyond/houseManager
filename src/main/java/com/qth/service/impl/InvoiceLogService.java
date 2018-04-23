package com.qth.service.impl;

import com.qth.dao.InvoiceLogMapper;
import com.qth.model.InvoiceLog;
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
}
