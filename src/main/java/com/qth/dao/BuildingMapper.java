package com.qth.dao;

import com.qth.model.Building;
import com.qth.model.ResidentialArea;

import java.util.List;

public interface BuildingMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Building record);

    Building selectByPrimaryKey(Integer id);

    List<Building> selectAll();

    int updateByPrimaryKey(Building record);

    List<Building> selectDataTable(Building entity);

    int selectDataTableCount(Building entity);

    Building findByName(Integer residentialArea,String name);
}