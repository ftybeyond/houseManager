package com.qth.service;

import com.qth.model.AccountLog;
import com.qth.model.common.DataTableRspWrapper;
import com.qth.model.dto.ReportForm;

import java.util.List;

public interface IAccountLogService extends IBaseService<AccountLog>
{
    public List<AccountLog> selectAll();

    public int insertAccountLog(AccountLog accountLog);

    public int updateAccountLog(AccountLog accountLog);

    public AccountLog findAccountLogById(int id);

    public int deleteAccountLogById(int id);

    public List<AccountLog> selectByHouseCode(String code);

    public DataTableRspWrapper<AccountLog> reportDetail(ReportForm reportForm);

    public DataTableRspWrapper<AccountLog> reportSummary(ReportForm reportForm);

}
