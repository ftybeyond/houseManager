package com.qth.service;

import com.qth.model.RepairItem;

import java.util.List;

public interface IRepairItemService extends IBaseService<RepairItem>
{
    public List<RepairItem> selectAll();

    public int insertRepairItem(RepairItem repairItem);

    public int updateRepairItem(RepairItem repairItem);

    public RepairItem findRepairItemById(int id);

    public int deleteRepairItemById(int id);

    public List<RepairItem> selectByRecord(Integer record);
}
