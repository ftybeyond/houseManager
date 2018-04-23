package com.qth.model;

import com.qth.model.common.DataTableReqWrapper;
import java.math.BigDecimal;

public class RepairItem extends DataTableReqWrapper {
    private Integer id;

    private Integer repairRecord;

    private String remark;

    private BigDecimal price;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRepairRecord() {
        return repairRecord;
    }

    public void setRepairRecord(Integer repairRecord) {
        this.repairRecord = repairRecord;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}