package com.qth.service.impl;

import com.qth.dao.HouseMapper;
import com.qth.model.House;
import com.qth.model.Unit;
import com.qth.model.common.DataTableRspWrapper;
import com.qth.service.IHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class HouseService extends BaseService<House> implements IHouseService{

    @Autowired
    HouseMapper houseMapper;

    @Override
    public DataTableRspWrapper<House> selectDataTable2Rsp(House house) {
        //声明datatable应答包装类
        DataTableRspWrapper rspWrapper = new DataTableRspWrapper();
        //设置分页信息，总条数
        rspWrapper.setRecordsTotal(houseMapper.selectDataTableCount(house));
        //设置数据集
        rspWrapper.setData(houseMapper.selectDataTable(house));
        return rspWrapper;
    }

    @Override
    public List<House> selectAll() {
        return houseMapper.selectAll();
    }

    @Override
    public int insertHouse(House house) {
        return houseMapper.insert(house);
    }

    @Override
    public int updateHouse(House house) {
        return houseMapper.updateByPrimaryKey(house);
    }

    @Override
    public House findHouseById(int id) {
        return houseMapper.selectByPrimaryKey(id);
    }

    @Override
    public int deleteHouseById(int id) {
        return houseMapper.deleteByPrimaryKey(id);
    }
}
