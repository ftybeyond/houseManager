package com.qth.dao;

import com.qth.model.RepairItem;
import java.util.List;

public interface RepairItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RepairItem record);

    RepairItem selectByPrimaryKey(Integer id);

    List<RepairItem> selectAll();

    int updateByPrimaryKey(RepairItem record);
}