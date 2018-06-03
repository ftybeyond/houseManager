package com.qth.model.dto;

import java.math.BigDecimal;

public class InvoiceInfo {

    private String address;

    private Integer houseType;

    private String chargeItem;

    private String count;

    private String projectName;

    private String invoiceAccount;

    private String invoiceBank;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getChargeItem() {
        return chargeItem;
    }

    public void setChargeItem(String chargeItem) {
        this.chargeItem = chargeItem;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Integer getHouseType() {
        return houseType;
    }

    public void setHouseType(Integer houseType) {
        this.houseType = houseType;
    }

    public String getInvoiceAccount() {
        return invoiceAccount;
    }

    public void setInvoiceAccount(String invoiceAccount) {
        this.invoiceAccount = invoiceAccount;
    }

    public String getInvoiceBank() {
        return invoiceBank;
    }

    public void setInvoiceBank(String invoiceBank) {
        this.invoiceBank = invoiceBank;
    }
}
