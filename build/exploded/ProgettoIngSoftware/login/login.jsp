<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Login</title>
	
	<meta name="theme-color" content="#EE0022">
	<link rel="icon" type="image/png" href="/ProgettoIngSoftware/img/icon.png" />
	
	<link rel="stylesheet" type="text/css" href="/ProgettoIngSoftware/style/mainStyle_mobile.css">
	<link rel="stylesheet" type="text/css" href="/ProgettoIngSoftware/style/inputStyle.css">
	
	<script type="text/javascript" src="/ProgettoIngSoftware/script/checkInput.js"></script>
</head>
<body>
	<h1 id="title">Accedi</h1>
	
	<%
		Boolean isLogged = (Boolean)session.getAttribute("isLogged");
	
		if(isLogged != null && isLogged)
		{
	%>		<h3>Accesso gi&agrave effettuato!</h3>
	<%
		}
		else
		{
	%>
	
	<div class="selectType">
		<label id="utente" class="desc">Utente</label>
		
		<label class="switch">
			<input type="checkbox" onchange="change(this)">
			<span class="slider round"></span>
		</label>
		
		<label id="struttura" class="desc">Struttura</label>
	
	</div>
	
	<form action="" method="post">
		<input type="text" id="usr" name="usr" placeholder="Username" class="inputText">
		<input type="password" id="psw" name="psw" placeholder="Password" class="inputText">
		<input type="submit" id="subUsr" value="Accedi" class="inputButton">
	</form>
	<%
		}
	%>
</body>
</html>