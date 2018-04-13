package com.qth.service;

import com.qth.model.Region;

import java.util.List;

public interface IRegionService
{
    public List<Region> getRegionList();

    public int insertRegion();

    public int updateRegion(Region region);

    public int findRegionById(int id);

    public int deleteRegionById(int id);
}
