package com.qth.model.dto;

import com.qth.model.common.DataTableReqWrapper;

public class InvoiceForm extends DataTableReqWrapper{

    private Integer residentialArea;

    private String invoiceNum;

    private String ownerName;

    private String ownerTel;

    public Integer getResidentialArea() {
        return residentialArea;
    }

    public void setResidentialArea(Integer residentialArea) {
        this.residentialArea = residentialArea;
    }

    public String getInvoiceNum() {
        return invoiceNum;
    }

    public void setInvoiceNum(String invoiceNum) {
        this.invoiceNum = invoiceNum;
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
}
