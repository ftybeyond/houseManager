package com.qth.service.impl;

import com.qth.dao.CommonMapper;
import com.qth.dao.CompanyMapper;
import com.qth.model.Company;
import com.qth.model.common.DataTableRspWrapper;
import com.qth.service.ICompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CompanyService implements ICompanyService{

    @Autowired
    CompanyMapper companyMapper;

    @Override
    public List<Company> selectAll() {
        return companyMapper.selectAll();
    }

    @Override
    public DataTableRspWrapper<Company> selectDataTable2Rsp(Company company) {
        //声明datatable应答包装类
        DataTableRspWrapper rspWrapper = new DataTableRspWrapper();
        //设置分页信息，总条数
        rspWrapper.setRecordsTotal(companyMapper.selectDataTableCount(company));
        //设置数据集
        rspWrapper.setData(companyMapper.selectDataTable(company));
        return rspWrapper;
    }

    @Override
    public int insertCompany(Company company) {
        return companyMapper.insert(company);
    }

    @Override
    public int updateCompany(Company company) {
        return companyMapper.updateByPrimaryKey(company);
    }

    @Override
    public Company findCompanyById(int id) {
        return companyMapper.selectByPrimaryKey(id);
    }

    @Override
    public int deleteCompanyById(int id) {
        return companyMapper.deleteByPrimaryKey(id);
    }
}
