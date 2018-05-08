package com.qth.model.common;

import java.util.Map;

public class SelectDataTableMap extends DataTableReqWrapper{

    public static final String ORDER_AES = "asc";

    public static final String ORDER_DESC = "desc";

    //查询的表名
    private String tableName;

    //要查询的列,null时为查询所有列
    private String[] cols;

    //要查询的条件映射,null时为无条件查询
    private Map<String,String> conditions;

    //排序
    private Map<String,String> orders;


    public SelectDataTableMap(String tableName, String[] cols, Map<String, String> conditions) {
        this.tableName = tableName;
        this.cols = cols;
        this.conditions = conditions;
    }

    public SelectDataTableMap(String tableName, Map<String, String> conditions) {
        this.tableName = tableName;
        this.cols = null;
        this.conditions = conditions;
    }

    public SelectDataTableMap(String tableName) {
        this.tableName = tableName;
        this.cols = null;
        this.conditions = null;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String[] getCols() {
        return cols;
    }

    public void setCols(String[] cols) {
        this.cols = cols;
    }

    public Map<String, String> getConditions() {
        return conditions;
    }

    public void setConditions(Map<String, String> conditions) {
        this.conditions = conditions;
    }

    public Map<String, String> getOrders() {
        return orders;
    }

    public void setOrders(Map<String, String> orders) {
        this.orders = orders;
    }
}
