<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>七台河市房管局住房维修基金管理系统</title>

    <!-- Bootstrap -->
    <link href="<%=path%>/vendors/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="<%=path%>/vendors/font-awesome/css/font-awesome.min.css" rel="stylesheet">
    <!-- Custom Theme Style -->
    <link href="<%=path%>/vendors/gentelella/css/custom.min.css" rel="stylesheet">

    <script src="<%=path%>/vendors/requireJS/require.js"></script>
    <script type="text/javascript" src="<%=path%>/vendors/requireJS/require-config.js"></script>
    <script type="text/javascript">
        require(['main','layer'],function () {
            layer.config({
                path: '/vendors/layer/',
                offset: "200px"
            });
            jQuery(function(){
                jQuery("#main_frame").on("load",function(){
                    jQuery(this).height(jQuery(".right_col").height())
                })
            })
        });
        function loginOut() {
            layer.confirm("确认退出系统？",function () {
                window.location.href = "/forward/loginOut.action"
            })
        }
    </script>
    <style>
        iframe{
            border: 0;
            width:100%;
            height: 100%;
        }
        .glyphicon{
            width:26px;
            font-size:16px;
        }
    </style>
</head>
<body class="nav-md">
<div class="container body">
    <div class="main_container">
        <div class="col-md-3 left_col">
            <div class="left_col scroll-view">
                <div class="navbar nav_title" style="border: 0;">
                    <a href="/index.html" class="site_title"><i class="fa fa-paw"></i> <span>七台河房管局</span></a>
                </div>

                <div class="clearfix"></div>

                <!-- menu profile quick info -->
                <div class="profile clearfix">
                    <div class="profile_pic">
                        <img src="<%=path%>/images/user.png" alt="..." class="img-circle profile_img">
                    </div>
                    <div class="profile_info">
                        <span>Welcome,</span>
                        <h2>${sessionScope.loginUser.realName}</h2>
                    </div>
                </div>
                <!-- /menu profile quick info -->

                <br />

                <!-- sidebar menu -->
                <div id="sidebar-menu" class="main_menu_side hidden-print main_menu">
                    <div class="menu_section">
                        <h3>功能列表</h3>
                        <ul class="nav side-menu">
                            <c:forEach items="${sessionScope.authority}" var="item">
                                <li><a><i class="${item.value[0].icon}"></i> ${item.key} <span class="fa fa-chevron-down"></span></a>
                                    <ul class="nav child_menu">
                                        <c:forEach items="${item.value}" var="func">
                                            <li><a target="main_frame" href="${func.url}">${func.name}</a></li>
                                        </c:forEach>
                                    </ul>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
                <!-- /sidebar menu -->
            </div>
        </div>

        <!-- top navigation -->
        <div class="top_nav">
            <div class="nav_menu">
                <nav>
                    <div class="nav toggle">
                        <a id="menu_toggle"><i class="fa fa-bars"></i></a>
                    </div>

                    <ul class="nav navbar-nav navbar-right">
                        <li class="">
                            <a href="javascript:;" class="user-profile dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
                                <img src="<%=path%>/images/user.png" alt="">${sessionScope.loginUser.realName}
                                <span class=" fa fa-angle-down"></span>
                            </a>
                            <ul class="dropdown-menu dropdown-usermenu pull-right">
                                <li><a href="javascript:;"> 密码修改</a></li>
                                <li><a href="javascript:loginOut();"><i class="fa fa-sign-out pull-right"></i> 注销</a></li>
                            </ul>
                        </li>
                    </ul>
                </nav>
            </div>
        </div>
        <!-- /top navigation -->

        <!-- page content -->
        <div class="right_col" role="main">
            <iframe id="main_frame" name="main_frame"></iframe>
        </div>
        <!-- /page content -->

        <!-- footer content -->
        <footer>
            <div class="pull-right">
                Copyright © 2018-2020 七台河房管局 All Rights Reserved.
            </div>
            <div class="clearfix"></div>
        </footer>
        <!-- /footer content -->
    </div>
</div>
</body>
</html>
