<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Login</title>
<link rel="icon" href="${pageContext.request.contextPath}/images/oa.ico" type="image/x-icon">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<script type="application/x-javascript">
	 addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } 
</script>
<meta name="keywords"
	content="Flat Dark Web Login Form Responsive Templates, Iphone Widget Template, Smartphone login forms,Login form, Widget Template, Responsive Templates, a Ipad 404 Templates, Flat Responsive Templates" />
<link href="${pageContext.request.contextPath}/css/style.css"
	rel='stylesheet' type='text/css' />
<!--webfonts-->
<!-- <link href='http://fonts.useso.com/css?family=PT+Sans:400,700,400italic,700italic|Oswald:400,300,700' rel='stylesheet' type='text/css'>
<link href='http://fonts.useso.com/css?family=Exo+2' rel='stylesheet' type='text/css'> -->
<!--//webfonts-->
<script src="${pageContext.request.contextPath}/js/jquery-1.11.0.min.js"></script>
</head>
<body>
	<script>
		$(document).ready(function(c) {
			$('.close').on('click', function(c) {
				$('.login-form').fadeOut('slow', function(c) {
					$('.login-form').remove();
				});
			});
		});
	</script>
	<!--SIGN UP-->
	<h1>欢迎使用OA办公室系统</h1>
	<div class="login-form">
		<!-- <div class="close"> </div> -->
		<div class="head-info">
			<label class="lbl-1"> </label> <label class="lbl-2"> </label> <label
				class="lbl-3"> </label>
		</div>
		<div class="clear"></div>
		<div class="avtar">
			<img src="${pageContext.request.contextPath}/images/avtar.png" />
		</div>
		<form action="${pageContext.request.contextPath}/login" method="post">
			<input type="text" class="text" name="userName" value="请输入用户名"
				onfocus="this.value = '';"
				onblur="if (this.value == '') {this.value = '请输入用户名';}">
			<div class="key">
				<input type="password" name="password" value="请输入密码"
					onfocus="this.value = '';"
					onblur="if (this.value == '') {this.value = '请输入密码';}">
			</div>
			<p style="color: #dd3e3e; margin-top: -30px; margin-bottom: 18px">${requestScope.msg}</p>
			<div class="signin">
				<input type="submit" value="Login">
			</div>

		</form>

	</div>

</body>
</html>