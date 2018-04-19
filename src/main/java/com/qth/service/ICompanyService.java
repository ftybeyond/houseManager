package com.qth.service;

import com.qth.model.Company;
import com.qth.model.common.DataTableRspWrapper;

import java.util.List;

public interface ICompanyService extends IBaseService<Company>
{
    public List<Company> selectAll();

    public int insertCompany(Company company);

    public int updateCompany(Company company);

    public Company findCompanyById(int id);

    public int deleteCompanyById(int id);
}
