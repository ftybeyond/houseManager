package com.qth.service;

import com.qth.model.ChargeBill;
import com.qth.model.common.DataTableRspWrapper;
import com.qth.model.dto.HouseTreeModel;

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

}
