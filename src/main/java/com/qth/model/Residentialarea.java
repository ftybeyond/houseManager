package com.qth.model;

import com.qth.model.common.DataTableReqWrapper;

public class Residentialarea extends DataTableReqWrapper {
    Integer id;
    String name;
    Integer company;
    Integer street;
    String address;
    Double areaelevator;
    Double areanoelevator;
    Integer nature;

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

    public Integer getStreet() {
        return street;
    }

    public void setStreet(Integer street) {
        this.street = street;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getAreaelevator() {
        return areaelevator;
    }

    public void setAreaelevator(Double areaelevator) {
        this.areaelevator = areaelevator;
    }

    public Double getAreanoelevator() {
        return areanoelevator;
    }

    public void setAreanoelevator(Double areanoelevator) {
        this.areanoelevator = areanoelevator;
    }

    public Integer getNature() {
        return nature;
    }

    public void setNature(Integer nature) {
        this.nature = nature;
    }
}