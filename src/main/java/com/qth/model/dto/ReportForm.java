package com.qth.model.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.qth.model.common.DataTableReqWrapper;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ReportForm extends DataTableReqWrapper{

    public static Map<Integer,String> GROUP;

    static{
        GROUP = new HashMap<>();
        GROUP.put(1,"ra.id");
        GROUP.put(2,"b.id");
        GROUP.put(3,"u.id");
        GROUP.put(4,"h.id");
    }

    //树型节点参数
    private String paths;

    @JSONField(format = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fromDate;

    @JSONField(format = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    //分组区域 小区，楼栋，单元，楼层，房号
    private Integer summaryGroup;

    //查询类型：全部，使用、计息、收缴、未缴、返还
    private Integer summaryType;

    private Long seq;

    //根据paths生成的or条件参数集合
    private String sqlAppend;

    private String groupByColumn;

    public String getGroupByColumn() {
        return GROUP.get(this.summaryGroup);
    }

    public String getPaths() {
        return paths;
    }

    public void setPaths(String paths) {
        this.paths = paths;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getSummaryGroup() {
        return summaryGroup;
    }

    public void setSummaryGroup(Integer summaryGroup) {
        this.summaryGroup = summaryGroup;
    }

    public Integer getSummaryType() {
        return summaryType;
    }

    public void setSummaryType(Integer summaryType) {
        this.summaryType = summaryType;
    }

    public String getSqlAppend() {
        return sqlAppend;
    }

    public void setSqlAppend(String sqlAppend) {
        this.sqlAppend = sqlAppend;
    }

    public Long getSeq() {
        return seq;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }
}
