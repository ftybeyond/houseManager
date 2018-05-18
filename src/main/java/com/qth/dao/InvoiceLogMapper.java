package com.qth.dao;

import com.qth.model.InvoiceLog;
import com.qth.model.dto.InvoiceForm;

import java.util.List;

public interface InvoiceLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(InvoiceLog record);

    InvoiceLog selectByPrimaryKey(Integer id);

    List<InvoiceLog> selectAll();

    int updateByPrimaryKey(InvoiceLog record);

    List<InvoiceLog> selectByForm(InvoiceForm invoiceForm);

    int selectCountByForm(InvoiceForm invoiceForm);

    List<InvoiceLog> selectByBill(Integer bill);
}