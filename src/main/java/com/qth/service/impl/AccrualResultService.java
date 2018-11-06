package com.qth.service.impl;

import com.qth.dao.AccrualResultMapper;
import com.qth.model.AccrualResult;
import com.qth.model.House;
import com.qth.model.HouseTree;
import com.qth.model.common.DataTableRspWrapper;
import com.qth.model.dto.AccrualInfo;
import com.qth.service.IAccrualResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class AccrualResultService extends BaseService<AccrualResult> implements IAccrualResultService{

    @Autowired
    AccrualResultMapper accrualResultMapper;

    @Override
    public List<AccrualResult> selectAll() {
        return accrualResultMapper.selectAll();
    }

    @Override
    public int insertAccrualResult(AccrualResult accrualResult) {
        return accrualResultMapper.insert(accrualResult);
    }

    @Override
    public int updateAccrualResult(AccrualResult accrualResult) {
        return accrualResultMapper.updateByPrimaryKey(accrualResult);
    }

    @Override
    public AccrualResult findAccrualResultById(int id) {
        return accrualResultMapper.selectByPrimaryKey(id);
    }

    @Override
    public int deleteAccrualResultById(int id) {
        return accrualResultMapper.deleteByPrimaryKey(id);
    }

    @Override
    public DataTableRspWrapper<AccrualResult> selectByTree(AccrualResult accrualResult) {
        String sqlAppend = HouseService.sqlAppend(accrualResult.getPaths());
        Map map = new HashMap<>();
        map.put("state",accrualResult.getState());
        map.put("sqlAppend",sqlAppend);
        map.put("start",accrualResult.getStart());
        map.put("length",accrualResult.getLength());
        List<AccrualResult> list =  accrualResultMapper.selectResultByTree(map);
        DataTableRspWrapper rspWrapper = new DataTableRspWrapper();
        rspWrapper.setData(list);
        rspWrapper.setRecordsTotal(accrualResultMapper.selectResultCountByTree(map));
        return rspWrapper;
    }

    @Override
    public Double sumByTree(AccrualResult accrualResult) {
        String sqlAppend = HouseService.sqlAppend(accrualResult.getPaths());
        Map map = new HashMap<>();
        map.put("state",accrualResult.getState());
        map.put("sqlAppend",sqlAppend);
        return accrualResultMapper.sumResultByTree(map);
    }

    @Override
    public List<AccrualInfo> summaryResult(AccrualResult accrualResult) {
        String sqlAppend = HouseService.sqlAppend(accrualResult.getPaths());
        Map map = new HashMap<>();
        map.put("state",accrualResult.getState());
        map.put("sqlAppend",sqlAppend);
        return accrualResultMapper.summaryResultByTree(map);
    }

    @Override
    public List<AccrualResult> selectByModel(AccrualResult model) {
        List<AccrualResult> list = new ArrayList<>();
        if(model.getPaths()== null||model.getPaths().trim().length()<1){
            return list;
        }
        String[] pathsArr = model.getPaths().split(",");
        for (String path : pathsArr) {
            //解析第二层路径，每一个元素代表具体的小区、楼栋、单元等
            String[] ids = path.split("-");
            switch (ids.length - 1) {
                case HouseTree.RESIDENTIALAREA_LEVEL:
                    //指定小区下所有房屋信息
                    model.setDomainId(ids[HouseTree.RESIDENTIALAREA_LEVEL]);
                    list.addAll(accrualResultMapper.allResultInResidentialArea(model));
                    break;
                case HouseTree.BUILDING_LEVEL:
                    model.setDomainId(ids[HouseTree.BUILDING_LEVEL]);
                    list.addAll(accrualResultMapper.allResultInBuilding(model));
                    break;
                case HouseTree.UNIT_LEVEL:
                    model.setDomainId(ids[HouseTree.BUILDING_LEVEL]);
                    list.addAll(accrualResultMapper.allResultInUnit(model));
                    break;
                case HouseTree.FLOOR_LEVEL:
                    model.setAttr1(ids[HouseTree.UNIT_LEVEL]);
                    model.setAttr2(ids[HouseTree.FLOOR_LEVEL]);
                    list.addAll(accrualResultMapper.allResultInFloor(model));
                    break;
                case HouseTree.HOUSE_LEVEL:
                    model.setDomainId(ids[HouseTree.HOUSE_LEVEL]);
                    list.addAll(accrualResultMapper.allResultInHouse(model));
                    break;
                default:
                    break;
            }
        }
        return list;
    }

    @Override
    public int selectCountByModel(AccrualResult model) {
        int count = 0;
        if(model.getPaths()==null||model.getPaths().trim().length()<1){
            return count;
        }
        String[] pathsArr = model.getPaths().split(",");
        for (String path : pathsArr) {
            //解析第二层路径，每一个元素代表具体的小区、楼栋、单元等
            String[] ids = path.split("-");
            switch (ids.length - 1) {
                case HouseTree.RESIDENTIALAREA_LEVEL:
                    //指定小区下所有房屋信息
                    model.setDomainId(ids[HouseTree.RESIDENTIALAREA_LEVEL]);
                    count += accrualResultMapper.allCountResultInResidentialArea(model);
                    break;
                case HouseTree.BUILDING_LEVEL:
                    model.setDomainId(ids[HouseTree.BUILDING_LEVEL]);
                    count += accrualResultMapper.allCountResultInBuilding(model);
                    break;
                case HouseTree.UNIT_LEVEL:
                    model.setDomainId(ids[HouseTree.BUILDING_LEVEL]);
                    count += accrualResultMapper.allCountResultInUnit(model);
                    break;
                case HouseTree.FLOOR_LEVEL:
                    model.setAttr1(ids[HouseTree.UNIT_LEVEL]);
                    model.setAttr2(ids[HouseTree.FLOOR_LEVEL]);
                    count += accrualResultMapper.allCountResultInFloor(model);
                    break;
                case HouseTree.HOUSE_LEVEL:
                    model.setDomainId(ids[HouseTree.HOUSE_LEVEL]);
                    count += accrualResultMapper.allCountResultInHouse(model);
                    break;
                default:
                    break;
            }
        }
        return count;
    }

    @Override
    public int delectBatch(List<AccrualResult> list) {
        return accrualResultMapper.delectBatch(list);
    }

    @Override
    public List<AccrualInfo> selectAccrualInfoByModel(AccrualResult model) {
        List<AccrualInfo> list = new ArrayList<>();
        if(model.getPaths()== null||model.getPaths().trim().length()<1){
            return list;
        }
        String[] pathsArr = model.getPaths().split(",");
        for (String path : pathsArr) {
            //解析第二层路径，每一个元素代表具体的小区、楼栋、单元等
            String[] ids = path.split("-");
            switch (ids.length - 1) {
                case HouseTree.RESIDENTIALAREA_LEVEL:
                    //指定小区下所有房屋信息
                    model.setDomainId(ids[HouseTree.RESIDENTIALAREA_LEVEL]);
                    list.addAll(accrualResultMapper.allAccrualInfoInResidentialArea(model));
                    break;
                case HouseTree.BUILDING_LEVEL:
                    model.setDomainId(ids[HouseTree.BUILDING_LEVEL]);
                    list.addAll(accrualResultMapper.allAccrualInfoInBuilding(model));
                    break;
                case HouseTree.UNIT_LEVEL:
                    model.setDomainId(ids[HouseTree.BUILDING_LEVEL]);
                    list.addAll(accrualResultMapper.allAccrualInfoInUnit(model));
                    break;
                case HouseTree.FLOOR_LEVEL:
                    model.setAttr1(ids[HouseTree.UNIT_LEVEL]);
                    model.setAttr2(ids[HouseTree.FLOOR_LEVEL]);
                    list.addAll(accrualResultMapper.allAccrualInfoInFloor(model));
                    break;
                case HouseTree.HOUSE_LEVEL:
                    model.setDomainId(ids[HouseTree.HOUSE_LEVEL]);
                    list.addAll(accrualResultMapper.allAccrualInfoInHouse(model));
                    break;
                default:
                    break;
            }
        }
        return list;
    }
}
