package com.qth.service;

import com.qth.model.AccountLog;

import java.util.List;

public interface IAccountLogService extends IBaseService<AccountLog>
{
    public List<AccountLog> selectAll();

    public int insertAccountLog(AccountLog accountLog);

    public int updateAccountLog(AccountLog accountLog);

    public AccountLog findAccountLogById(int id);

    public int deleteAccountLogById(int id);
}
