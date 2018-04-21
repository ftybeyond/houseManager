package com.qth.service;

import com.qth.model.House;

import java.util.List;

public interface IHouseService extends IBaseService<House>
{
    public List<House> selectAll();

    public int insertHouse(House house);

    public int updateHouse(House house);

    public House findHouseById(int id);

    public int deleteHouseById(int id);
}
