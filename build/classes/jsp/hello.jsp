<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
hello world. Simple MVC is working if this is being displayed.
<P>This is <spring:message code="label.test.i18"/> message.
<P>Go to <a href="/${data.context_root}/${data.spring_root}/helloworld?locale=en_US">English version</a>
<P>Go to <a href="/${data.context_root}/${data.spring_root}/helloworld?locale=zh_HK">Chinese version</a> 

<P>${data.string1}</P>
<P>${data.string2}</P>
<P>${data.string3}</P>
</body>
</html>