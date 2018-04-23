package com.qth.service.impl;

import com.qth.dao.RepairItemMapper;
import com.qth.model.RepairItem;
import com.qth.service.IRepairItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RepairItemService extends BaseService<RepairItem> implements IRepairItemService{

    @Autowired
    RepairItemMapper repairItemMapper;

    @Override
    public List<RepairItem> selectAll() {
        return repairItemMapper.selectAll();
    }

    @Override
    public int insertRepairItem(RepairItem repairItem) {
        return repairItemMapper.insert(repairItem);
    }

    @Override
    public int updateRepairItem(RepairItem repairItem) {
        return repairItemMapper.updateByPrimaryKey(repairItem);
    }

    @Override
    public RepairItem findRepairItemById(int id) {
        return repairItemMapper.selectByPrimaryKey(id);
    }

    @Override
    public int deleteRepairItemById(int id) {
        return repairItemMapper.deleteByPrimaryKey(id);
    }
}
