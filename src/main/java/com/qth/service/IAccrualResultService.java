package com.qth.service;

import com.qth.model.AccrualResult;
import com.qth.model.common.DataTableRspWrapper;
import com.qth.model.dto.AccrualInfo;

import java.util.List;

public interface IAccrualResultService extends IBaseService<AccrualResult>
{
    public List<AccrualResult> selectAll();

    public int insertAccrualResult(AccrualResult accrualResult);

    public int updateAccrualResult(AccrualResult accrualResult);

    public AccrualResult findAccrualResultById(int id);

    public int deleteAccrualResultById(int id);

    public DataTableRspWrapper<AccrualResult> selectByTree(AccrualResult accrualResult);

    public Double sumByTree(AccrualResult accrualResult);

    public List<AccrualInfo> summaryResult(AccrualResult accrualResult);

    @Deprecated
    public List<AccrualResult> selectByModel(AccrualResult model);

    @Deprecated
    public int selectCountByModel(AccrualResult model);

    public int delectBatch(List<AccrualResult> list);

    @Deprecated
    public List<AccrualInfo> selectAccrualInfoByModel(AccrualResult accrualResult);

}
