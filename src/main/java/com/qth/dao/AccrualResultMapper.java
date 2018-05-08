package com.qth.dao;

import com.qth.model.AccrualResult;
import com.qth.model.House;

import java.util.List;

public interface AccrualResultMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AccrualResult record);

    AccrualResult selectByPrimaryKey(Integer id);

    List<AccrualResult> selectAll();

    int updateByPrimaryKey(AccrualResult record);

    List<AccrualResult> allResultInResidentialArea(AccrualResult accrualResult);

    List<AccrualResult> allResultInBuilding(AccrualResult accrualResult);

    List<AccrualResult> allResultInUnit(AccrualResult accrualResult);

    List<AccrualResult> allResultInFloor(AccrualResult accrualResult);

    List<AccrualResult> allResultInHouse(AccrualResult accrualResult);

    int allCountResultInResidentialArea(AccrualResult accrualResult);

    int allCountResultInBuilding(AccrualResult accrualResult);

    int allCountResultInUnit(AccrualResult accrualResult);

    int allCountResultInFloor(AccrualResult accrualResult);

    int allCountResultInHouse(AccrualResult accrualResult);

    int delectBatch(List<AccrualResult> list);
}