package com.qth.service.impl;

import com.qth.dao.AccountLogMapper;
import com.qth.model.AccountLog;
import com.qth.service.IAccountLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AccountLogService extends BaseService<AccountLog> implements IAccountLogService{

    @Autowired
    AccountLogMapper accountLogMapper;

    @Override
    public List<AccountLog> selectAll() {
        return accountLogMapper.selectAll();
    }

    @Override
    public int insertAccountLog(AccountLog accountLog) {
        return accountLogMapper.insert(accountLog);
    }

    @Override
    public int updateAccountLog(AccountLog accountLog) {
        return accountLogMapper.updateByPrimaryKey(accountLog);
    }

    @Override
    public AccountLog findAccountLogById(int id) {
        return accountLogMapper.selectByPrimaryKey(id);
    }

    @Override
    public int deleteAccountLogById(int id) {
        return accountLogMapper.deleteByPrimaryKey(id);
    }
}
