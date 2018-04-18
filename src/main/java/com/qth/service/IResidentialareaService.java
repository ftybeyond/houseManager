package com.qth.service;

import com.qth.model.Residentialarea;
import com.qth.model.Street;
import com.qth.model.common.DataTableRspWrapper;

import java.util.List;

public interface IResidentialareaService {

//    public List<Residentialarea> selectAll();

    public DataTableRspWrapper<Residentialarea> selectDataTable2Rsp(Residentialarea street);

//    public int insertStreet(Street street);
//
//    public int updateStreet(Street street);
//
//    public Street findStreetById(int id);
//
//    public int deleteStreetById(int id);
}
