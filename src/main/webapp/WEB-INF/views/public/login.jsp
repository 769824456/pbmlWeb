<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://zlzkj.com/tags" prefix="z" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>组织部“基层党建工作人员”名录库</title>
	<link href="${__static__}/admin/css/login.css" rel="stylesheet" type="text/css"/>
</head>
<body class="login-bd">
<div id="mainBody">
	<div id="cloud1" class="cloud" style="background-position: 450px 100px;"></div>
	<div id="cloud2" class="cloud" style="background-position: 0 460px;"></div>
</div>
<div class="logintop">
	<span>欢迎登录组织部“基层党建工作人员”名录库管理系统</span>
</div>
<div class="loginbody">
	<span class="systemlogo"></span>
	<form class="loginbox" id="form" action="${__url__}" method="post">
		<ul>
			<li>
				<input name="loginName" type="text" class="loginuser" placeholder="账号" />
			</li>
			<li>
				<input name="loginPass" type="password" class="loginpwd" placeholder="密码" />
			</li>
			<li class="mb10">
				<input type="submit" class="loginbtn" value="登录" />
				<label>
					<input name="" type="checkbox" value="" checked="checked" />记住密码</label>
				<%--<label>--%>
				<%--<a href="#">忘记密码？</a>--%>
				<%--</label>--%>
			</li>
			<li>
				<span class="error" id="msg"></span>
			</li>
		</ul>
	</form>
</div>
<div class="loginbm">Copyright&nbsp;2013-
	<script>
		document.write(new Date().getFullYear());
	</script>
</div>
<script src="${__static__}/mod/jquery/jquery.min.js" type="text/javascript"></script>
<script src="${__static__}/admin/js/cloud.js" type="text/javascript"></script>
<script src="${__static__}/mod/jquery/jquery.form.js" type="text/javascript"></script>
<script type="text/javascript">
	// 提交
	$("#form").ajaxForm({
		type: "post",
		dataType: "json",
		beforeSubmit: function() {
			var name = $(".loginuser").val();
			var pwd = $(".loginpwd").val();
			if (name == "") {
				$(".loginuser").focus();
				return false;
			}
			if (pwd == "") {
				$(".loginpwd").focus();
				return false;
			}
			$("#msg").removeClass("error").addClass("right").html("正在登录...");
		},
		success: function(data) {
			if (data.status == 1) {
				$("#msg").removeClass("error").addClass("right").html(data.info);
				window.location.href = "${z:u('/')}";
			} else {
				$("#msg").removeClass("right").addClass("error").html(data.info);
			}

		}
	});
</script>
</body>
</html>