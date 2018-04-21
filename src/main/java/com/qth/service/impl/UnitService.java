package com.qth.service.impl;

import com.qth.dao.UnitMapper;
import com.qth.model.Unit;
import com.qth.service.IUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UnitService extends BaseService<Unit> implements IUnitService{

    @Autowired
    UnitMapper unitMapper;

    @Override
    public List<Unit> selectAll() {
        return unitMapper.selectAll();
    }

    @Override
    public int insertUnit(Unit unit) {
        return unitMapper.insert(unit);
    }

    @Override
    public int updateUnit(Unit unit) {
        return unitMapper.updateByPrimaryKey(unit);
    }

    @Override
    public Unit findUnitById(int id) {
        return unitMapper.selectByPrimaryKey(id);
    }

    @Override
    public int deleteUnitById(int id) {
        return unitMapper.deleteByPrimaryKey(id);
    }
}
