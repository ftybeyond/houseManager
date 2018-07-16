package com.qth.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.qth.model.common.DataTableReqWrapper;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

public class ChargeCriterion extends DataTableReqWrapper {
    private Integer id;

    @JSONField(format = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date term;

    private Integer company;

    private Integer houseType;

    private Integer chargeType;

    private Integer elevatorSign;

    private BigDecimal priceRatio;

    private BigDecimal areaRatio;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getTerm() {
        return term;
    }

    public void setTerm(Date term) {
        this.term = term;
    }

    public Integer getHouseType() {
        return houseType;
    }

    public void setHouseType(Integer houseType) {
        this.houseType = houseType;
    }

    public Integer getChargeType() {
        return chargeType;
    }

    public void setChargeType(Integer chargeType) {
        this.chargeType = chargeType;
    }

    public Integer getElevatorSign() {
        return elevatorSign;
    }

    public void setElevatorSign(Integer elevatorSign) {
        this.elevatorSign = elevatorSign;
    }

    public BigDecimal getPriceRatio() {
        return priceRatio;
    }

    public void setPriceRatio(BigDecimal priceRatio) {
        this.priceRatio = priceRatio;
    }

    public BigDecimal getAreaRatio() {
        return areaRatio;
    }

    public void setAreaRatio(BigDecimal areaRatio) {
        this.areaRatio = areaRatio;
    }

    public Integer getCompany() {
        return company;
    }

    public void setCompany(Integer company) {
        this.company = company;
    }
}