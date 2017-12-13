<!-- .slide: data-background-image="images/spring.png" data-background-size="1200px" class="chapter" -->
## 5
### JSP : Java server pages





<!-- .slide: class="slide" -->
### Accéder aux objets du modèle dans les JSP
EL : Expression language

`${personne.nom}`
```java
((Personne)request.getAttribute("personne")).getNom()
```

`${sessionScope.personne.nom}`
```java
((Personne)session.getAttribute("personne")).getNom()
```

`${liste[0]}`
```java
liste.get(0)
```

`${map["nom"]}`
```java
map.get("nom")
```





<!-- .slide: class="slide" -->
### Accéder aux objets du modèle dans les JSP
EL : Expression language

`${empty personnes}`
```java
CollectionUtils.isEmpty(personnes)
```

`${not empty personnes}`
```java
CollectionUtils.isNotEmpty(personnes)
```

`${empty personne.nom}`
```java
StringUtils.isEmpty(personne.getNom())
```

`${not empty personne.nom}`
```java
StringUtils.isNotEmpty(personne.getNom())
```





<!-- .slide: class="slide" -->
### JSTL : JSP Standard Tag Library
```html
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
```



```html

<c:set var="nom" value="${personne.nom}"
<c:set var="nom">Valeur</c:set>

(vide)
```
 
```html
<c:url var="url" value="/accueil" />
<a href="${url}">Lien</a>
<a href="<c:url value="/accueil" />">Lien</a>

<a href="contextPath/accueil">Lien</a>
```
 
```html
<c:out value="${personne.nom}" />

Nom
```
 
```html
<c:out value="${personne.nom}" default="Inconnu" />

Nom / Inconnu
```





<!-- .slide: class="slide" -->
### Structures de contrôle
```html
<c:if test="${empty personne.nom}">Inconnu</c:if>

(vide) / Inconnu
```

```html
<c:forEach items="${personnes}" var="personne">
    ${personne.nom}<br/>
</c:forEach>

Nom0
Nom1
Nom2
```

```html
<c:choose>
    <c:when test="${personne.titre == 'M.'}">
        Bonjour Monsieur
    </c:when>
    <c:when test="${personne.titre == 'Mme'}">
        Bonjour Madame
    </c:when>
    <c:otherwise>
        Bonjour
    </c:otherwise>
</c:choose>

Bonjour Monsieur / Bonjour Madame / Bonjour
```





<!-- .slide: class="slide" -->
### Formattage et internationalisation (i18n) 
JSTL : JSP Standard Tag Library
```html
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
```

```html
<fmt:bundle basename="message">
    <fmt:message key="a.b.bonjour" />
</fmt:bundle>

Bonjour / ???a.b.bonjour???
Hello / ???a.b.bonjour???
```

```html
<fmt:formatNumber value="${1234.5}" minIntegerDigits="1" minFractionDigits="2" />

1 234,50 
1,234.50
```

```html
<fmt:formatDate value="${date}" pattern="dd/mmmm/YYYY"/>

30 avril 2015
30 April 2015
```

```html
<fmt:formatDate value="${date}" dateStyle="short"/>

30/04/15
4/30/15
```





<!-- .slide: data-background-image="images/tp.png" data-background-size="500px" class="tp" -->
## [TP2](https://github.com/romain-warnan/formation-spring-mvc#2-navigation)