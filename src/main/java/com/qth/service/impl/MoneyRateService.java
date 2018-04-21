package com.qth.service.impl;

import com.qth.dao.MoneyRateMapper;
import com.qth.model.MoneyRate;
import com.qth.service.IMoneyRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MoneyRateService extends BaseService<MoneyRate> implements IMoneyRateService{

    @Autowired
    MoneyRateMapper moneyRateMapper;

    @Override
    public List<MoneyRate> selectAll() {
        return moneyRateMapper.selectAll();
    }

    @Override
    public int insertMoneyRate(MoneyRate moneyRate) {
        return moneyRateMapper.insert(moneyRate);
    }

    @Override
    public int updateMoneyRate(MoneyRate moneyRate) {
        return moneyRateMapper.updateByPrimaryKey(moneyRate);
    }

    @Override
    public MoneyRate findMoneyRateById(int id) {
        return moneyRateMapper.selectByPrimaryKey(id);
    }

    @Override
    public int deleteMoneyRateById(int id) {
        return moneyRateMapper.deleteByPrimaryKey(id);
    }
}
