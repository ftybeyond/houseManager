package com.qth.dao;

import com.qth.model.common.ZTreeModel;
import com.qth.model.House;
import com.qth.model.common.ZTreeNodeReq;

import java.math.BigDecimal;
import java.util.List;

/**
 * by fty
 * zTree控件载入从小区到房屋的属性结构菜单
 */
public interface ShareMapper {

    List<ZTreeModel> loadResidentialAreaNodes(Integer residentialArea);

    List<ZTreeModel> loadBuildingNodes(Integer residentialArea);

    List<ZTreeModel> loadUnitNodes(Integer building);

    List<ZTreeModel> loadFloorNodes(Integer unit);

    List<ZTreeModel> loadHouseNodes(ZTreeNodeReq req);

    BigDecimal allAreaInResidentialArea(Integer residentialArea);

    Integer allHouseInResidentialArea(Integer residentialArea);

    BigDecimal allAreaInBuilding(Integer building);

    Integer allHouseInBuilding(Integer building);

    BigDecimal allAreaInUnit(Integer unit);

    Integer allHouseInUnit(Integer unit);

    BigDecimal allAreaInFloor(House house);

    Integer allHouseInFloor(House house);

    List<House> allHousesInResidentialArea(Integer residentialArea);

    List<House> allHousesInBuilding(Integer building);

    List<House> allHousesInUnit(Integer unit);

    List<House> allHousesInFloor(House house);

}
