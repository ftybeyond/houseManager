package com.qth.dao;

import com.qth.model.Authority;
import java.util.List;

public interface AuthorityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Authority record);

    Authority selectByPrimaryKey(Integer id);

    List<Authority> selectAll();

    int updateByPrimaryKey(Authority record);

    List<Authority> selectByIds(String ids);
}