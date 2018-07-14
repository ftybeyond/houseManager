package com.qth.service;

import com.qth.model.AlgorithmSwitch;
import com.qth.model.ChargeCriterion;
import com.qth.model.House;
import com.qth.model.User;
import com.qth.model.common.DataTableRspWrapper;
import com.qth.model.common.ImportCacheNode;
import com.qth.model.dto.HouseTreeModel;
import com.qth.model.dto.InvoiceInfo;

import javax.print.attribute.IntegerSyntax;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IHouseService extends IBaseService<House> {
    public DataTableRspWrapper<House> selectDataTable2Rsp(House house);

    public List<House> selectAll();

    public int insertHouse(House house);

    public int updateHouse(House house);

    public int updateOwnerInfo(House house);

    public House findHouseById(int id);

    public int deleteHouseById(int id);

    public Date getLastAccrual(String paths);

    public List<House> selectByTreePath(String paths);

    public List<House> selectByTreeNode(HouseTreeModel model);

    public int selectCountByTreeNode(HouseTreeModel model);

    public ChargeCriterion getChargeCriterionByHouse(Integer house, Integer user);

    public AlgorithmSwitch getChargeType();

    public int backBalance(Integer house, String handler);

    public int importByExcel(Map<String, ImportCacheNode> node, String handler, Date stamp) throws Exception;

    public InvoiceInfo invoiceInfoByCode(String code);
}
