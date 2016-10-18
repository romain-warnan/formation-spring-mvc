# Introduction à Spring MVC

## Récupérer le code source du TP

> Terminal

```bash
cd /d/idep/Mes\ Documents/eclipse_workspace
git config --global http.proxy http://proxy-orange.http.insee.fr:8080
git clone https://github.com/Insee-CNIP/formation-spring-mvc.git
```

## Importer le projet dans Eclipse

> Eclipse

* File
* Import…
* Existing Maven Project
* Root directory : D:\idep\Mes Documents\eclipse_workspace\formation-spring-mvc
* Finish

## Créer une configuration de lancement

> Eclipse

* Run configuration…
* Maven build > New
* Name : formation-spring-mvc-run
* Base directory : ${workspace_loc:/formation-spring-mvc}
* Goals : clean tomcat7:run -DskipTests
* Apply

## TP1 : Mise en place

### Ajouter les dépendances Maven

> pom.xml

```xml
<properties>
	<spring.version>4.3.2.RELEASE</spring.version>
</properties>

<dependencies>
	<!-- Spring MVC -->
	<dependency>
		<groupId>org.springframework</groupId>
		<artifactId>spring-webmvc</artifactId>
		<version>${spring.version}</version>
	</dependency>
	
	<!-- Servlet -->
	<dependency>
		<groupId>javax.servlet</groupId>
		<artifactId>javax.servlet-api</artifactId>
		<version>3.1.0</version>
		<scope>provided</scope>
	</dependency>
	<dependency>
		<groupId>javax.servlet.jsp</groupId>
		<artifactId>jsp-api</artifactId>
		<version>2.2</version>
		<scope>provided</scope>
	</dependency>
	<dependency>
		<groupId>jstl</groupId>
		<artifactId>jstl</artifactId>
		<version>1.2</version>
	</dependency>
</dependencies>
```

### Créer le fichier de contexte de l’application

> src/main/resources/applicationContext.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:context="http://www.springframework.org/schema/context"
	xsi:schemaLocation="
    http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
    http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-3.0.xsd
">
	<context:component-scan base-package="fr.insee.bar" />
</beans>
```

### Charger le contexte de l’application au démarage du serveur

> web.xml

```xml
<context-param>
	<param-name>contextConfigLocation</param-name>
	<param-value>classpath:applicationContext.xml</param-value>
</context-param>
<listener>
	<listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
</listener>
```

### Créer le fichier de contexte web

> src/main/resources/servlet-dispatcher.xml 

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:context="http://www.springframework.org/schema/context"
	xmlns:mvc="http://www.springframework.org/schema/mvc"
	xsi:schemaLocation="
    http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
    http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-3.0.xsd
    http://www.springframework.org/schema/mvc http://www.springframework.org/schema/mvc/spring-mvc.xsd
">
	<context:component-scan base-package="fr.insee.bar" />
</beans>
```

### Ajouter la servlet de Spring MVC et diriger toutes les requêtes vers cette servlet

> web.xml

```xml
<servlet>
	<servlet-name>servlet-dispatcher</servlet-name>
	<servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
	<init-param>
		<param-name>contextConfigLocation</param-name>
		<param-value>classpath:servlet-dispatcher.xml</param-value>
	</init-param>
	<load-on-startup>1</load-on-startup>
</servlet>
<servlet-mapping>
	<servlet-name>servlet-dispatcher</servlet-name>
	<url-pattern>/</url-pattern>
</servlet-mapping>
```

### Créer le dossier contenant les vues

> src/main/webapp/WEB-INF/views/

### Déclarer et paramétrer le viewResolver

> servlet-dispatcher.xml

```xml
<bean id="viewResolver"	class="org.springframework.web.servlet.view.InternalResourceViewResolver">
	<property name="prefix">
		<value>WEB-INF/views/</value>
	</property>
	<property name="suffix">
		<value>.jsp</value>
	</property>
</bean>
```

### Créer le package contenant les contrôleurs

> fr.insee.bar.controller

### Créer le contrôleur `AccueilController`

> AccueilController.java

Ajouter l’annotation `@Controller`.
Créer une méthode qui dirige vers la vue « accueil.jsp » quand on accède à l’URL « /accueil ».
Cette méthode ajoute au modèle un objet « message » de type qui vaut "Hello world".
Créer la JSP « views/accueil.jsp » et afficher l’objet « message ».

> accueil.jsp

```jsp
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<h1><c:out value="${message}" /></h1>
```

Tester.

### Utiliser un fichier de propriétés

> src/main/resources/application-properties

