package com.qth.test;

import com.alibaba.fastjson.JSON;
import com.qth.model.MoneyRate;
import com.qth.model.dto.ReportForm;
import com.qth.util.MD5;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Test {

    public static void main(String[] args) {
//        MoneyRate  moneyRate = new MoneyRate();
//        moneyRate.setRate(new BigDecimal(0.000000000000212));
//        System.out.println(JSON.toJSONString(moneyRate));
//        System.out.println(new Date().getTime());
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSSSS");
//        System.out.println(sdf.format(new Date()));

//        System.out.println(MD5.EncoderByMd5("123456"));
//        String s = "[\"0\",\"1\",\"4\"]";
//        System.out.println(s.contains("\"0\""));
//        System.out.println(ReportForm.GROUP.get(null));
//
//        String s1 =" or ra.id = 1";
//        System.out.println(s1.substring(2));
//
//        String s2 = "123.xls";
//        System.out.println(s2.split("\\.")[1]);
//
//        Map<String,String> map = new HashMap<>();
//        map.put("1","aaa");
//        map.put("1","bbb");
//        map.put("3",null);
//        System.out.println(map);
       BigDecimal decimal = new BigDecimal(0.32823123123123123);
        DecimalFormat df2 =new DecimalFormat("0.00");
        System.out.println(df2.format(decimal));
    }

}
