package com.qth.service.impl;

import com.qth.dao.HouseMapper;
import com.qth.model.House;
import com.qth.service.IHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class HouseService extends BaseService<House> implements IHouseService{

    @Autowired
    HouseMapper houseMapper;

    @Override
    public List<House> selectAll() {
        return houseMapper.selectAll();
    }

    @Override
    public int insertHouse(House house) {
        return houseMapper.insert(house);
    }

    @Override
    public int updateHouse(House house) {
        return houseMapper.updateByPrimaryKey(house);
    }

    @Override
    public House findHouseById(int id) {
        return houseMapper.selectByPrimaryKey(id);
    }

    @Override
    public int deleteHouseById(int id) {
        return houseMapper.deleteByPrimaryKey(id);
    }
}
