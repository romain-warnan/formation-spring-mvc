<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<link rel="stylesheet" type="text/css" href="/static/css/application.css">
		<title>Spring MVC &ndash; Nouveau client</title>
	</head>
	<body>
		<h1>Nouveau client</h1>
		<c:url value="/client/nouveau" var="url" />
		<form action="${url}" method="post" >
		
			<label>Titre&nbsp;</label>
			<select name="titre" >
				<c:forEach items="${titres}" var="titre">
					<option value="${titre}"><c:out value="${titre.libelle}" /></option>
				</c:forEach>
			</select><br/>
			
			<label>Nom&nbsp;:</label>
			<input type="text" name="nom" /><br/>
			
			<label>Email&nbsp;:</label>
			<input type="text" name="email" />
			<br/>
			<label>Date de naissance&nbsp;:</label>
			<input type="text" name="dateNaissance" /> <em>jj/mm/aaaa</em><br/>
			
			<button type="submit">Créer</button>
		</form>
	</body>
</html>
