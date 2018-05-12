package com.qth.model;

import com.qth.model.common.DataTableReqWrapper;

public class Building extends DataTableReqWrapper {
    private Integer id;

    private String name;

    private Integer residentialArea;

    private String residentialAreaName;

    private Integer units;

    private Integer hasElevator;

    private Integer hasUnderground;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getResidentialArea() {
        return residentialArea;
    }

    public void setResidentialArea(Integer residentialArea) {
        this.residentialArea = residentialArea;
    }

    public String getResidentialAreaName() {
        return residentialAreaName;
    }

    public void setResidentialAreaName(String residentialAreaName) {
        this.residentialAreaName = residentialAreaName;
    }

    public Integer getUnits() {
        return units;
    }

    public void setUnits(Integer units) {
        this.units = units;
    }

    public Integer getHasElevator() {
        return hasElevator;
    }

    public void setHasElevator(Integer hasElevator) {
        this.hasElevator = hasElevator;
    }

    public Integer getHasUnderground() {
        return hasUnderground;
    }

    public void setHasUnderground(Integer hasUnderground) {
        this.hasUnderground = hasUnderground;
    }

}