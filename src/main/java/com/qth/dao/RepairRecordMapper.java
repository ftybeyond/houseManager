package com.qth.dao;

import com.qth.model.RepairRecord;

import java.math.BigDecimal;
import java.util.List;

public interface RepairRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RepairRecord record);

    RepairRecord selectByPrimaryKey(Integer id);

    List<RepairRecord> selectAll();

    int updateByPrimaryKey(RepairRecord record);

    BigDecimal recordCostSum(Integer record);

    int stateChange(RepairRecord repairRecord);
}