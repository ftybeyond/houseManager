package com.qth.dao;

import com.qth.model.User;
import java.util.List;

public interface UserMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    User selectByPrimaryKey(Integer id);

    List<User> selectAll();

    int updateByPrimaryKey(User entity);

    List<User> selectDataTable(User entity);

    int selectDataTableCount(User entity);
}