package com.qth.service.impl;

import com.qth.dao.BaseMapper;
import com.qth.model.House;
import com.qth.model.common.Select2;
import com.qth.model.common.SelectIdstring;
import com.qth.service.ISelectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SelectService implements ISelectService {

    @Autowired
    private BaseMapper baseMapper;

    @Override
    public List<Select2> getAll(String table) {
        return baseMapper.getSelect2Data(table);
    }

    @Override
    public List<Select2> getStreetByRegion(Integer region) {
        return baseMapper.getStreetByRegion(region);
    }

    @Override
    public List<Select2> getResidentialAreaByRegion(Integer street) {
        if (street == null || street == 0) {
            return baseMapper.getResidentialArea();
        } else {
            return baseMapper.getResidentialAreaByRegion(street);
        }
    }

    @Override
    public List<Select2> getBuildingDataByResidentialArea(Integer residentialArea) {
        if (residentialArea == null || residentialArea == 0) {
            return baseMapper.getBuilding();
        } else {
            return baseMapper.getBuildingByResidentialArea(residentialArea);
        }
    }

    @Override
    public List<Select2> getUnitDataByBuilding(Integer building) {
        if (building == null || building == 0) {
            return baseMapper.getUnit();
        } else {
            return baseMapper.getUnitByBuilding(building);
        }
    }

    @Override
    public List<Select2> getFloorDataByUnit(Integer unit) {
        if (unit == null || unit == 0) {
            return baseMapper.getFloor();
        } else {
            return baseMapper.getFloorByUnit(unit);
        }
    }

    @Override
    public List<SelectIdstring> getHouseNameDataByUnitFloor(House house) {
        if (house.getUnit() == null || house.getUnit() == 0) {
            List<SelectIdstring> a = baseMapper.getHouseName();
                return baseMapper.getHouseName();
        } else {
            return baseMapper.getHouseNameByUnitFloor(house);
        }
    }

    @Override
    public List<Select2> getConfigSelect(String type) {
        return baseMapper.getConfigSelect(type);
    }
}
