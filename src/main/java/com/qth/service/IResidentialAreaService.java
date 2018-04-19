package com.qth.service;

import com.qth.model.ResidentialArea;
import com.qth.model.common.DataTableRspWrapper;

import java.util.List;

public interface IResidentialAreaService
{
    public List<ResidentialArea> selectAll();

    public DataTableRspWrapper<ResidentialArea> selectDataTable2Rsp(ResidentialArea residentialArea);

    public int insertResidentialArea(ResidentialArea residentialArea);

    public int updateResidentialArea(ResidentialArea residentialArea);

    public ResidentialArea findResidentialAreaById(int id);

    public int deleteResidentialAreaById(int id);
}
