package com.qth.service.impl;

import com.qth.dao.UserMapper;
import com.qth.model.User;
import com.qth.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService extends BaseService<User> implements IUserService{

    @Autowired
    UserMapper userMapper;

    @Override
    public List<User> selectAll() {
        return userMapper.selectAll();
    }

    @Override
    public int insertUser(User user) {
        return userMapper.insert(user);
    }

    @Override
    public int updateUser(User user) {
        return userMapper.updateByPrimaryKey(user);
    }

    @Override
    public User findUserById(int id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public int deleteUserById(int id) {
        return userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public User checkPassword(User user) {
        return userMapper.checkPassword(user);
    }

    @Override
    public int updatePassword(User user) {
        return userMapper.updatePassword(user);
    }


}
