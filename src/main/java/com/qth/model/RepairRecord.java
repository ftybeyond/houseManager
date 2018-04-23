package com.qth.model;

import com.qth.model.common.DataTableReqWrapper;
import java.math.BigDecimal;
import java.util.Date;

public class RepairRecord extends DataTableReqWrapper {
    private Integer id;

    private Integer resideentialArea;

    private String address;

    private String developer;

    private String propertyCompany;

    private String propertyCompanyTel;

    private String owners;

    private String ownersTel;

    private BigDecimal moneySum;

    private Integer shareType;

    private String handler;

    private Integer state;

    private Date stamp;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getResideentialArea() {
        return resideentialArea;
    }

    public void setResideentialArea(Integer resideentialArea) {
        this.resideentialArea = resideentialArea;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public String getPropertyCompany() {
        return propertyCompany;
    }

    public void setPropertyCompany(String propertyCompany) {
        this.propertyCompany = propertyCompany;
    }

    public String getPropertyCompanyTel() {
        return propertyCompanyTel;
    }

    public void setPropertyCompanyTel(String propertyCompanyTel) {
        this.propertyCompanyTel = propertyCompanyTel;
    }

    public String getOwners() {
        return owners;
    }

    public void setOwners(String owners) {
        this.owners = owners;
    }

    public String getOwnersTel() {
        return ownersTel;
    }

    public void setOwnersTel(String ownersTel) {
        this.ownersTel = ownersTel;
    }

    public BigDecimal getMoneySum() {
        return moneySum;
    }

    public void setMoneySum(BigDecimal moneySum) {
        this.moneySum = moneySum;
    }

    public Integer getShareType() {
        return shareType;
    }

    public void setShareType(Integer shareType) {
        this.shareType = shareType;
    }

    public String getHandler() {
        return handler;
    }

    public void setHandler(String handler) {
        this.handler = handler;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getStamp() {
        return stamp;
    }

    public void setStamp(Date stamp) {
        this.stamp = stamp;
    }
}