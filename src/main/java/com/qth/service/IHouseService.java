package com.qth.service;

import com.qth.model.House;
import com.qth.model.common.DataTableRspWrapper;

import java.util.List;

public interface IHouseService extends IBaseService<House>
{
    public DataTableRspWrapper<House> selectDataTable2Rsp(House house);

    public List<House> selectAll();

    public int insertHouse(House house);

    public int updateHouse(House house);

    public House findHouseById(int id);

    public int deleteHouseById(int id);
}
