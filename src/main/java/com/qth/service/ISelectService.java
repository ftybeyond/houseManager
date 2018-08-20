package com.qth.service;

import com.qth.model.House;
import com.qth.model.common.Select2;
import com.qth.model.common.Select2Var;
import com.qth.model.common.SelectIdstring;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface ISelectService {

    public List<Select2> getAll(String table);

    public List<Select2> getStreetByRegion(Integer region);

    public List<Select2> getResidentialAreaByRegion(Integer street);

    public List<Select2> getBuildingDataByResidentialArea(Integer residentialArea);

    public List<Select2> getUnitDataByBuilding(Integer building);

    public List<Select2Var> getFloorDataByUnit(Integer unit);

    public List<Select2> getConfigSelect(String type);

    public List<SelectIdstring> getHouseNameDataByUnitFloor(House house);
}
