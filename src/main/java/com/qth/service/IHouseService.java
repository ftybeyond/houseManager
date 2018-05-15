package com.qth.service;

import com.qth.model.AlgorithmSwitch;
import com.qth.model.ChargeCriterion;
import com.qth.model.House;
import com.qth.model.User;
import com.qth.model.common.DataTableRspWrapper;
import com.qth.model.dto.HouseTreeModel;

import javax.print.attribute.IntegerSyntax;
import java.util.Date;
import java.util.List;

public interface IHouseService extends IBaseService<House>
{
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

    public AlgorithmSwitch getChargeType(Integer user);

    public int backBalance(Integer house,String handler);

}
