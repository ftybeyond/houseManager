package com.qth.dao;


import com.qth.model.House;
import com.qth.model.common.*;
import org.apache.ibatis.annotations.MapKey;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface BaseMapper {

    /**
     * 查询一个指定table的下拉列表，此表必须有一个id 和name字段
     * @param table
     * @return
     */
    public List<Select2> getSelect2Data(String table);

    /**
     * 根据UpdateMap 更新数据
     * Map 内容 tableName:表名
     *          cols:{"更新列1","更新列2","更新列3"}
     *          props:{"列1更新值","列2更新值","列3更新值"}
     *          id:id值
     * @param map
     * @return
     */
    public int updateByMap(UpdateMap map);

    /**
     * 根据SelectDataTableMap 查询一个map（id:{object}）
     * @param map
     * @return
     */
    @MapKey("id")
    public Map<Integer,Map<String,Object>> selectDataTable(SelectDataTableMap map);

    /**
     *  根据SelectDataTableMap 查询总行数，用于分页
     * @return
     */
    public int selectDataTableCount(SelectDataTableMap map);

    public List<Select2> getStreetByRegion(Integer region);

    public List<Select2> getResidentialArea();

    public List<Select2> getResidentialAreaByRegion(Integer street);

    public List<Select2> getConfigSelect(String type);

    public List<Select2> getBuilding();

    public List<Select2> getBuildingByResidentialArea(Integer residentialArea);

    public List<Select2> getUnit();

    public List<Select2> getUnitByBuilding(Integer building);

    public List<Select2> getFloor();

    public List<Select2Var> getFloorByUnit(Integer unit);

    public List<SelectIdstring> getHouseName();

    public List<SelectIdstring> getHouseNameByUnitFloor(House house);
}
