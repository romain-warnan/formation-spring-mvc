<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<h1>Liste de clients</h1>
<c:url var="txt" value="/clients/telechargement" />
<c:url var="pdf" value="/clients/telechargement">
	<c:param name="type">pdf</c:param>
</c:url>
<c:url var="xls" value="/clients/telechargement">
	<c:param name="type">xls</c:param>
</c:url>
<p>
	Télécharger : <a href="${txt}">(txt)</a> <a href="${pdf}">(pdf)</a> <a href="${xls}">(xls)</a>
</p>
<br/>
<c:url value="/clients/chargement" var="url" />
<form method="POST" enctype="multipart/form-data" action="${url}">
	<label>Fichier : </label><input type="file" name="file" /><br/>
	<button type="submit">Charger</button>
</form>
