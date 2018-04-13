package com.qth.service.impl;

import com.qth.dao.RegionMapper;
import com.qth.model.Region;
import com.qth.service.IRegionService;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RegionService implements IRegionService{

    @Autowired
    RegionMapper regionMapper;

    @Override
    public List<Region> getRegionList() {
        return regionMapper.selectAll();
    }

    @Override
    public int insertRegion() {
        return 0;
    }

    @Override
    public int updateRegion(Region region) {
        return 0;
    }

    @Override
    public int findRegionById(int id) {
        return 0;
    }

    @Override
    public int deleteRegionById(int id) {
        return 0;
    }
}
