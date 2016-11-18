<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<link rel="stylesheet" type="text/css" href="/static/css/application.css">
		<title>Spring MVC &ndash; Clients</title>
	</head>
	<body>	
		<h1>Liste des clients</h1>
		<c:if test="${not empty nouveauClient}">
			<p class="success">Le client <c:out value="${nouveauClient.email}" /> a été créé avec succès.</p>
		</c:if>
		<c:if test="${not empty message}">
			<p class="success"><c:out value="${message}" /></p>
		</c:if>
		<table>
		<tr>
			<th>Id</th>
			<th>Nom</th>
		</tr>
		<c:forEach items="${clients}" var="client">
			<c:url var="url" value="client/${client.id}" />
			<tr>
				<td><a href="${url}">${client.nom}</a></td>
				<td>${client.email}</td>
			</tr>
		</c:forEach>
		</table>
		<p><a href="<c:url value="/accueil" />">Accueil</a> | <a href="<c:url value="/client/nouveau" />">Nouveau client</a> | <a href="<c:url value="/client/chargement" />">Charger une liste de clients</a></p>
	</body>
</html>