package com.qth.dao;

import com.qth.model.AlgorithmSwitch;
import java.util.List;

public interface AlgorithmSwitchMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AlgorithmSwitch record);

    AlgorithmSwitch selectByPrimaryKey(Integer id);

    List<AlgorithmSwitch> selectAll();

    int updateByPrimaryKey(AlgorithmSwitch record);
}