
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>first exercise</title>
</head>
<body>
	<!-- exercise 1 
	<form action = "servlet/LoginController">	
		用户名：<input type = "text" name = "userName" />
		密&nbsp码:<input type = "password" name = "password" />
		<input type = "submit"/>
	</form>		
	-->
	<form action = "login.scaction">	
		用户名:<input type = "text" name = "userName" /><br/>
		密&nbsp码:<input type = "password" name = "password" /> 
		<input type = "submit" value = "登陆"/><br/><br/>a
	</form>	
	<form action = "register.scaction">	
		用户名:&nbsp<input type = "text" name = "userName" /><br/>
		密&nbsp码:&nbsp<input type = "password" name = "password" /><br/>
		确认密码:<input type = "password" name = "repassword" /><br/>
		邮&nbsp箱:&nbsp<input type = "text" name = "email" /> 
		<input type = "submit" value = "注册"/><br/>
	</form>	
	
</body>
</html>