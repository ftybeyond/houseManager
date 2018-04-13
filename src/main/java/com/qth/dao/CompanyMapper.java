package com.qth.dao;

import com.qth.model.Company;
import java.util.List;

public interface CompanyMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table company
     *
     * @mbg.generated Fri Apr 13 16:10:18 CST 2018
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table company
     *
     * @mbg.generated Fri Apr 13 16:10:18 CST 2018
     */
    int insert(Company record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table company
     *
     * @mbg.generated Fri Apr 13 16:10:18 CST 2018
     */
    Company selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table company
     *
     * @mbg.generated Fri Apr 13 16:10:18 CST 2018
     */
    List<Company> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table company
     *
     * @mbg.generated Fri Apr 13 16:10:18 CST 2018
     */
    int updateByPrimaryKey(Company record);
}