package com.qth.model.common;

public class DataTableReqWrapper{
    int draw = 0;

    int start = 0;

    int Length = 10;

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

}
