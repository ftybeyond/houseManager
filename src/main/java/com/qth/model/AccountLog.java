package com.qth.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.qth.model.common.DataTableReqWrapper;
import com.qth.util.AccountLogRateChangeComparatorHelper;

import java.math.BigDecimal;
import java.util.Date;

public class AccountLog extends DataTableReqWrapper implements AccountLogRateChangeComparatorHelper{

    private Integer id;

    private String houseCode;

    private String houseOwner;

    private BigDecimal balance;

    private BigDecimal tradeMoney;

    @JSONField(format = "yyyy-MM-dd")
    private Date tradeTime;

    private Integer tradeType;

    private String handler;

    private Long seq;

    private String remark;

    //------------------以下非数据库字段，关联查询属性

    private String residentialAreaName;

    private String unitName;

    private String buildingName;

    private String houseName;

    private String houseFloor;

    private String houseArea;

    private String joinHouseCode;

    private String joinHouseOwner;

    //汇总结果字段，可以是余额，交易额等等的汇总金额
    private BigDecimal sumResult;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHouseCode() {
        return houseCode;
    }

    public void setHouseCode(String houseCode) {
        this.houseCode = houseCode;
    }

    public String getHouseOwner() {
        return houseOwner;
    }

    public void setHouseOwner(String houseOwner) {
        this.houseOwner = houseOwner;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getTradeMoney() {
        return tradeMoney;
    }

    public void setTradeMoney(BigDecimal tradeMoney) {
        this.tradeMoney = tradeMoney;
    }

    public Date getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(Date tradeTime) {
        this.tradeTime = tradeTime;
    }

    public Integer getTradeType() {
        return tradeType;
    }

    public void setTradeType(Integer tradeType) {
        this.tradeType = tradeType;
    }

    public String getHandler() {
        return handler;
    }

    public void setHandler(String handler) {
        this.handler = handler;
    }

    public Long getSeq() {
        return seq;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getResidentialAreaName() {
        return residentialAreaName;
    }

    public void setResidentialAreaName(String residentialAreaName) {
        this.residentialAreaName = residentialAreaName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public String getHouseFloor() {
        return houseFloor;
    }

    public void setHouseFloor(String houseFloor) {
        this.houseFloor = houseFloor;
    }

    public String getHouseArea() {
        return houseArea;
    }

    public void setHouseArea(String houseArea) {
        this.houseArea = houseArea;
    }

    public BigDecimal getSumResult() {
        return sumResult;
    }

    public void setSumResult(BigDecimal sumResult) {
        this.sumResult = sumResult;
    }

    @Override
    public Date compareDateElement() {
        return tradeTime;
    }

    public String getJoinHouseCode() {
        return joinHouseCode;
    }

    public void setJoinHouseCode(String joinHouseCode) {
        this.joinHouseCode = joinHouseCode;
    }

    public String getJoinHouseOwner() {
        return joinHouseOwner;
    }

    public void setJoinHouseOwner(String joinHouseOwner) {
        this.joinHouseOwner = joinHouseOwner;
    }
}