package com.qth.service;

import com.qth.model.Unit;

import java.util.List;

public interface IUnitService extends IBaseService<Unit>
{
    public List<Unit> selectAll();

    public int insertUnit(Unit unit);

    public int updateUnit(Unit unit);

    public Unit findUnitById(int id);

    public int deleteUnitById(int id);
}
