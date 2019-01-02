<%--
  Created by IntelliJ IDEA.
  User: Jason Xu
  Date: 2018/4/1
  Time: 23:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/common.jsp" %>
<%
    //String _path = WebUtils.getWebContextPath(request);
    
%>
<!DOCTYPE html>
<html lang="zh-CN" style="height: 100%;">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>登录|BITs网盘</title>

    <link rel="icon" type="image/png" href="<%=___path%>img/favicon/cloud.png" sizes="16x16">
    <!--[if lt IE 9]>
    <script src="https://cdn.bootcss.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <style type="text/css">
        .error {
            color: red;
        }
    </style>
</head>
<body style="height: 100%;">
<div class="container-fluid" style="height: 100%;">
    <div class="row" style="margin-top:8%;">
        <div class="col-md-4 col-md-offset-4">
            <h1 style="text-align: center;"><img src="<%=___path%>img/favicon/cloud.png" alt="cloud">BITs云盘</h1>
            <div class="form-group">
                <input type="text" class="form-control" id="username" placeholder="用户名">
                <br/>
                <input type="password" class="form-control" id="password" placeholder="密码">
            </div>
            <button class="btn btn-primary col-md-12" id="btn_login">登录</button>
            <p class="error"></p>
        </div>
    </div>
</div>


<%--<script src="js/jquery-3.2.1.min.js"></script>--%>
<%--<script src="js/bootstrap.min.js"></script>--%>
<script>
    var _path = '<%=___path%>';
    $(document).ready(function () {
        $("#btn_login").click(function () {
            //console.log(JSON.stringify(getUser()));
            $.ajax({
                type: "POST",
                dataType: "json",
                url: _path + "verify/authentication",
                contentType: "application/json; charset=utf-8",
                data: JSON.stringify(getUser()),
                statusCode: {
                    404: function () {
                        $(".error").text("登录失败，用户名不存在");
                    },
                    401: function () {
                        $(".error").text("登录失败，密码不正确");
                    }
                },
                success: function (result) {
                    console.log(result);

                    $(".error").text("登录成功");
                    sessionStorage.setItem("token", result.token);
                    sessionStorage.setItem("user_username", result.username);
                    window.location.href = _path + "/home";

                }
            });
        });

        function getUser() {
            var user = {
                username: $("#username").val(),
                password: $("#password").val()
            };
            return user;
        }
    });
</script>
</body>
</html>