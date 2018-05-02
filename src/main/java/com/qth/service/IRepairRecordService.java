package com.qth.service;

import com.qth.model.RepairRecord;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

public interface IRepairRecordService extends IBaseService<RepairRecord>
{
    public List<RepairRecord> selectAll();

    public int insertRepairRecord(RepairRecord repairRecord);

    public int updateRepairRecord(RepairRecord repairRecord);

    public RepairRecord findRepairRecordById(Integer id);

    public int deleteRepairRecordById(Integer id);

}
