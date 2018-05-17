package com.qth.service.impl;

import com.qth.dao.RoleMapper;
import com.qth.model.Role;
import com.qth.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoleService extends BaseService<Role> implements IRoleService{

    @Autowired
    RoleMapper roleMapper;

    @Override
    public List<Role> selectAll() {
        return roleMapper.selectAll();
    }

    @Override
    public int insertRole(Role role) {
        return roleMapper.insert(role);
    }

    @Override
    public int updateRole(Role role) {
        return roleMapper.updateByPrimaryKey(role);
    }

    @Override
    public Role findRoleById(int id) {
        return roleMapper.selectByPrimaryKey(id);
    }

    @Override
    public int deleteRoleById(int id) {
        return roleMapper.deleteByPrimaryKey(id);
    }
}
