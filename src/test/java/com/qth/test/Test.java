package com.qth.test;

import com.alibaba.fastjson.JSON;
import com.qth.model.MoneyRate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Test {

    public static void main(String[] args) {
//        MoneyRate  moneyRate = new MoneyRate();
//        moneyRate.setRate(new BigDecimal(0.000000000000212));
//        System.out.println(JSON.toJSONString(moneyRate));
//        System.out.println(new Date().getTime());
        List<Integer> list = new ArrayList<>(10);
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        System.out.println(list.size());
    }
}
