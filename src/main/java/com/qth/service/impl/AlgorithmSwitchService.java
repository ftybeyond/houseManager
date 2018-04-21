package com.qth.service.impl;

import com.qth.dao.AlgorithmSwitchMapper;
import com.qth.model.AlgorithmSwitch;
import com.qth.service.IAlgorithmSwitchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AlgorithmSwitchService extends BaseService<AlgorithmSwitch> implements IAlgorithmSwitchService{

    @Autowired
    AlgorithmSwitchMapper algorithmSwitchMapper;

    @Override
    public List<AlgorithmSwitch> selectAll() {
        return algorithmSwitchMapper.selectAll();
    }

    @Override
    public int insertAlgorithmSwitch(AlgorithmSwitch algorithmSwitch) {
        return algorithmSwitchMapper.insert(algorithmSwitch);
    }

    @Override
    public int updateAlgorithmSwitch(AlgorithmSwitch algorithmSwitch) {
        return algorithmSwitchMapper.updateByPrimaryKey(algorithmSwitch);
    }

    @Override
    public AlgorithmSwitch findAlgorithmSwitchById(int id) {
        return algorithmSwitchMapper.selectByPrimaryKey(id);
    }

    @Override
    public int deleteAlgorithmSwitchById(int id) {
        return algorithmSwitchMapper.deleteByPrimaryKey(id);
    }
}
