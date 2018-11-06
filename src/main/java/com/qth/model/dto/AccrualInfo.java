package com.qth.model.dto;

import java.math.BigDecimal;
import java.util.Date;

public class AccrualInfo {

    private String residentialAreaName;

    private BigDecimal accrualSum;

    private Date endTime;

    private String houseCode;

    private String houseOwner;

    private BigDecimal accountBalance;

    private BigDecimal newBalance;

    public String getResidentialAreaName() {
        return residentialAreaName;
    }

    public void setResidentialAreaName(String residentialAreaName) {
        this.residentialAreaName = residentialAreaName;
    }

    public BigDecimal getAccrualSum() {
        return accrualSum;
    }

    public void setAccrualSum(BigDecimal accrualSum) {
        this.accrualSum = accrualSum;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getHouseCode() {
        return houseCode;
    }

    public void setHouseCode(String houseCode) {
        this.houseCode = houseCode;
    }

    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(BigDecimal accountBalance) {
        this.accountBalance = accountBalance;
    }

    public BigDecimal getNewBalance() {
        if(accountBalance!=null){
            return accountBalance.add(accrualSum);
        }
        return new BigDecimal(0f);
    }

    public void setNewBalance(BigDecimal newBalance) {
        this.newBalance = newBalance;
    }

    public String getHouseOwner() {
        return houseOwner;
    }

    public void setHouseOwner(String houseOwner) {
        this.houseOwner = houseOwner;
    }
}
