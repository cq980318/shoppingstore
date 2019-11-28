<%--
  Created by IntelliJ IDEA.
  User: Dell
  Date: 2019/11/26
  Time: 15:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>忘记密码</title>
    <script src="https://code.jquery.com/jquery-2.1.1.min.js"></script>

</head>
<body>
<h3> 请输入你需要找回密码的账户名</h3>
用户名: <input type="text" id="username" />
邮箱:<input type="text" id="email" />
新密码:<input type="password" id="newPwd">
<input type="button" value="send email" id="btn1">
<input type="button" value="重置密码" id="btn2">
<div id="msg" style="background-color:#8db842 ;width: 200px;height: 200px"></div>

<script type="text/javascript">
    $(function () {
        $("#btn2").click(function () {
            if($("#username").val()!='' && $("#newPwd").val()!='') {
                $.ajax({
                    url: "../../find",
                    type: "POST",
                    data: {
                        username: $("#username").val(),
                        pwd: $("#newPwd").val()
                    },
                    success: function (data) {
                        if (data == "yes") {
                            $("#msg").html("修改成功");
                        } else if (data == "no") {
                            $("#msg").html("修改失败");
                        } else {
                            $("#msg").html("用户名不存在");
                        }
                    }

                });
            }else{
                $("#msg").html("用户名和新密码不能为空");
            }
        })

    })
</script>
</body>
</html>
