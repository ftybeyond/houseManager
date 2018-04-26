package com.qth.model;

import com.qth.model.common.DataTableReqWrapper;

import java.math.BigDecimal;

public class House extends DataTableReqWrapper {
    private Integer id;

    private String name;

    private Integer residentialArea;

    private String residentialAreaName;

    private Integer building;

    private String buildingName;

    private Integer unit;

    private String unitName;

    private String floor;

    private String code;

    private BigDecimal area;

    private Integer hasElevator;

    private String hasElevatorName;

    private Integer nature;

    private String natureName;

    private Integer type;

    private String typeName;

    private Integer buildingRise;

    private BigDecimal unitPrice;

    private String ownerName;

    private Integer ownerPsptid;

    private BigDecimal accountBalance;

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

    public Integer getUnit() {
        return unit;
    }

    public void setUnit(Integer unit) {
        this.unit = unit;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getArea() {
        return area;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    public Integer getHasElevator() {
        return hasElevator;
    }

    public void setHasElevator(Integer hasElevator) {
        this.hasElevator = hasElevator;
    }

    public String getHasElevatorName() {
        return hasElevatorName;
    }

    public void setHasElevatorName(String hasElevatorName) {
        this.hasElevatorName = hasElevatorName;
    }

    public Integer getNature() {
        return nature;
    }

    public void setNature(Integer nature) {
        this.nature = nature;
    }

    public String getNatureName() {
        return natureName;
    }

    public void setNatureName(String natureName) {
        this.natureName = natureName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getBuildingRise() {
        return buildingRise;
    }

    public void setBuildingRise(Integer buildingRise) {
        this.buildingRise = buildingRise;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Integer getOwnerPsptid() {
        return ownerPsptid;
    }

    public void setOwnerPsptid(Integer ownerPsptid) {
        this.ownerPsptid = ownerPsptid;
    }

    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(BigDecimal accountBalance) {
        this.accountBalance = accountBalance;
    }
}