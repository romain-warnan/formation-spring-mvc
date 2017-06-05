<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<h1><c:out value="${message}" /></h1>
<ul>
	<li><a href="<c:url value="clients" />">Liste des clients</a></li>
	<li><a href="<c:url value="commande" />">Passer une commande</a></li>
</ul>