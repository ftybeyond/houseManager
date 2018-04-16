package com.qth.service.impl;

import com.qth.dao.StreetMapper;
import com.qth.model.Street;
import com.qth.service.IStreetService;
import com.qth.util.DataTableRspWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StreetService implements IStreetService{

    @Autowired
    StreetMapper streetMapper;

    @Override
    public List<Street> selectAll() {
        return streetMapper.selectAll();
    }

    @Override
    public DataTableRspWrapper<Street> selectDataTable2Rsp(Street street) {
        //声明datatable应答包装类
        DataTableRspWrapper rspWrapper = new DataTableRspWrapper();
        //设置分页信息，总条数
        rspWrapper.setRecordsTotal(streetMapper.selectDataTableCount(street));
        //设置数据集
        rspWrapper.setData(streetMapper.selectDataTable(street));
        return rspWrapper;
    }

    @Override
    public int insertStreet(Street street) {
        return streetMapper.insert(street);
    }

    @Override
    public int updateStreet(Street street) {
        return streetMapper.updateByPrimaryKey(street);
    }

    @Override
    public Street findStreetById(int id) {
        return streetMapper.selectByPrimaryKey(id);
    }

    @Override
    public int deleteStreetById(int id) {
        return streetMapper.deleteByPrimaryKey(id);
    }
}
