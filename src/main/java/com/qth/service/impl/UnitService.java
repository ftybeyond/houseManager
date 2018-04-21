package com.qth.service.impl;

import com.qth.dao.UnitMapper;
import com.qth.model.Building;
import com.qth.model.Unit;
import com.qth.model.common.DataTableRspWrapper;
import com.qth.service.IUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UnitService extends BaseService<Unit> implements IUnitService{

    @Autowired
    UnitMapper unitMapper;

    @Override
    public DataTableRspWrapper<Unit> selectDataTable2Rsp(Unit unit) {
        //声明datatable应答包装类
        DataTableRspWrapper rspWrapper = new DataTableRspWrapper();
        //设置分页信息，总条数
        rspWrapper.setRecordsTotal(unitMapper.selectDataTableCount(unit));
        //设置数据集
        rspWrapper.setData(unitMapper.selectDataTable(unit));
        return rspWrapper;
    }

    @Override
    public List<Unit> selectAll() {
        return unitMapper.selectAll();
    }

    @Override
    public int insertUnit(Unit unit) {
        return unitMapper.insert(unit);
    }

    @Override
    public int updateUnit(Unit unit) {
        return unitMapper.updateByPrimaryKey(unit);
    }

    @Override
    public Unit findUnitById(int id) {
        return unitMapper.selectByPrimaryKey(id);
    }

    @Override
    public int deleteUnitById(int id) {
        return unitMapper.deleteByPrimaryKey(id);
    }
}
