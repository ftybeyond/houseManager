package com.qth.dao;

import com.qth.model.AccountLog;
import com.qth.model.RepairRecord;
import com.qth.model.dto.ReportForm;

import java.util.List;
import java.util.Map;

public interface AccountLogMapper {
    int deleteByPrimaryKey(Integer id);

    int deleteTempByRecord(Integer record);

    int insert(AccountLog record);

    int insertTemp(AccountLog accountLog);

    AccountLog selectByPrimaryKey(Integer id);

    List<AccountLog> selectAll();

    int updateByPrimaryKey(AccountLog record);

    List<AccountLog> selectByRecord(RepairRecord record);

    List<AccountLog> selectTempByRecord(Integer record);

    List<AccountLog> selectHouseChangeWithDateRange(Map map);

    List<AccountLog> selectByHouseCode(String code);

    List<AccountLog> selectReportDetail(ReportForm reportForm);

    int selectReportDetailCount(ReportForm reportForm);

    List<AccountLog> selectReportSummary(ReportForm reportForm);

    double reportSum(ReportForm reportForm);

    List<AccountLog> shareAccountDetail(AccountLog log);

    double shareSumAccountDetail(AccountLog log);

    int shareCountAccountDetail(AccountLog log);

}