package com.qth.service;

import com.qth.model.AccrualResult;

import java.util.Date;
import java.util.List;

public interface IAccrualService {

    int accrualCalculate(String paths,Date fromDate,String handler);

    int accrualBack(AccrualResult model);

    public int billBatch(AccrualResult model,String accountDate,String handler);

}
