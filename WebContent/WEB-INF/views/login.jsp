<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.portfolio.www.message.Message" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>CodePen - Flat Login Form</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/meyer-reset/2.0/reset.min.css">
<link rel='stylesheet'
	href='https://fonts.googleapis.com/css?family=Roboto:400,100,300,500,700,900'>
<link rel='stylesheet'
	href='https://fonts.googleapis.com/css?family=Montserrat:400,700'>
<link rel='stylesheet'
	href='https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css'>
<link rel="stylesheet" href="<c:url value='/resources/style.css' />">
<script src='//cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>
<script defer src="<c:url value='/resources/script.js' />"></script>
</head>
<script>
	window.onload = function() {
		const code = '${code}';
		const msg = '${msg}';
		
		if (msg !== '' && code !== '${Message.SUCCESS.getCode()}') {
			alert(msg);
			location.href = '/11005/join-page.do';
		}
	}
</script>
<body>
	<!-- partial:index.partial.html -->
	<div class="container">
		<div class="info">
			<h1>Flat Login Form</h1>
			<span>Made with <i class="fa fa-heart"></i> by <a
				href="http://andytran.me">Andy Tran</a></span>
		</div>
	</div>
	<div class="form">
		<div class="thumbnail">
			<img
				src="https://s3-us-west-2.amazonaws.com/s.cdpn.io/169963/hat.svg" />
		</div>
		<form class="register-form" action="<c:url value='/sign-up.do' />" method="post">
			<input type="text" name="username" placeholder="name" /> 
			<input type="password" name="password" placeholder="password" /> 
			<input type="text" name="email" placeholder="email address" />
			<button>create</button>
			<p class="message">
				Already registered? <a href="#">Sign In</a>
			</p>
		</form>
		<form class="login-form" action="<c:url value='/login.do' />" method="post">
			<input type="text" name="username" placeholder="name" /> 
			<input type="password" name="password" placeholder="password" /> 
			<button>login</button>
			<p class="message">
				Not registered? <a href="#">Create an account</a>
			</p>
		</form>
	</div>
	<video id="video" autoplay="autoplay" loop="loop" poster="polina.jpg">
		<source
			src="http://andytran.me/A%20peaceful%20nature%20timelapse%20video.mp4"
			type="video/mp4" />
	</video>
	<!-- partial -->
</body>
</html>