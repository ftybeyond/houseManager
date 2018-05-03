package com.qth.model.common;

/**
 * by fty
 * zTree空间节点模型
 */
public class ZTreeModel {

    private Integer id;

    private String name;

    private Integer leaves=-1;

    private Boolean checked;

    private String isParent;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLeaves() {
        return leaves;
    }

    public void setLeaves(Integer leaves) {
        this.leaves = leaves;
    }

    public String getIsParent() {
        if(leaves>0){
            return "true";
        }else{
            return "false";
        }
    }

    public void setIsParent(String isParent) {
        this.isParent = isParent;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }
}
