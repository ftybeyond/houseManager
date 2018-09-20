package com.qth.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.qth.model.common.DataTableReqWrapper;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

public class RepairRecord extends DataTableReqWrapper {
    private Integer id;

    private Integer residentialArea;

    private String address;

    @Deprecated
    private String developer;

    @Deprecated
    private String propertyCompany;

    @Deprecated
    private String propertyCompanyTel;

    private String org1;

    private String tel1;

    private String org2;

    private String tel2;

    private String worker;

    private String workerTel;

    @JSONField(format = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date workTime;

    private String owners;

    private String ownersTel;

    private BigDecimal moneySum;

    private Integer shareType;

    private String handler;

    private Integer state;

    @JSONField(format = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date stamp;

    private Long shareSeq;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getResidentialArea() {
        return residentialArea;
    }

    public void setResidentialArea(Integer residentialArea) {
        this.residentialArea = residentialArea;
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

    public Long getShareSeq() {
        return shareSeq;
    }

    public void setShareSeq(Long shareSeq) {
        this.shareSeq = shareSeq;
    }

    public String getOrg1() {
        return org1;
    }

    public void setOrg1(String org1) {
        this.org1 = org1;
    }

    public String getTel1() {
        return tel1;
    }

    public void setTel1(String tel1) {
        this.tel1 = tel1;
    }

    public String getOrg2() {
        return org2;
    }

    public void setOrg2(String org2) {
        this.org2 = org2;
    }

    public String getTel2() {
        return tel2;
    }

    public void setTel2(String tel2) {
        this.tel2 = tel2;
    }

    public String getWorker() {
        return worker;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }

    public String getWorkerTel() {
        return workerTel;
    }

    public void setWorkerTel(String workerTel) {
        this.workerTel = workerTel;
    }

    public Date getWorkTime() {
        return workTime;
    }

    public void setWorkTime(Date workTime) {
        this.workTime = workTime;
    }
}