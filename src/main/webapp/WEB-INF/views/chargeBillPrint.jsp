<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
    <title>.</title>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <script type="text/javascript" src="/vendors/jquery/1.x/jquery.min.js"></script>
    <script type="text/javascript" src="/vendors/jquery/jquery-migrate-1.2.1.min.js"></script>
    <script type="text/javascript" src="/vendors/jquery/jquery.jqprint-0.3.js"></script>
    <link rel="stylesheet" type="text/css" href="/vendors/jquery/print.css"/>
    <script type="text/javascript">
        function print(){
            $("div.container").jqprint();
        }
    </script>
</head>
<body>
<div class="container">
    <h2>${printInfo.state==2?"补缴单":"收缴单"}</h2>
    <table width="100%" border="1" cellspacing="10" cellpadding="5" style="border-collapse: collapse;">
        <tr>
            <td style="width: 25%">户名</td>
            <td style="width: 25%">${printInfo.companyName}</td>
            <td style="width: 25%">账号</td>
            <td style="width: 25%">${printInfo.companyAccount}</td>
        </tr>
        <tr>
            <td>收缴编号</td>
            <td>${printInfo.chargeNum}</td>
            <td>业主姓名</td>
            <td>${printInfo.ownerName}</td>
        </tr>
        <tr>
            <td>身份证号</td>
            <td>${printInfo.ownerCard}</td>
            <td>所属小区</td>
            <td>${printInfo.residentialAreaName}</td>
        </tr>
        <tr>
            <td>楼栋</td>
            <td>${printInfo.buildingName}</td>
            <td>单元</td>
            <td>${printInfo.unitName}</td>
        </tr>
        <tr>
            <td>房号</td>
            <td>${printInfo.houseName}</td>
            <td>面积(㎡)</td>
            <td>${printInfo.houseArea}</td>
        </tr>
        <tr>
            <td>单价(元/㎡)</td>
            <td>${printInfo.houseUnitPrice}</td>
            <td>金额(元)</td>
            <td>${printInfo.chargeMoney}</td>
        </tr>
    </table>
    <div style="float: right;text-align: left;margin-top: 10px;">制单人：${printInfo.handler}<br/>制单日期：${printInfo.stamp}</div>
</div>
</body>
</html>
