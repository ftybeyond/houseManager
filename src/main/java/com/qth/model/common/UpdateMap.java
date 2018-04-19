package com.qth.model.common;

public class UpdateMap {

    String tableName;

    String[] cols;

    String[] props;

    Integer id;

    public UpdateMap(String tableName, String[] cols, String[] props, Integer id) {
        this.tableName = tableName;
        this.cols = cols;
        this.props = props;
        this.id = id;
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

    public String[] getProps() {
        return props;
    }

    public void setProps(String[] props) {
        this.props = props;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
