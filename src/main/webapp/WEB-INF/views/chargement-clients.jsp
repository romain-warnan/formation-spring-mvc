<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<link rel="stylesheet" type="text/css" href="/static/css/application.css">
		<title>Spring MVC &ndash; Nouveaux clients</title>
	</head>
	<body>
		<h1>Nouveaux clients</h1>
		<c:url value="/client/chargement" var="url" />
		<form method="POST" enctype="multipart/form-data" action="${url}">
			<label>Fichier : </label><input type="file" name="file" /><br/>
			<button type="submit">Charger</button>
		</form>
	</body>
</html>
