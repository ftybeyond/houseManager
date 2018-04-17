package com.qth.service;

import com.qth.model.Street;
import com.qth.model.common.DataTableRspWrapper;

import java.util.List;

public interface IStreetService {

    public List<Street> selectAll();

    public DataTableRspWrapper<Street> selectDataTable2Rsp(Street street);

    public int insertStreet(Street street);

    public int updateStreet(Street street);

    public Street findStreetById(int id);

    public int deleteStreetById(int id);
}
