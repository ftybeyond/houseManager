package com.qth.dao;

import com.qth.model.ChargeBill;
import java.util.List;

public interface ChargeBillMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChargeBill record);

    ChargeBill selectByPrimaryKey(Integer id);

    List<ChargeBill> selectAll();

    int updateByPrimaryKey(ChargeBill record);
}