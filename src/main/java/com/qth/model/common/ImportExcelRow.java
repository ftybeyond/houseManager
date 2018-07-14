package com.qth.model.common;

import com.qth.model.ResidentialArea;

import java.math.BigDecimal;
import java.util.Date;

public class ImportExcelRow {

    private Integer rowNum;

    private String residentialArea;

    private String building;

    private String unit;

    private String floor;

    private String house;

    private BigDecimal area;

    private BigDecimal unitPrice;

    private Integer type;

    private Integer elevator;

    private Integer nature;

    private String ownerName;

    private String ownerTel;

    private String ownerLicense;

    private BigDecimal balance;

    private Date accountTime;

    private Date accrualTime;

    private Integer patchCharge;

    private String invoiceNum;

    public Integer getRowNum() {
        return rowNum;
    }

    public void setRowNum(Integer rowNum) {
        this.rowNum = rowNum;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public BigDecimal getArea() {
        return area;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getElevator() {
        return elevator;
    }

    public void setElevator(Integer elevator) {
        this.elevator = elevator;
    }

    public Integer getNature() {
        return nature;
    }

    public void setNature(Integer nature) {
        this.nature = nature;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerTel() {
        return ownerTel;
    }

    public void setOwnerTel(String ownerTel) {
        this.ownerTel = ownerTel;
    }

    public String getOwnerLicense() {
        return ownerLicense;
    }

    public void setOwnerLicense(String ownerLicense) {
        this.ownerLicense = ownerLicense;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Date getAccrualTime() {
        return accrualTime;
    }

    public void setAccrualTime(Date accrualTime) {
        this.accrualTime = accrualTime;
    }

    public Integer getPatchCharge() {
        return patchCharge;
    }

    public void setPatchCharge(Integer patchCharge) {
        this.patchCharge = patchCharge;
    }

    public String getResidentialArea() {
        return residentialArea;
    }

    public void setResidentialArea(String residentialArea) {
        this.residentialArea = residentialArea;
    }

    public String getInvoiceNum() {
        return invoiceNum;
    }

    public void setInvoiceNum(String invoiceNum) {
        this.invoiceNum = invoiceNum;
    }

    public Date getAccountTime() {
        return accountTime;
    }

    public void setAccountTime(Date accountTime) {
        this.accountTime = accountTime;
    }
}
