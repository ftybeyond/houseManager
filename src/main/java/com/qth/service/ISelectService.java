package com.qth.service;

import com.qth.model.common.Select2;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface ISelectService {

    public List<Select2> getAll(String table);

    public List<Select2> getStreetByRegion(int region);

    public List<Select2> getResidentialAreaByRegion(int street);

    public List<Select2> getConfigSelect(String type);
}
