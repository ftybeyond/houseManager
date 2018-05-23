package com.qth.test;

import com.alibaba.fastjson.JSON;
import com.qth.model.MoneyRate;
import com.qth.model.dto.ReportForm;
import com.qth.util.MD5;

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
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSSSS");
//        System.out.println(sdf.format(new Date()));

//        System.out.println(MD5.EncoderByMd5("123456"));
        String s = "[\"0\",\"1\",\"4\"]";
        System.out.println(s.contains("\"0\""));
        System.out.println(ReportForm.GROUP.get(null));

        String s1 =" or ra.id = 1";
        System.out.println(s1.substring(2));
    }

}
