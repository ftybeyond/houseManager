<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>用户登录</title>

    <script type="text/javascript" src="/vendors/jquery/1.x/jquery.min.js"></script>
    <!-- Bootstrap -->
    <link href="/vendors/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="/vendors/font-awesome/css/font-awesome.min.css" rel="stylesheet">
    <!-- Custom Theme Style -->
    <link href="/vendors/gentelella/css/custom.min.css" rel="stylesheet">

    <!--[if IE]>
    <script src="/vendors/html5shiv.min.js"></script>
    <![endif]-->
    <script>
        if (window.frames.length != parent.frames.length) {
            parent.window.location.href="/page/index.action";
        }
        $(function(){
            $("#loginForm").on("submit",function () {
                var loginName = $(this).find("input[name='loginName']");
                var password = $(this).find("input[name='password']")
                if(!loginName){
                    $("#message").html("登录名不能为空!");
                }else if(!password){
                    $("#message").html("密码不能为空!")
                }

            })
        })
    </script>
</head>

<body class="login">
<div>
    <a class="hiddenanchor" id="signup"></a>
    <a class="hiddenanchor" id="signin"></a>

    <div class="login_wrapper">
        <div class="animate form login_form">
            <section class="login_content">
                <form action="/login.action" method="post" id="loginForm">
                    <h2 style="padding-bottom: 20px;font-size: 23px;">七台河住宅专项维修资金管理系统</h2>
                    <span id="message" style="color: red">${message}</span>
                    <div>
                        <input type="text" class="form-control" placeholder="登录名" name="loginName" required="" />
                    </div>
                    <div>
                        <input type="password" class="form-control" placeholder="登录密码" name="password" required="" />
                    </div>
                    <div>
                        <button type="submit" class="btn btn-primary">登　　录</button>
                    </div>

                    <div class="clearfix"></div>

                    <div class="separator">
                        <div class="clearfix"></div>
                        <br />
                        <div>
                            <p>©2018 All Rights Reserved. 七台河市房管局</p>
                        </div>
                    </div>
                </form>
            </section>
        </div>

    </div>
</div>
</body>
</html>
