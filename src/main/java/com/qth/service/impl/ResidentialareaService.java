package com.qth.service.impl;

import com.qth.dao.ResidentialareaMapper;
import com.qth.dao.StreetMapper;
import com.qth.model.Residentialarea;
import com.qth.model.Street;
import com.qth.model.common.DataTableRspWrapper;
import com.qth.service.IResidentialareaService;
import com.qth.service.IStreetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResidentialareaService implements IResidentialareaService{

    @Autowired
    ResidentialareaMapper residentialareaMapper;

//    @Override
//    public List<Residentialarea> selectAll() {
//        return residentialareaMapper.selectAll();
//    }

    @Override
    public DataTableRspWrapper<Residentialarea> selectDataTable2Rsp(Residentialarea residentialarea) {
        //声明datatable应答包装类
        DataTableRspWrapper rspWrapper = new DataTableRspWrapper();
        //设置分页信息，总条数
        rspWrapper.setRecordsTotal(residentialareaMapper.selectDataTableCount(residentialarea));
        //设置数据集
        rspWrapper.setData(residentialareaMapper.selectDataTable(residentialarea));
        return rspWrapper;
    }

//    @Override
//    public int insertStreet(Street street) {
//        return streetMapper.insert(street);
//    }
//
//    @Override
//    public int updateStreet(Street street) {
//        return streetMapper.updateByPrimaryKey(street);
//    }
//
//    @Override
//    public Street findStreetById(int id) {
//        return streetMapper.selectByPrimaryKey(id);
//    }
//
//    @Override
//    public int deleteStreetById(int id) {
//        return streetMapper.deleteByPrimaryKey(id);
//    }
}
