<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>Login - MMS</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link media="screen" rel="stylesheet" href="<c:url value="resources/css/style.css" />" type="text/css" />
</head>
<body bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<div class="wrapper">
	<div class="header">
  	<div class="left-header">
		<div class="logo"><a href="${pageContext.request.contextPath}"><img src="<c:url value="resources/images/mms_logo.png" />" height="61" width="267" />
		</a></div>	
		</div>
	</div>
        <div class="clear"></div>
		<div class="main-login">
            <div class="login-image">
        <div class="login-image"><img src="<c:url value="resources/images/login.png" />" height="301" width="500" />
        </div>
            </div>
                <div class="login-container">
        <div class="login-container">
	<form:form action="loginform.html"  commandName="loginForm">
	<input type="hidden" name="j_idt24" value="j_idt24" />
	
	<table class="login">
		<tr>
			<td colspan="2"><h2>Login to MMS</h2></td>
		</tr>
		<tr>
			<td><label>Login Name : </label> </td>
		</tr>
		<tr>
			<td><input id="userName" type="text" name="userName" value="admin" /></td>
		</tr>
		<tr>
			<td><label>Password : </label>  </td>
		</tr>
		<tr>
			<td><input id="password" type="password" name="password" value="admin" /> </td>
		</tr>
		<tr>
			<td><input type="submit" name="j_idt24:j_idt32" value="Log In" class="add" /></td>
		</tr>	
	</table>	
</form:form></div></div>
		</div><div class="clear"></div>
            <div class="footer">
            <p style="text-align: center;">©  MMS  Developed By : Narayan Subedi</p> 
            <p  style="text-align: center;">Contact : 9841669206 || Email : prabhat.aurora@gmail.com</p>
            </div>

	</div></body>
</html>