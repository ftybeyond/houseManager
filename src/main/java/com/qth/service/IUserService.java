package com.qth.service;

import com.qth.model.User;
import com.qth.model.common.DataTableRspWrapper;

import java.util.List;

public interface IUserService
{
    public List<User> selectAll();

    public DataTableRspWrapper<User> selectDataTable2Rsp(User user);

    public int insertUser(User user);

    public int updateUser(User user);

    public User findUserById(int id);

    public int deleteUserById(int id);
}
