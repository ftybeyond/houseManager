package com.qth.service.impl;

import com.qth.dao.BaseMapper;
import com.qth.model.common.Select2;
import com.qth.service.ISelectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SelectService implements ISelectService {

    @Autowired
    private BaseMapper baseMapper;

    @Override
    public List<Select2> getAll(String table) {
        return baseMapper.getSelect2Data(table);
    }
}
