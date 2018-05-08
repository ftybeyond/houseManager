package com.qth.dao;

import com.qth.model.AccountLog;
import com.qth.model.RepairRecord;

import java.util.List;
import java.util.Map;

public interface AccountLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AccountLog record);

    AccountLog selectByPrimaryKey(Integer id);

    List<AccountLog> selectAll();

    int updateByPrimaryKey(AccountLog record);

    List<AccountLog> selectByRecord(RepairRecord record);

    List<AccountLog> selectHouseChangeWithDateRange(Map map);
}