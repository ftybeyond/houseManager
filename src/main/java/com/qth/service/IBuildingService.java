package com.qth.service;

import com.qth.model.Building;
import com.qth.model.ResidentialArea;
import com.qth.model.common.DataTableRspWrapper;

import java.util.List;

public interface IBuildingService extends IBaseService<Building>
{
    public List<Building> selectAll();

    public DataTableRspWrapper<Building> selectDataTable2Rsp(Building building);

    public int insertBuilding(Building building);

    public int updateBuilding(Building building);

    public Building findBuildingById(int id);

    public int deleteBuildingById(int id);
}
