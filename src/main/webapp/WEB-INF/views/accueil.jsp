<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<link rel="stylesheet" type="text/css" href="/static/css/application.css">
		<link rel="icon" type="image/png" href="/static/favicon.png" />
		<title>Spring MVC &ndash; Accueil</title>
	</head>
	<body>
		<h1><c:out value="${message}" /></h1>
		<c:url var="url" value="clients" />
		<p><a href="<c:url value="clients" />">Liste des clients</a> | <a href="<c:url value="commande" />">Passer une commande</a>
	</body>
</html>