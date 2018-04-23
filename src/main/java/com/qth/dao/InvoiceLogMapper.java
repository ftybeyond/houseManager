package com.qth.dao;

import com.qth.model.InvoiceLog;
import java.util.List;

public interface InvoiceLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(InvoiceLog record);

    InvoiceLog selectByPrimaryKey(Integer id);

    List<InvoiceLog> selectAll();

    int updateByPrimaryKey(InvoiceLog record);
}