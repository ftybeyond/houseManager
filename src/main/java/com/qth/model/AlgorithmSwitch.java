package com.qth.model;

import com.qth.model.common.DataTableReqWrapper;

public class AlgorithmSwitch extends DataTableReqWrapper {
    private Integer id;

    private String name;

    private Integer chargeSwitch;

    private Integer paySwitch;

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

    public Integer getChargeSwitch() {
        return chargeSwitch;
    }

    public void setChargeSwitch(Integer chargeSwitch) {
        this.chargeSwitch = chargeSwitch;
    }

    public Integer getPaySwitch() {
        return paySwitch;
    }

    public void setPaySwitch(Integer paySwitch) {
        this.paySwitch = paySwitch;
    }
}