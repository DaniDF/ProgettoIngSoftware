<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<%
		Boolean isLogged = (Boolean)session.getAttribute("isLogged");
		String userType = (String)session.getAttribute("userType");
		String usr = (String)session.getAttribute("usr");
	
		if(isLogged == null || !isLogged ||
		   userType == null || (!userType.equals("utente") && !userType.equals("struttura")) ||
		   usr == null || usr.length() == 0)
		{
			//response.sendRedirect("/ProgettoIngSoftware");
		}
		
		userType = "struttura";
	%>

	<meta charset="ISO-8859-1">
	<title>Home utente<!-- userType --></title>
	
	<meta name="theme-color" content="#EE0022">
	<link rel="icon" type="image/png" href="/ProgettoIngSoftware/img/icon.png" />
	
	<link rel="stylesheet" type="text/css" href="/ProgettoIngSoftware/style/mainStyle_mobile.css">
	<link rel="stylesheet" type="text/css" href="/ProgettoIngSoftware/style/inputStyle.css">
	
	<script type="text/javascript" src="/ProgettoIngSoftware/script/struttura.js"></script>
</head>
<body>

	<%
		if(userType.equals("utente"))
		{
	%>
			<h1>Benvenuto Valentino<!-- %= usr %>--></h1>
			
			<button class="inputButton">
				<img src="/ProgettoIngSoftware/img/chrono.png">
				Visualizza tempi
			</button>
			<button class="inputButton">
				<img src="/ProgettoIngSoftware/img/doc.png">
				Visualizza fatture
			</button>
			<button class="inputButton">
				<img src="/ProgettoIngSoftware/img/chrono.png">
				Modifica dati
			</button>
			<button class="inputButton">
				<img src="/ProgettoIngSoftware/img/kart.png">
				Visualizza strutture
			</button>
			<button class="inputButton">
				<img src="/ProgettoIngSoftware/img/carr.png">
				Carrello
			</button>
	<%
		}
		else
		{
	%>
			<h1>Benvenuto Misanino<!-- %= usr %>--></h1>
			
			<button id="turni" class="inputButton" onclick="viewTurni(8)">
				<img src="/ProgettoIngSoftware/img/chrono.png">
				I tuoi turni
			</button>
			<button id="turno_add" class="inputButton turno add">
				<img src="/ProgettoIngSoftware/img/add.png">
			</button>
			
	<%
			//getTurni
	%>
			
			<button id="turno0" class="inputButton turno">
				Turno 0
			</button>
			<button id="turno1" class="inputButton turno">
				Turno 1
			</button>
			<button id="turno2" class="inputButton turno">
				Turno 2
			</button>
			<button id="turno3" class="inputButton turno">
				Turno 3
			</button>
			<button id="turno4" class="inputButton turno">
				Turno 4
			</button>
			<button id="turno5" class="inputButton turno">
				Turno 5
			</button>
			<button id="turno6" class="inputButton turno">
				Turno 6
			</button>
			<button id="turno7" class="inputButton turno">
				Turno 7
			</button>
			<button class="inputButton">
				<img src="/ProgettoIngSoftware/img/chrono.png">
				Modifica dati
			</button>
	<%
		}
	%>
</body>
</html>