package com.qth.service.impl;

import com.qth.dao.ChargeCriterionMapper;
import com.qth.model.ChargeCriterion;
import com.qth.service.IChargeCriterionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ChargeCriterionService extends BaseService<ChargeCriterion> implements IChargeCriterionService{

    @Autowired
    ChargeCriterionMapper chargeCriterionMapper;

    @Override
    public List<ChargeCriterion> selectAll() {
        return chargeCriterionMapper.selectAll();
    }

    @Override
    public int insertChargeCriterion(ChargeCriterion chargeCriterion) {
        return chargeCriterionMapper.insert(chargeCriterion);
    }

    @Override
    public int updateChargeCriterion(ChargeCriterion chargeCriterion) {
        return chargeCriterionMapper.updateByPrimaryKey(chargeCriterion);
    }

    @Override
    public ChargeCriterion findChargeCriterionById(int id) {
        return chargeCriterionMapper.selectByPrimaryKey(id);
    }

    @Override
    public int deleteChargeCriterionById(int id) {
        return chargeCriterionMapper.deleteByPrimaryKey(id);
    }
}
