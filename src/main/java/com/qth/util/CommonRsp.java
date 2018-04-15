package com.qth.util;

import java.util.List;

/**
 * by fty
 * 通用Json应答实体，只包含响应状态,应答码，响应描述信息
 *
 */
public class CommonRsp {
    //应答状态
    boolean success;

    //返回码,根据实际业务定制返回码
    String resultCode;

    //响应描述
    String description;

    //集合数据
    List dataList;

    //对象数据
    Object data;

    public CommonRsp() {
        
    }

    public CommonRsp(boolean success, String resultCode, String description) {
        this.success = success;
        this.resultCode = resultCode;
        this.description = description;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List getDataList() {
        return dataList;
    }

    public void setDataList(List dataList) {
        this.dataList = dataList;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
