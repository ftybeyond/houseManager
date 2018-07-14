package com.qth.dao;

import com.qth.model.ResidentialArea;
import java.util.List;

public interface ResidentialAreaMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ResidentialArea record);

    ResidentialArea selectByPrimaryKey(Integer id);

    List<ResidentialArea> selectAll();

    int updateByPrimaryKey(ResidentialArea record);

    List<ResidentialArea> selectDataTable(ResidentialArea entity);

    int selectDataTableCount(ResidentialArea entity);

    ResidentialArea findByName(String name);
}