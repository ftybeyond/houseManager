package com.qth.service.impl;

import com.qth.dao.BuildingMapper;
import com.qth.model.Building;
import com.qth.model.common.DataTableRspWrapper;
import com.qth.service.IBuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BuildingService extends BaseService<Building> implements IBuildingService {

    @Autowired
    BuildingMapper buildingMapper;

    @Override
    public List<Building> selectAll() {
        return buildingMapper.selectAll();
    }

    @Override
    public DataTableRspWrapper<Building> selectDataTable2Rsp(Building building) {
        //声明datatable应答包装类
        DataTableRspWrapper rspWrapper = new DataTableRspWrapper();
        //设置分页信息，总条数
        rspWrapper.setRecordsTotal(buildingMapper.selectDataTableCount(building));
        //设置数据集
        rspWrapper.setData(buildingMapper.selectDataTable(building));
        return rspWrapper;
    }

    @Override
    public int insertBuilding(Building building) {
        return buildingMapper.insert(building);
    }

    @Override
    public int updateBuilding(Building building) {
        return buildingMapper.updateByPrimaryKey(building);
    }

    @Override
    public Building findBuildingById(int id) {
        return buildingMapper.selectByPrimaryKey(id);
    }

    @Override
    public int deleteBuildingById(int id) {
        return buildingMapper.deleteByPrimaryKey(id);
    }
}
