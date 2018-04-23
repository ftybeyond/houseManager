package com.qth.dao;

import com.qth.model.RepairRecord;
import java.util.List;

public interface RepairRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RepairRecord record);

    RepairRecord selectByPrimaryKey(Integer id);

    List<RepairRecord> selectAll();

    int updateByPrimaryKey(RepairRecord record);
}