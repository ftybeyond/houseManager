package com.qth.service.impl;

import com.qth.dao.CompanyMapper;
import com.qth.model.Company;
import com.qth.service.ICompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CompanyService extends BaseService<Company> implements ICompanyService{

    @Autowired
    CompanyMapper companyMapper;

    @Override
    public List<Company> selectAll() {
        return companyMapper.selectAll();
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
