package com.qth.dao;

import com.qth.model.House;
import com.qth.model.Unit;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface HouseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(House record);

    House selectByPrimaryKey(Integer id);

    House selectSimpleOne(Integer id);

    List<House> selectAll();

    int updateByPrimaryKey(House record);

    List<Unit> selectDataTable(House entity);

    int selectDataTableCount(House entity);

    int updateBalanceByCode(House house);

    BigDecimal selectBalanceByCode(String code);

    Date lastAccrualInResidentialArea(Integer residentialArea);

    Date lastAccrualInBuilding(Integer building);

    Date lastAccrualInUnit(Integer unit);

    Date lastAccrualInFloor(House house);

    List<House> allHousesInResidentialArea(Integer residentialArea);

    List<House> allHousesInBuilding(Integer building);

    List<House> allHousesInUnit(Integer unit);

    List<House> allHousesInFloor(House house);

}