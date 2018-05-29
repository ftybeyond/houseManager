package com.qth.dao;

import com.qth.model.Building;
import com.qth.model.Unit;
import java.util.List;

public interface UnitMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Unit record);

    Unit selectByPrimaryKey(Integer id);

    List<Unit> selectAll();

    int updateByPrimaryKey(Unit record);

    List<Unit> selectDataTable(Unit entity);

    int selectDataTableCount(Unit entity);

    Unit findByName(Integer building,String name);
}