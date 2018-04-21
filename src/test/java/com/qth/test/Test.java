package com.qth.test;

import com.alibaba.fastjson.JSON;
import com.qth.model.MoneyRate;

import java.math.BigDecimal;

public class Test {

    public static void main(String[] args) {
        MoneyRate  moneyRate = new MoneyRate();
        moneyRate.setRate(new BigDecimal(0.000000000000212));
        System.out.println(JSON.toJSONString(moneyRate));
    }
}