package com.qth.model.common;

import java.util.HashMap;
import java.util.Map;

public class ImportCacheNode {

    //名称键，是excel导入时填写的名称
    private String key;

    //数据库关联对象
    private Object dbObj;

    private boolean newInsert = false;

    //0:building 1:unit 2:floor 3:house
    private Integer level;

    private ImportCacheNode father;

    private Map<String,ImportCacheNode> children = new HashMap<>();

    private ImportExcelRow importExcelRow;

    public ImportCacheNode(String key, Integer level,ImportCacheNode father) {
        this.key = key;
        this.level = level;
        this.father = father;
    }


    public ImportCacheNode() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getDbObj() {
        return dbObj;
    }

    public void setDbObj(Object dbObj) {
        this.dbObj = dbObj;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public ImportExcelRow getImportExcelRow() {
        return importExcelRow;
    }

    public void setImportExcelRow(ImportExcelRow importExcelRow) {
        this.importExcelRow = importExcelRow;
    }

    public ImportCacheNode getFather() {
        return father;
    }

    public void setFather(ImportCacheNode father) {
        this.father = father;
    }

    public Map<String, ImportCacheNode> getChildren() {
        return children;
    }

    public void setChildren(Map<String, ImportCacheNode> children) {
        this.children = children;
    }

    public void addChildren(String key,ImportCacheNode cacheNode){
        children.put(key,cacheNode);
    }

    public boolean isNewInsert() {
        return newInsert;
    }

    public void setNewInsert(boolean newInsert) {
        this.newInsert = newInsert;
    }
}
