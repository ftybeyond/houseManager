package com.qth.test;

import com.alibaba.fastjson.JSON;
import com.qth.model.MoneyRate;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Test {

    public static void main(String[] args) {
//        MoneyRate  moneyRate = new MoneyRate();
//        moneyRate.setRate(new BigDecimal(0.000000000000212));
//        System.out.println(JSON.toJSONString(moneyRate));
//        System.out.println(new Date().getTime());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSSSS");
        System.out.println(sdf.format(new Date()));
    }
}
