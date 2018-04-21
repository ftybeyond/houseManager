package com.qth.service;

import com.qth.model.ChargeCriterion;

import java.util.List;

public interface IChargeCriterionService extends IBaseService<ChargeCriterion>
{
    public List<ChargeCriterion> selectAll();

    public int insertChargeCriterion(ChargeCriterion chargeCriterion);

    public int updateChargeCriterion(ChargeCriterion chargeCriterion);

    public ChargeCriterion findChargeCriterionById(int id);

    public int deleteChargeCriterionById(int id);
}
