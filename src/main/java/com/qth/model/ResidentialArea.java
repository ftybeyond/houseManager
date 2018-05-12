package com.qth.model;

import com.qth.model.common.DataTableReqWrapper;

import java.math.BigDecimal;

public class ResidentialArea extends DataTableReqWrapper {
    private Integer id;

    private String name;

    private Integer company;

    private Integer region;

    private String regionName;

    private Integer street;

    private String streetName;

    private String address;

    private BigDecimal areaElevator;

    private BigDecimal areaNoelevator;

    private Integer nature;

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

    public Integer getCompany() {
        return company;
    }

    public void setCompany(Integer company) {
        this.company = company;
    }

    public Integer getRegion() {
        return region;
    }

    public void setRegion(Integer region) {
        this.region = region;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public Integer getStreet() {
        return street;
    }

    public void setStreet(Integer street) {
        this.street = street;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getAreaElevator() {
        return areaElevator;
    }

    public void setAreaElevator(BigDecimal areaElevator) {
        this.areaElevator = areaElevator;
    }

    public BigDecimal getAreaNoelevator() {
        return areaNoelevator;
    }

    public void setAreaNoelevator(BigDecimal areaNoelevator) {
        this.areaNoelevator = areaNoelevator;
    }

    public Integer getNature() {
        return nature;
    }

    public void setNature(Integer nature) {
        this.nature = nature;
    }

}