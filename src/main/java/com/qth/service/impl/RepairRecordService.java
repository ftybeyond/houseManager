package com.qth.service.impl;

import com.qth.dao.RepairRecordMapper;
import com.qth.model.RepairRecord;
import com.qth.service.IRepairRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RepairRecordService extends BaseService<RepairRecord> implements IRepairRecordService{

    @Autowired
    RepairRecordMapper repairRecordMapper;

    @Override
    public List<RepairRecord> selectAll() {
        return repairRecordMapper.selectAll();
    }

    @Override
    public int insertRepairRecord(RepairRecord repairRecord) {
        return repairRecordMapper.insert(repairRecord);
    }

    @Override
    public int updateRepairRecord(RepairRecord repairRecord) {
        return repairRecordMapper.updateByPrimaryKey(repairRecord);
    }

    @Override
    public RepairRecord findRepairRecordById(int id) {
        return repairRecordMapper.selectByPrimaryKey(id);
    }

    @Override
    public int deleteRepairRecordById(int id) {
        return repairRecordMapper.deleteByPrimaryKey(id);
    }
}
