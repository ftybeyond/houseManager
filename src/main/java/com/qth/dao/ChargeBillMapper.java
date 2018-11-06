package com.qth.dao;

import com.qth.model.ChargeBill;
import com.qth.model.dto.ChargeBillForm;
import com.qth.model.dto.ChargeBillPrintInfo;
import com.qth.model.dto.HouseTreeModel;
import com.qth.model.dto.InvoiceForm;

import java.util.List;

public interface ChargeBillMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChargeBill record);

    ChargeBill selectByPrimaryKey(Integer id);

    List<ChargeBill> selectAll();

    int updateByPrimaryKey(ChargeBill record);

    int countByHouse(Integer house);

    List<ChargeBill> selectByTreeNode(HouseTreeModel model);

    int selectCountByTreeNode(HouseTreeModel model);

    int updateState(ChargeBill chargeBill);

    ChargeBillPrintInfo printInfo(Integer id);

    List<ChargeBill> selectByForm(ChargeBillForm chargeBillForm);

    int selectCountByForm(ChargeBillForm chargeBillForm);

    List<ChargeBill> selectInvoiceByForm(InvoiceForm invoiceForm);

    int selectCountInvoiceByForm(InvoiceForm invoiceForm);

    int updateInvoiceNum(ChargeBill chargeBill);

    int abolishInvoiceNum(ChargeBill chargeBill);

    int selectCountByInvoiceNum(String invoiceNum);

    Double selectSumByForm(ChargeBillForm chargeBillForm);
}