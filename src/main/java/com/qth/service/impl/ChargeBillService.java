package com.qth.service.impl;

import com.qth.dao.AccountLogMapper;
import com.qth.dao.ChargeBillMapper;
import com.qth.dao.HouseMapper;
import com.qth.dao.InvoiceLogMapper;
import com.qth.model.AccountLog;
import com.qth.model.ChargeBill;
import com.qth.model.House;
import com.qth.model.InvoiceLog;
import com.qth.model.common.DataTableRspWrapper;
import com.qth.model.dto.ChargeBillForm;
import com.qth.model.dto.ChargeBillPrintInfo;
import com.qth.model.dto.HouseTreeModel;
import com.qth.model.dto.InvoiceForm;
import com.qth.service.IChargeBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ChargeBillService extends BaseService<ChargeBill> implements IChargeBillService{

    @Autowired
    ChargeBillMapper chargeBillMapper;

    @Autowired
    AccountLogMapper accountLogMapper;

    @Autowired
    InvoiceLogMapper invoiceLogMapper;

    @Autowired
    HouseMapper houseMapper;

    @Override
    public List<ChargeBill> selectAll() {
        return chargeBillMapper.selectAll();
    }

    @Override
    public int insertChargeBill(ChargeBill chargeBill) {
        return chargeBillMapper.insert(chargeBill);
    }

    @Override
    public int updateChargeBill(ChargeBill chargeBill) {
        return chargeBillMapper.updateByPrimaryKey(chargeBill);
    }

    @Override
    public ChargeBill findChargeBillById(int id) {
        return chargeBillMapper.selectByPrimaryKey(id);
    }

    @Override
    public int deleteChargeBillById(int id) {
        return chargeBillMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int countByHouse(Integer house) {
        return chargeBillMapper.countByHouse(house);
    }

    @Override
    public DataTableRspWrapper<ChargeBill> treeTable(HouseTreeModel model) {
        DataTableRspWrapper<ChargeBill> rspWrapper = new DataTableRspWrapper();
        rspWrapper.setData(chargeBillMapper.selectByTreeNode(model));
        rspWrapper.setRecordsTotal(chargeBillMapper.selectCountByTreeNode(model));
        rspWrapper.setDraw(model.getDraw());
        return rspWrapper;
    }

    @Transactional
    @Override
    public int updateState(ChargeBill chargeBill,Integer toState,String handler) {
        if(chargeBill.getState() ==0 ||chargeBill.getState() ==2){
            //入账操作，记录余额变更，更新houseBalance
            House house = houseMapper.selectByCode(chargeBill.getHouseCode());
            AccountLog accountLog = new AccountLog();
            accountLog.setHouseOwner(house.getOwnerName());
            accountLog.setHouseCode(house.getCode());
            accountLog.setTradeMoney(chargeBill.getActualSum());
            accountLog.setHandler(handler);
            accountLog.setTradeType(1);
            accountLog.setTradeTime(new Date());
            accountLog.setBalance(house.getAccountBalance());
            if(toState==0){
                accountLog.setRemark("收缴登帐");
            }else{
                accountLog.setRemark("补缴登帐");
            }
            accountLogMapper.insert(accountLog);
            house.setAccountBalance(house.getAccountBalance().add(chargeBill.getActualSum()));
            houseMapper.updateBalanceByCode(house);
        }
        chargeBill.setState(toState);
        return chargeBillMapper.updateState(chargeBill);
    }

    @Override
    public ChargeBillPrintInfo getPrintInfo(Integer id) {
        return chargeBillMapper.printInfo(id);
    }

    @Override
    public DataTableRspWrapper<ChargeBill> selectByForm(ChargeBillForm chargeBillForm) {
        DataTableRspWrapper<ChargeBill> rspWrapper = new DataTableRspWrapper<>();
        rspWrapper.setData(chargeBillMapper.selectByForm(chargeBillForm));
        rspWrapper.setRecordsTotal(chargeBillMapper.selectCountByForm(chargeBillForm));
        return rspWrapper;
    }

    @Override
    public DataTableRspWrapper<ChargeBill> selectInvoiceByForm(InvoiceForm invoiceForm) {
        DataTableRspWrapper<ChargeBill> rspWrapper = new DataTableRspWrapper<>();
        rspWrapper.setData(chargeBillMapper.selectInvoiceByForm(invoiceForm));
        rspWrapper.setRecordsTotal(chargeBillMapper.selectCountInvoiceByForm(invoiceForm));
        return rspWrapper;
    }

    @Override
    public int updateInvoiceNum(ChargeBill chargeBill,String handler) {
        //记录日志
        InvoiceLog invoiceLog = new InvoiceLog();
        invoiceLog.setEventType(2);
        invoiceLog.setBill(chargeBill.getId());
        invoiceLog.setHandler(handler);
        invoiceLog.setInvoiceNum(chargeBill.getInvoiceNum());
        invoiceLog.setStamp(new Date());
        invoiceLog.setPayor(chargeBill.getHouseOwner());
        invoiceLog.setMoney(chargeBill.getActualSum());
        invoiceLogMapper.insert(invoiceLog);
        return chargeBillMapper.updateInvoiceNum(chargeBill);
    }

    @Override
    public int abolishInvoiceNum(ChargeBill chargeBill,String handler) {
        //记录日志
        InvoiceLog invoiceLog = new InvoiceLog();
        invoiceLog.setEventType(4);
        invoiceLog.setBill(chargeBill.getId());
        invoiceLog.setHandler(handler);
        invoiceLog.setInvoiceNum(chargeBill.getInvoiceNum());
        invoiceLog.setStamp(new Date());
        invoiceLog.setPayor(chargeBill.getHouseOwner());
        invoiceLog.setMoney(chargeBill.getActualSum());
        invoiceLogMapper.insert(invoiceLog);
        return chargeBillMapper.abolishInvoiceNum(chargeBill);
    }

    @Override
    public int selectCountByInvoiceNum(String invoiceNum) {
        return chargeBillMapper.selectCountByInvoiceNum(invoiceNum);
    }
}
