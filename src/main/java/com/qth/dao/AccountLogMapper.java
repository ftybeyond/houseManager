package com.qth.dao;

import com.qth.model.AccountLog;
import java.util.List;

public interface AccountLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AccountLog record);

    AccountLog selectByPrimaryKey(Integer id);

    List<AccountLog> selectAll();

    int updateByPrimaryKey(AccountLog record);
}