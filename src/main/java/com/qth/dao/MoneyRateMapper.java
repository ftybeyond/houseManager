package com.qth.dao;

import com.qth.model.MoneyRate;
import java.util.List;

public interface MoneyRateMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MoneyRate record);

    MoneyRate selectByPrimaryKey(Integer id);

    List<MoneyRate> selectAll();

    int updateByPrimaryKey(MoneyRate record);
}