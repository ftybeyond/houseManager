package com.qth.service;

import com.qth.model.ChargeBill;
import com.qth.model.common.DataTableRspWrapper;
import com.qth.model.dto.ChargeBillForm;
import com.qth.model.dto.ChargeBillPrintInfo;
import com.qth.model.dto.HouseTreeModel;
import com.qth.model.dto.InvoiceForm;

import java.util.List;

public interface IChargeBillService extends IBaseService<ChargeBill>
{
    public List<ChargeBill> selectAll();

    public int insertChargeBill(ChargeBill chargeBill);

    public int updateChargeBill(ChargeBill chargeBill);

    public ChargeBill findChargeBillById(int id);

    public int deleteChargeBillById(int id);

    public int countByHouse(Integer house);

    public DataTableRspWrapper treeTable(HouseTreeModel model);

    public int updateState(ChargeBill chargeBill,Integer toState,String handler);

    public ChargeBillPrintInfo getPrintInfo(Integer id);

    public DataTableRspWrapper<ChargeBill> selectByForm(ChargeBillForm chargeBillForm);

    DataTableRspWrapper<ChargeBill> selectInvoiceByForm(InvoiceForm invoiceForm);

    public int updateInvoiceNum(ChargeBill chargeBill,String handler);

    public int abolishInvoiceNum(ChargeBill chargeBill,String handler);

    public int selectCountByInvoiceNum(String invoiceNum);

    public Double selectSumByForm(ChargeBillForm chargeBillForm);

}
