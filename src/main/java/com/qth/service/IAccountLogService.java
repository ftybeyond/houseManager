package com.qth.service;

import com.qth.model.AccountLog;
import com.qth.model.common.DataTableRspWrapper;
import com.qth.model.dto.ReportForm;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface IAccountLogService extends IBaseService<AccountLog>
{
    public static final String[] EXPORT_HEADER = new String[]{"产业代码","业主姓名","所属小区","楼栋","单元","层号","房号","面积","交易类型","交易时间","处理人","交易金额(元)"};

    public List<AccountLog> selectAll();

    public int insertAccountLog(AccountLog accountLog);

    public int updateAccountLog(AccountLog accountLog);

    public AccountLog findAccountLogById(int id);

    public int deleteAccountLogById(int id);

    public List<AccountLog> selectByHouseCode(String code);

    public DataTableRspWrapper<AccountLog> reportDetail(ReportForm reportForm);

    public void exportExcel(ReportForm reportForm, HttpServletResponse response, String title);

    public DataTableRspWrapper<AccountLog> reportSummary(ReportForm reportForm);

    public List<AccountLog> reportSummaryList(ReportForm reportForm);

    public Double[] reportSum(ReportForm reportForm);

}
