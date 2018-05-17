package com.qth.service;

import com.qth.model.Authority;
import com.qth.model.Role;

import java.util.List;

public interface IAuthorityService extends IBaseService<Authority>
{
    public List<Authority> selectAll();

    public int insertAuthority(Authority authority);

    public int updateAuthority(Authority authority);

    public Authority findAuthorityById(int id);

    public int deleteAuthorityById(int id);

    public List<Authority> selectByRole(Role role);

}
