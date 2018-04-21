package com.qth.model;

import com.qth.model.common.DataTableReqWrapper;

public class Unit extends DataTableReqWrapper {
    private Integer id;

    private String name;

    private Integer residentialArea;

    private String residentialAreaName;

    private Integer building;

    private String buildingName;

    private Integer totalFloor;

    private Integer housePerFloor;

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

    public Integer getBuilding() {
        return building;
    }

    public void setBuilding(Integer building) {
        this.building = building;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public Integer getTotalFloor() {
        return totalFloor;
    }

    public void setTotalFloor(Integer totalFloor) {
        this.totalFloor = totalFloor;
    }

    public Integer getHousePerFloor() {
        return housePerFloor;
    }

    public void setHousePerFloor(Integer housePerFloor) {
        this.housePerFloor = housePerFloor;
    }
}