package com.qth.dao;

import com.qth.model.Company;
import java.util.List;

public interface CompanyMapper extends BaseMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Company record);

    Company selectByPrimaryKey(Integer id);

    List<Company> selectAll();

    int updateByPrimaryKey(Company record);

}