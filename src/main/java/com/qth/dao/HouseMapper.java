package com.qth.dao;

import com.qth.model.House;
import com.qth.model.Unit;

import java.util.List;

public interface HouseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(House record);

    House selectByPrimaryKey(Integer id);

    List<House> selectAll();

    int updateByPrimaryKey(House record);

    List<Unit> selectDataTable(House entity);

    int selectDataTableCount(House entity);
}