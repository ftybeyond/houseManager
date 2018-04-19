package com.qth.service.impl;

import com.qth.dao.CommonMapper;
import com.qth.model.common.Select2;
import com.qth.service.ISelectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SelectService implements ISelectService {

    @Autowired
    private CommonMapper commonMapper;

    @Override
    public List<Select2> getAll(String table) {
        return commonMapper.getAll(table);
    }

    @Override
    public List<Select2> getStreetByRegion(int region) {
        return commonMapper.getStreetByRegion(region);
    }

    @Override
    public List<Select2> getConfigSelect(String type){
        return commonMapper.getConfigSelect(type);
    }
}
