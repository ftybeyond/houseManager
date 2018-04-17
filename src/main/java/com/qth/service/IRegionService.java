package com.qth.service;

import com.qth.model.Region;
import com.qth.model.common.DataTableRspWrapper;

import java.util.List;

public interface IRegionService
{
    public List<Region> selectAll();

    public DataTableRspWrapper<Region> selectDataTable2Rsp(Region region);

    public int insertRegion(Region region);

    public int updateRegion(Region region);

    public Region findRegionById(int id);

    public int deleteRegionById(int id);
}
