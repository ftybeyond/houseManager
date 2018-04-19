package com.qth.dao;


import com.qth.model.common.Select2;

import java.util.List;

public interface CommonMapper {

    public List<Select2> getAll(String table);

    public List<Select2> getStreetByRegion(Integer region);

    public List<Select2> getConfigSelect(String type);
}
