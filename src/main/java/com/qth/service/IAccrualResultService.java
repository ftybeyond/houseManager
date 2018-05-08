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

    public List<AccrualResult> selectByModel(AccrualResult model);

    public int selectCountByModel(AccrualResult model);

    public int delectBatch(List<AccrualResult> list);

    public List<AccrualInfo> selectAccrualInfoByModel(AccrualResult accrualResult);

}