```properties
name=Spring MVC
```

> AccueilController.java

Ajouter l’annotation `@PropertySource("classpath:application.properties")` pour charger le fichier de propriétés.
Ajouter un attribute de type `String` dans le contrôleur et l’annoter avec `@Value("${name}")` pour récupérer la valeur de la clé « name »
Paramétrer le message avec cet attribut.
Tester.

### Rediriger l’URL racine vers la page d’accueil

> AccueilController.java

Créer une nouvelle méthode qui se déclenche quand on accède à l’URL « / ».
À l’aide de l’instruction `"redirect:/accueil"` rediriger cette URL vers l’URL « /accueil ».
Utiliser un code 301 (redirection permanente) pour effectuer la redirection (important pour le référencement).
Tester et vérifier avec les outils de développement du navigateur que le code est bien 301.

## TP2 : Navigation

### Liste de tous les clients

#### Créer un contrôleur qui permet d’afficher la liste de tous les clients

> ClientsController.java

Ce contrôleur possède une méthode qui est appelée à l’URL « /clients ».
Il récupère la liste de tous les clients dans la base de donnée et l’ajoute au modèle.

```java
	@Autowired
	private ClientDao clientDao;
	…
	List<Client> clients = clientDao.findAll();
```

Il lance la génération de la vue `/jsp/clients.jsp`.

#### Afficher la liste des clients

> clients.jsp

En itérant sur la liste des clients avec le tag `<c:forEach>`, afficher la liste de tous les clients (nom et email) dans un tableau :

> Rappel : structure d’un tableau HTML

```html
	<table>
		<tr> <!-- Ligne entête -->
			<th>Entête 1</th>
			<th>Entête 2</th>
			<th>Entête 3</th>
		</tr>
		<tr> <!-- Ligne 1 -->
			<td>Cellule 1.1</td>
			<td>Cellule 1.2</td>
			<td>Cellule 1.3</td>
		</tr>
		<tr> <!-- Ligne 2 -->
			<td>Cellule 2.1</td>
			<td>Cellule 2.2</td>
			<td>Cellule 2.3</td>
		</tr>
	</table>
```

#### Ajouter un lien vers la page d’accueil

> clients.jsp

Grace au tag `<c:url>` créer une variable qui pointe vers la page d’accueil.
Utiliser cette variable dans un lien qui redirige vers la page d’accueil.

#### Sur la page d’accueil, ajouter un lien vers la page de la liste des clients

> accueil.jsp

### Détails pour un client donné

#### Créer un contrôleur qui permet d’afficher les informations concernant un client donné

> ClientController.java

Ce contrôleur possède une méthode qui est appelée à l’URL « /client/{id} ».
À l’aide de l’annotation `@PathVariable`, récupérer la valeur de l’identifiant passé dans l’URL.
Dans la base, récupérer le client associé à cet identifiant.
Ajouter le client au modèle.
Diriger vers la page `/jsp/client.jsp`.

#### Créer la page client.jsp

> client.jsp

Y afficher les informations relatives au client : identifiant, nom, email et date de naissance.
Pour formater la date, utiliser le tag `<fmt:formatDate>` et le format `dd/MMMM/yyyy`.
Ajouter un lien vers la page d’accueil.

#### Faire le lien entre la page clients et les sous-pages client

> clients.jsp

Autour de chaque nom de client, ajouter un lien qui pointe vers l’URL `/client/{id}`.
De cette manière, l’utilisateur peut cliquer sur le nom d’un client pour en voir le détail.

### Utilisation d’un convertisseur

#### Créer le nouveau convertisseur

> ClientConverter

Dans  le package fr.insee.bar.converter, créer une classe `ClientConverter` qui implémente de l’interface `Converter<String, Client>`.
Ne pas oublier le stéréotype `@Component` sur la classe. 
Implémenter la méthode `convert` avec un appel à `ClientDao.find`.

#### Simplifier le contrôleur

> ClientController

Modifier la signature de la méthode pour remplacer le `Short` par un `Client`.
Supprimer le DAO du contrôleur.

#### Enregistrer le convertissuer

> dispatcher-servlet.xml

Déclarer ce nouveau convertisseur auprès de la servlet de Spring MVC :

```xml
<mvc:annotation-driven conversion-service="conversionService" />
<bean id="conversionService" class="org.springframework.format.support.FormattingConversionServiceFactoryBean">
	<property name="converters">
		<set>
			<bean class="fr.insee.bar.converter.ClientConverter" />
		</set>
	</property>
</bean>
 ```
 
 Tester que l’application fonctionne toujours.