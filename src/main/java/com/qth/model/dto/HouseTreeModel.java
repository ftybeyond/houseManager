package com.qth.model.dto;

import com.qth.model.common.ZTreeNodeReq;

public class HouseTreeModel extends ZTreeNodeReq{

    private Integer start;

    private Integer length;

    private Integer draw;

    private String ownerName;

    private String houseCode;

    private Boolean hasOwner;

    private String sqlAppend;

    private String accountDateStart;

    private String accountDateEnd;

    /**
     * 1：收缴回退
     * 2：收缴登帐
     */
    private int sign;

    private String paths;

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getDraw() {
        return draw;
    }

    public void setDraw(Integer draw) {
        this.draw = draw;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getHouseCode() {
        return houseCode;
    }

    public void setHouseCode(String houseCode) {
        this.houseCode = houseCode;
    }

    public Boolean getHasOwner() {
        return hasOwner;
    }

    public void setHasOwner(Boolean hasOwner) {
        this.hasOwner = hasOwner;
    }

    public String getAccountDateStart() {
        return accountDateStart;
    }

    public void setAccountDateStart(String accountDateStart) {
        this.accountDateStart = accountDateStart;
    }

    public String getAccountDateEnd() {
        return accountDateEnd;
    }

    public void setAccountDateEnd(String accountDateEnd) {
        this.accountDateEnd = accountDateEnd;
    }

    public String getSqlAppend() {
        return sqlAppend;
    }

    public void setSqlAppend(String sqlAppend) {
        this.sqlAppend = sqlAppend;
    }

    public int getSign() {
        return sign;
    }

    public void setSign(int sign) {
        this.sign = sign;
    }

    public String getPaths() {
        return paths;
    }

    public void setPaths(String paths) {
        this.paths = paths;
    }
}
