package com.qth.dao;

import com.qth.model.ChargeCriterion;
import java.util.List;
import java.util.Map;

public interface ChargeCriterionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChargeCriterion record);

    ChargeCriterion selectByPrimaryKey(Integer id);

    List<ChargeCriterion> selectAll();

    int updateByPrimaryKey(ChargeCriterion record);

    List<ChargeCriterion> selectByHouse(Map param);
}