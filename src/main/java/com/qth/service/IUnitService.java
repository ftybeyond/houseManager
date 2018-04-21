package com.qth.service;

import com.qth.model.Unit;
import com.qth.model.common.DataTableRspWrapper;

import java.util.List;

public interface IUnitService extends IBaseService<Unit>
{
    public DataTableRspWrapper<Unit> selectDataTable2Rsp(Unit unit);

    public List<Unit> selectAll();

    public int insertUnit(Unit unit);

    public int updateUnit(Unit unit);

    public Unit findUnitById(int id);

    public int deleteUnitById(int id);
}
