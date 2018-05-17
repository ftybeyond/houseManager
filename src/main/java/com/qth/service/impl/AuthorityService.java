package com.qth.service.impl;

import com.qth.dao.AuthorityMapper;
import com.qth.model.Authority;
import com.qth.model.Role;
import com.qth.service.IAuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AuthorityService extends BaseService<Authority> implements IAuthorityService{

    @Autowired
    AuthorityMapper authorityMapper;

    @Override
    public List<Authority> selectAll() {
        return authorityMapper.selectAll();
    }

    @Override
    public int insertAuthority(Authority authority) {
        return authorityMapper.insert(authority);
    }

    @Override
    public int updateAuthority(Authority authority) {
        return authorityMapper.updateByPrimaryKey(authority);
    }

    @Override
    public Authority findAuthorityById(int id) {
        return authorityMapper.selectByPrimaryKey(id);
    }

    @Override
    public int deleteAuthorityById(int id) {
        return authorityMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<Authority> selectByRole(Role role) {
        if(role.getAuthority()==null||role.getAuthority().trim().length()<1){
            return null;
        }
        String ids = role.getAuthority().replace("[","").replace("]","").replace("\"","");
        if(ids==null||ids.trim().length()<1){
            return null;
        }
        return authorityMapper.selectByIds(ids);
    }
}
