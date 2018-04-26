package com.qth.service.impl;

import com.alibaba.fastjson.JSON;
import com.qth.dao.RepairItemMapper;
import com.qth.dao.RepairRecordMapper;
import com.qth.model.RepairItem;
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

    @Autowired
    RepairItemMapper repairItemMapper;

    @Override
    public List<RepairRecord> selectAll() {
        return repairRecordMapper.selectAll();
    }

    @Transactional
    @Override
    public int insertRepairRecord(RepairRecord repairRecord) {
        //添加维修记录
        int result = repairRecordMapper.insert(repairRecord);
        //获取维修项目参数
        String supplement = repairRecord.getSupplement();
        if(supplement!= null&&supplement.trim().length()>0){
            //插入维修项目
            List<RepairItem> repairItems = JSON.parseArray(supplement,RepairItem.class);
            for(RepairItem item :repairItems){
                item.setRepairRecord(repairRecord.getId());
                repairItemMapper.insert(item);
            }
        }
        return result;
    }

    @Transactional
    @Override
    public int updateRepairRecord(RepairRecord repairRecord) {
        //删除所有维修项
        repairItemMapper.deleteByRecord(repairRecord.getId());
        //获取维修项目参数
        String supplement = repairRecord.getSupplement();
        if(supplement!= null&&supplement.trim().length()>0){
            //插入维修项目
            List<RepairItem> repairItems = JSON.parseArray(supplement,RepairItem.class);
            for(RepairItem item :repairItems){
                item.setRepairRecord(repairRecord.getId());
                repairItemMapper.insert(item);
            }
        }
        return repairRecordMapper.updateByPrimaryKey(repairRecord);
    }

    @Override
    public RepairRecord findRepairRecordById(int id) {
        return repairRecordMapper.selectByPrimaryKey(id);
    }

    @Transactional
    @Override
    public int deleteRepairRecordById(int id) {
        //删除所有维修项
        repairItemMapper.deleteByRecord(id);
        return repairRecordMapper.deleteByPrimaryKey(id);
    }
}
