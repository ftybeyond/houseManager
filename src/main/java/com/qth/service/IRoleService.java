package com.qth.service;

import com.qth.model.Role;

import java.util.List;

public interface IRoleService extends IBaseService<Role>
{
    public List<Role> selectAll();

    public int insertRole(Role role);

    public int updateRole(Role role);

    public Role findRoleById(int id);

    public int deleteRoleById(int id);
}
