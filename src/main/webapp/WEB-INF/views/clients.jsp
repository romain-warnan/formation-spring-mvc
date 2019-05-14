<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<link rel="stylesheet" type="text/css" href="/static/css/application.css">
	</head>
	<body>	
		<h1>Liste des clients</h1>
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
		<p><a href="<c:url value="/accueil" />">Accueil</a> | <a href="<c:url value="/nouveau-client" />">Nouveau client</a></p>
	</body>
</html>