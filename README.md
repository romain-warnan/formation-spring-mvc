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

> src/main/resources/application.properties

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
À l’aide d'une RedirectView rediriger cette URL vers l’URL « /accueil ».
Utiliser un code 301 (redirection permanente) pour effectuer la redirection (important pour le référencement).
Tester et vérifier avec les outils de développement du navigateur que le code est bien 301.