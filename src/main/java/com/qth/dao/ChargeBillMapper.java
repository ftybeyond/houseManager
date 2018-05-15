package com.qth.dao;

import com.qth.model.ChargeBill;
import com.qth.model.dto.HouseTreeModel;

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
}