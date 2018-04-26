package com.qth.service;

import com.qth.model.RepairRecord;

import java.util.List;

public interface IRepairRecordService extends IBaseService<RepairRecord>
{
    public List<RepairRecord> selectAll();

    public int insertRepairRecord(RepairRecord repairRecord);

    public int updateRepairRecord(RepairRecord repairRecord);

    public RepairRecord findRepairRecordById(int id);

    public int deleteRepairRecordById(int id);

}
