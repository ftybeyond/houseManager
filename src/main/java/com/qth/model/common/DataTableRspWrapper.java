package com.qth.model.common;

import java.util.List;

/**
 *  by fty
 * @param <T>
 *   Jquery DataTable组件查询应答数据包装类
 */
public class DataTableRspWrapper<T> {

    //Datatables发送的draw是多少那么服务器就返回多少。 这里注意，作者出于安全的考虑，强烈要求把这个转换为整形，即数字后再返回，而不是纯粹的接受然后返回，这是 为了防止跨站脚本（XSS）攻击。
    int draw;


    //即没有过滤的记录数
    int recordsTotal;
    //过滤后的记录数，没用启用自带searching，此值跟recordTotal一致,在设置recordTotal时同时赋值此值
    int recordsFiltered;

    List<T> data;

    String error;

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public int getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(int recordsTotal) {
        this.recordsTotal = recordsTotal;
        this.recordsFiltered = recordsTotal;
    }

    public int getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(int recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
