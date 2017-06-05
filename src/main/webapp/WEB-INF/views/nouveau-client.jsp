<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<h1>Nouveau client</h1>
<c:url value="/client/nouveau" var="url" />
<form:form action="${url}" modelAttribute="client" method="post" >
	<label>Titre&nbsp;:</label>
	<form:select path="titre" >
		<form:options itemLabel="libelle" />
	</form:select><br/>
	<form:errors cssClass="error" path="titre" /><br/>

	<label>Nom&nbsp;:</label>
	<form:input type="text" path="nom" /><br/>
	<form:errors cssClass="error" path="nom" /><br/>

	<label>Email&nbsp;:</label>
	<form:input type="text" path="email" /><br/>
	<form:errors cssClass="error" path="email" /><br/>

	<label>Date de naissance&nbsp;:</label>
	<form:input type="text" path="dateNaissance" /> <em>jj/mm/aaaa</em><br/>
	<form:errors cssClass="error" path="dateNaissance" /><br/>

	<button type="submit">Créer</button>
</form:form>
