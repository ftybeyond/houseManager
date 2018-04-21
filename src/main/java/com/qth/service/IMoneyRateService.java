package com.qth.service;

import com.qth.model.MoneyRate;

import java.util.List;

public interface IMoneyRateService extends IBaseService<MoneyRate>
{
    public List<MoneyRate> selectAll();

    public int insertMoneyRate(MoneyRate moneyRate);

    public int updateMoneyRate(MoneyRate moneyRate);

    public MoneyRate findMoneyRateById(int id);

    public int deleteMoneyRateById(int id);
}
