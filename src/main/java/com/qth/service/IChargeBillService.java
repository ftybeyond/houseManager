package com.qth.service;

import com.qth.model.ChargeBill;

import java.util.List;

public interface IChargeBillService extends IBaseService<ChargeBill>
{
    public List<ChargeBill> selectAll();

    public int insertChargeBill(ChargeBill chargeBill);

    public int updateChargeBill(ChargeBill chargeBill);

    public ChargeBill findChargeBillById(int id);

    public int deleteChargeBillById(int id);

    public int countByHouse(Integer house);
}
