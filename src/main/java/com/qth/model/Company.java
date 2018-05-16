package com.qth.model;

import com.qth.model.common.DataTableReqWrapper;

public class Company extends DataTableReqWrapper {

    private Integer id;

    private String name;

    private String legalPersonName;

    private String legalPersonLicense;

    private Integer nature;

    private String accountNum;

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

    public String getLegalPersonName() {
        return legalPersonName;
    }

    public void setLegalPersonName(String legalPersonName) {
        this.legalPersonName = legalPersonName;
    }

    public String getLegalPersonLicense() {
        return legalPersonLicense;
    }

    public void setLegalPersonLicense(String legalPersonLicense) {
        this.legalPersonLicense = legalPersonLicense;
    }

    public Integer getNature() {
        return nature;
    }

    public void setNature(Integer nature) {
        this.nature = nature;
    }

    public String getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(String accountNum) {
        this.accountNum = accountNum;
    }
}