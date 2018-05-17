package com.qth.service;

import com.qth.model.User;

import java.util.List;

public interface IUserService extends IBaseService<User>
{
    public List<User> selectAll();

    public int insertUser(User user);

    public int updateUser(User user);

    public User findUserById(int id);

    public int deleteUserById(int id);

    public User checkPassword(User user);

    public int updatePassword(User user);
}
