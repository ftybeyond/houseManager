package com.qth.service.impl;

import com.qth.dao.RegionMapper;
import com.qth.model.Region;
import com.qth.service.IRegionService;
import com.qth.model.common.DataTableRspWrapper;
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
    public List<Region> selectAll() {
        return regionMapper.selectAll();
    }

    @Override
    public DataTableRspWrapper<Region> selectDataTable2Rsp(Region region) {
        //声明datatable应答包装类
        DataTableRspWrapper rspWrapper = new DataTableRspWrapper();
        //设置分页信息，总条数
        rspWrapper.setRecordsTotal(regionMapper.selectDataTableCount(region));
        //设置数据集
        rspWrapper.setData(regionMapper.selectDataTable(region));
        return rspWrapper;
    }

    @Override
    public int insertRegion(Region region) {
        return regionMapper.insert(region);
    }

    @Override
    public int updateRegion(Region region) {
        return regionMapper.updateByPrimaryKey(region);
    }

    @Override
    public Region findRegionById(int id) {
        return regionMapper.selectByPrimaryKey(id);
    }

    @Override
    public int deleteRegionById(int id) {
        return regionMapper.deleteByPrimaryKey(id);
    }
}
