package com.qth.service.impl;

import com.qth.dao.CommonMapper;
import com.qth.dao.ResidentialAreaMapper;
import com.qth.model.ResidentialArea;
import com.qth.model.common.DataTableRspWrapper;
import com.qth.service.IResidentialAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ResidentialAreaService implements IResidentialAreaService{

    @Autowired
    ResidentialAreaMapper residentialAreaMapper;

    @Override
    public List<ResidentialArea> selectAll() {
        return residentialAreaMapper.selectAll();
    }

    @Override
    public DataTableRspWrapper<ResidentialArea> selectDataTable2Rsp(ResidentialArea residentialArea) {
        //声明datatable应答包装类
        DataTableRspWrapper rspWrapper = new DataTableRspWrapper();
        //设置分页信息，总条数
        rspWrapper.setRecordsTotal(residentialAreaMapper.selectDataTableCount(residentialArea));
        //设置数据集
        rspWrapper.setData(residentialAreaMapper.selectDataTable(residentialArea));
        return rspWrapper;
    }

    @Override
    public int insertResidentialArea(ResidentialArea residentialArea) {
        return residentialAreaMapper.insert(residentialArea);
    }

    @Override
    public int updateResidentialArea(ResidentialArea residentialArea) {
        return residentialAreaMapper.updateByPrimaryKey(residentialArea);
    }

    @Override
    public ResidentialArea findResidentialAreaById(int id) {
        return residentialAreaMapper.selectByPrimaryKey(id);
    }

    @Override
    public int deleteResidentialAreaById(int id) {
        return residentialAreaMapper.deleteByPrimaryKey(id);
    }
}
