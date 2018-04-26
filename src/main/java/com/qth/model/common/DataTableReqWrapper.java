package com.qth.model.common;

/**
 * by fty
 * DataTables 组件通用查询对象 基类
 * 实体对象继承此类可以直接作为查询对象进行数据表格的查询条件
 */
public class DataTableReqWrapper{

    //表格绘制次数，返回给DataTables组件使用
    int draw = 0;

    //分页起始下标
    int start = 0;

    //分页条数
    int Length = 10;

    String supplement;//补充内容、当查询请求还带有其他数据时，可用此字段传递json字符串

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLength() {
        return Length;
    }

    public void setLength(int length) {
        Length = length;
    }

    public String getSupplement() {
        return supplement;
    }

    public void setSupplement(String supplement) {
        this.supplement = supplement;
    }
}
