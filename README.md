# Introduction à Spring MVC

## 0. Récupérer le code source du TP

### 0.1. Cloner le dépot git

> Terminal

```bash
cd /d/*idep*/Mes\ Documents/eclipse_workspace
git config --global user.name "*Prénom Nom*"
git config --global user.email "*email*"
git config --global http.proxy http://proxy-orange.http.insee.fr:8080
git clone https://github.com/Insee-CNIP/formation-spring-mvc.git
cd formation-spring-mvc
git checkout tp1-enonce
git pull
```

### 0.2. Importer le projet dans Eclipse

> Eclipse

* File
* Import…
* Existing Maven Project
* Root directory : D:\idep\Mes Documents\eclipse_workspace\formation-spring-mvc
* Finish

### 0.3. Créer une configuration de lancement

> Eclipse

* Run configuration…
* Maven build > New
* Name : formation-spring-mvc-run
* Base directory : ${workspace_loc:/formation-spring-mvc}
* Goals : clean tomcat7:run -DskipTests
* Apply

## 1. Mise en place

### 1.1. Ajouter les dépendances Maven

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

### 1.2. Créer le fichier de contexte de l’application

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

### 1.3. Charger le contexte de l’application au démarage du serveur

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

### 1.4. Créer le fichier de contexte web

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

### 1.5. Ajouter la servlet de Spring MVC et diriger toutes les requêtes vers cette servlet

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

### 1.6. Créer le dossier contenant les vues

> src/main/webapp/WEB-INF/views/

### 1.7. Déclarer et paramétrer le viewResolver

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

### 1.8. Créer le package contenant les contrôleurs

> fr.insee.bar.controller

### 1.9. Créer le contrôleur `AccueilController`

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

### 1.10. Utiliser un fichier de propriétés

> src/main/resources/application.properties

```properties
name=Spring MVC
```

> AccueilController.java

Ajouter l’annotation `@PropertySource("classpath:application.properties")` pour charger le fichier de propriétés.
Ajouter un attribute de type `String` dans le contrôleur et l’annoter avec `@Value("${name}")` pour récupérer la valeur de la clé « name »
Paramétrer le message avec cet attribut.
Tester.

### 1.11. Rediriger l’URL racine vers la page d’accueil

> AccueilController.java

Créer une nouvelle méthode qui se déclenche quand on accède à l’URL « / ».
À l’aide de l’instruction `"redirect:/accueil"` rediriger cette URL vers l’URL « /accueil ».
Utiliser un code 301 (redirection permanente) pour effectuer la redirection (important pour le référencement).
Tester et vérifier avec les outils de développement du navigateur que le code est bien 301.

## 2. Navigation

> Terminal

```bash
git add .
git commit -m "TP1 <idep>"
git checkout tp2-enonce
git pull
```

### 2.1. Liste de tous les clients

#### 2.1.1. Créer un contrôleur qui permet d’afficher la liste de tous les clients

> ClientsController.java

Ce contrôleur possède une méthode qui est appelée à l’URL « /clients ».
Il récupère la liste de tous les clients dans la base de donnée et l’ajoute au modèle.

```java
	@Autowired
	private ClientDao clientDao;
	…
	List<Client> clients = clientDao.findAll();
```

Il lance la génération de la vue `/WEB-INF/views/clients.jsp`.

#### 2.1.2. Afficher la liste des clients

> clients.jsp

En itérant sur la liste des clients avec le tag `<c:forEach>`, afficher la liste de tous les clients (nom et email) dans un tableau :

> :grey_question: Rappel : structure d’un tableau HTML

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

#### 2.1.3. Ajouter un lien vers la page d’accueil

> clients.jsp

Grace au tag `<c:url>` créer une variable qui pointe vers la page d’accueil.
Utiliser cette variable dans un lien qui redirige vers la page d’accueil.

#### 2.1.4. Sur la page d’accueil, ajouter un lien vers la page de la liste des clients

> accueil.jsp

### 2.2. Détails pour un client donné

#### 2.2.1. Créer un contrôleur qui permet d’afficher les informations concernant un client donné

> ClientController.java

Ce contrôleur possède une méthode qui est appelée à l’URL « /client/{id} ».
À l’aide de l’annotation `@PathVariable`, récupérer la valeur de l’identifiant passé dans l’URL.
Dans la base, récupérer le client associé à cet identifiant.
Ajouter le client au modèle.
Diriger vers la page `/jsp/client.jsp`.

#### 2.2.2. Créer la page client.jsp

> client.jsp

Y afficher les informations relatives au client : identifiant, nom, email et date de naissance.
Pour formater la date, utiliser le tag `<fmt:formatDate>` et le format `dd/MMMM/yyyy`.
Ajouter un lien vers la page d’accueil.

#### 2.2.3. Faire le lien entre la page clients et les sous-pages client

> clients.jsp

Autour de chaque nom de client, ajouter un lien qui pointe vers l’URL `/client/{id}`.
De cette manière, l’utilisateur peut cliquer sur le nom d’un client pour en voir le détail.

### 2.3. Utilisation d’un convertisseur

#### 2.3.1. Créer le nouveau convertisseur

> ClientConverter.java

Dans  le package `fr.insee.bar.converter`, créer une classe `ClientConverter` qui implémente de l’interface `Converter<String, Client>`.
Ne pas oublier le stéréotype `@Component` sur la classe. 
Implémenter la méthode `convert` avec un appel à `clientDao.find(id)`.

#### 2.3.2. Simplifier le contrôleur

> ClientController.java

Modifier la signature de la méthode pour remplacer le `Short` par un `Client`.
Supprimer le DAO du contrôleur.

#### 2.3.3. Enregistrer le convertissuer

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

## 3. Intercepteurs

> Terminal

```bash
git add .
git commit -m "TP2 <idep>"
git checkout tp3-enonce
git pull
```

### 3.1. Créer un intercepteur qui mesure la durée de la requête
 
> TimerInterceptor.java
 
L’intercepteur implémente l’interface `HandlerInterceptor`.
Démarrer un chronomètre (`Stopwatch` de la librairie guava) dans la méthode `preHandle`.
Enregistrer ce chronomètre en tant qu’attribut de la requête.
Dans la méthode `postHandle`, imprimer dans la console l’URI de la requête et le temps écoulé.

### 3.2. Enregistrer l’intercepteur

> dispatcher-servlet.xml

Déclarer l’intercepteur auprès de la servlet de Spring MVC.
Penser à exclure les URL commençant par « /static », car sinon on passe aussi dans l’intercepteur pour le fichier CSS.

```xml
<mvc:interceptors>
	<mvc:interceptor>
		<mvc:mapping path="/**"/>
		<mvc:exclude-mapping path="/static/**"/>
		<bean class="fr.insee.bar.interceptor.TimerInterceptor" />
	</mvc:interceptor>
</mvc:interceptors>
```

### 3.3. Placer l’employé connecté en session

> EmployeInterceptor.java

Créer et enregistrer un intercepteur `EmployeInterceptor`.
Dans l’intercepteur, récupèrer l’employé connecté dans la session.
Si aucun employé n’est présent en session, récupérer le grâce au service `EmployeProvider` et sa méthode `provide()` et placer l’objet obtenu dans la session.

### 3.4. Créer une nouvelle page pour l’ajout d’un client

> nouveau-client.jsp

Créer une nouvelle JSP qui ne contient qu’un titre.

> NouveauClientController.java

Créer un nouveau contrôleur qui dirige vers cette page.

> clients.jsp

Sur la page de la liste des clients, ajouter un lien qui dirige vers la nouvelle page.

### 3.5. Vérifier que l’employé connecté a le droit de se rendre sur la page d’ajout d’un nouveau client

#### 3.5.1. Vérifier les droits dans le contrôleur 

> NouveauClientController.java

Dans la signature de la méthode, ajouter un objet `Employe`.
Grace au service `EmployeService`, vérifier que l’employé possède le rôle de responsable.
Si oui, le diriger vers la nouvelle page `nouveau-client.jsp`, sinon, le rediriger vers la page `clients.jsp`.

#### 3.5.2. Créer et déclarer un résolveur d’argument pour la classe `Employe`

> EmployeResolver.java

Dans la méthode `resolveArgument`, récupérer l’objet `Employe` qui est dans la session.

> dispatcher-servlet.xml

Déclarer ce nouveau résolveur d’argument auprès de la servlet de Spring MVC.

```xml
<mvc:annotation-driven conversion-service="conversionService">
	<mvc:argument-resolvers>
		<bean class="fr.insee.bar.resolver.EmployeResolver" />
	</mvc:argument-resolvers>
</mvc:annotation-driven>
```

### 3.6. Tester

1. Démarrer le serveur tel quel et vérifier qu’on peut se rendre sur la page.
2. Dans le fichier web.xml, activer le profile de serveur à la place du profile de responsable.
3. Démarrer le serveur et vérifier qu’on ne peut pas se rendre sur la page.

> web.xml

```xml
<context-param>
	<param-name>spring.profiles.active</param-name>
	<param-value>serveur</param-value>
</context-param>
```

> :question: Quand on change le profile dans le fichier web.xml, Spring instancie une autre implémentation de l’interface `EmployeProvider` au chargement du contexte. Il y a en effet deux versions de la classe :
> - `ResponsableProvider` annotée `@Profile("responsable")`, qui fournit un employé ayant le rôle de *responsable*,
> - `ServeurProvider` annotée `@Profile("serveur")`, qui fournit un employé ayant le rôle de *serveur*.

> Seule une seule des deux versions existe dans le contexte Spring. L’annotation `@Autowired` peut donc être utilisée sans problème pour injecter un `EmployeProvider`.

## 4. Formulaires

> Terminal

```bash
git add .
git commit -m "TP3 <idep>"
git checkout tp4-enonce
git pull
```

### 4.1. Ajouter un nouveau client

#### 4.1.1. Créer un contrôlleur qui dirige vers le formulaire de saisie d’un nouveau client

> NouveauClientController.java

Pour le moment il comporte deux méthodes :

* une annotée `@ModelAttribute` qui retourne les modalités de l’énumeration `Client.Titre`,
* l’autre associée à l’URL `GET /client/nouveau` et qui dirige vers le formulaire d’ajout d’un nouveau client.
 
#### 4.1.2. Compléter la page qui permet de créer un nouveau client

> nouveau-client.jsp

La page doit comprendre un formulaire `<form>` qui servira à poster les données.
Le formulaire possède les éléments suivants :

* un menu déroulant (`<select>`) pour le titre (Monsieur ou Madame) ;
* un champ de texte pour le nom ;
* un champ de texte pour l’adresse email ;
* un champ de texte pour la date de naissance au format *jj/mm/aaaa* ;
* un bouton « Créer » qui poste les données du formulaire vers le serveur (`<button type="submit">`).

:question: Pour remplir le menu déroulant, utiliser la balise `<c:forEach>`. Pour le reste, utiliser des balises HTML natives.

![Formulaire nouveau client](images/formulaire-nouveau-client.png)

#### 4.1.3. Enregistrer le nouveau client en base de données

> NouveauClientController.java

Ajouter une nouvelle méthode associée à l’URL `POST /client/nouveau` qui prend en paramètre un objet `Client` annoté `@ModelAttribute` et qui encapsule les données postées depuis le formulaire.

:question: Pour que le format de la date soit bien pris en compte par Spring MVC, penser à ajouter une annotation `@DateTimeFormat(pattern = "dd/MM/yyyy")` dans la classe `Client`.

Sans contrôles préalables, insérer le nouveau client en base (méthode `ClientDao.insert)`.
Rediriger vers la liste des clients.

#### 4.1.4. Afficher un message au dessus de la liste des clients

> NouveauClientController.java

Placer le client nouvellement créé dans un *flashAttribute* de manière à pouvoir y accéder après la redirection. 

> clients.jsp

Si un client est accessible dans le modèle, afficher un message de succès de la forme : « Le client *mail* a été créé avec succès ».

:question: Pour obtenir un message de succès, utiliser la class CSS `class="success"`.

### 4.2. Modifier un client existant

#### 4.2.1. Créer le contrôlleur adéquat

> ModificationClientController.java

Comme précédemment, le contrôleur contient trois méthodes :

* une pour la liste des titres,
* une associée à l’URL `GET /client/modification`,
* et une associée à l’URL `POST /client/modification`.

:exclamation: Attention, cette fois, la méthode qui affiche le formulaire doit le pré-remplir et donc prendre en argument le client issu de la base pour l’ajouter au modèle.

Pour faire la modification en base, utiliser sans contrôles préalables, la méthode `ClientDao.update`.
Ensuite rediriger vers la page d’information du client.

#### 4.2.2. Ajouter un lien vers le formulaire de modification d’un client

> client.jsp

Le lien est paramétré par l’identifiant du client à modifier.

#### 4.2.3. Créer la page du formulaire pré-rempli

> modification-client.jsp

Cette fois ci, pour que les champs soient pré-remplis avec les données issues de la base, utiliser des balises `<form:...` plutôt que les balises HTML natives.

:exclamation: Il ne faut pas oublier d’ajouter un champ caché qui contient l’identifiant du client qu’on est en train de modifier.

![Formulaire modification client](images/formulaire-modification-client.png)

#### 4.2.4. Afficher un message au dessus de la liste des clients

> ModificationClientController.java

Placer un booléen `modification=true` dans un *flashAttribute* de manière à pouvoir y accéder après la redirection. 

> client.jsp

Si un un booléen `modification` est accessible dans le modèle, afficher un message de succès de la forme : « Le client a été modifié avec succès ».

#### 4.2.5. Supprimer la méthode qui retourne la liste des titres

> ModificationClientController.java

Il s’agit de la méthode annotée `@ModelAttribute`. Constater que le menu déroulant est toujours correctement rempli malgré l’absence de cette méthode.

## 5. Validation

> Terminal

```bash
git add .
git commit -m "TP4 <idep>"
git checkout tp5-enonce
git pull
```

### 5.1. Validation élémentaire des objets de la classe `Client`

#### 5.1.1. Ajouter les dépendances nécessaires

> pom.xml

```xml
<dependency>
    <groupId>javax.validation</groupId>
    <artifactId>validation-api</artifactId>
    <version>1.1.0.Final</version>
</dependency>
<dependency>
    <groupId>org.hibernate</groupId>
    <artifactId>hibernate-validator</artifactId>
    <version>5.3.1.Final</version>
</dependency>
```

#### 5.1.2. Annoter la classe `Client`

> Client.java

Les règles sont les suivantes :
 * l’identifiant doit être positif,
 * la taille du nom doit être compris entre 5 et 30 caractères,
 * l’email doit correspondre au patron suivant :  `[-_a-z0-9.]+@[-_a-z0-9]+\.[a-z]{2,4}`,
 * le titre doit être non nul,
 * la date doit être non nulle et située dans le passé.
 
#### 5.1.3. Valider l’objet client dans le contrôleur de modification d’un client
 
> ModificationClientController.java
 
Déclencher la validation de l’objet client posté grace à l’annotation `@Valid`.
Stocker le résultat de cette validation dans un objet de type `BindingResult`.
Si l’objet n’est pas valide, renvoyer vers le formulaire de modification d’un client.
Le formulaire devra être rempli avec les dernières données saisies par l’utilisateur.
 
#### 5.1.4. Afficher les éventuelles erreurs de validation
 
> modification-client.jsp
 
Sous chaque champ du formulaire, ajouter la balise `<form:errors>` appropriée.
On pourra utiliser l’attribut `cssClass="error"` pour avoir mieux voir les messages d’erreurs.
Faire quelques tests pour vérifier que la validation fonctionne comme souhaité.
Essayer par exemple avec une date dont le format n’est pas bon.

#### 5.1.5. Personaliser les messages d’erreurs de validation

> dispatcher-servlet.xml

Ajouter une source de message internationalisée :

```xml
<bean id="messageSource" class="org.springframework.context.support.ResourceBundleMessageSource">
    <property name="basenames">
        <list>
            <value>message</value>
        </list>
   </property>
    <property name="defaultEncoding" value="UTF-8" />
</bean>
```

Déclarer cette source de message auprès d’un validateur :

```xml
<bean id="validator" class="org.springframework.validation.beanvalidation.LocalValidatorFactoryBean">
    <property name="validationMessageSource" ref="messageSource"/>
</bean>
```

Déclarer le validateur auprès de Spring MVC :

```xml
<mvc:annotation-driven conversion-service="conversionService" validator="validator">
```

> message_fr.properties

En suivant les règles de nommage des clés, écrire des messages pour chaque erreur de validation possible.
Par exemple : 

```properties
NotNull.client.titre=Choisir un titre
```

:exclamation: Ne pas oublier le message d’erreur de conversion de la date.

Tester.

### 5.2. Vérifier que l’email du client n’est pas déjà utilisé

#### 5.2.1. Créer un validateur qui vérifie l’unicité de l’email

> ClientValidator.java

Utiliser la fonction `ClientService.emailDejaUtilise` pour faire le contrôle.
En cas d’erreur, le message à afficher est défini par sa clé dans le fichier message_fr.properties.
Pour cela, utiliser la fonction`Errors.rejectValue(String field, String errorCode)` et ajouter une ligne correspondant à `errorCode` dans le fichier de messages.

#### 5.2.2. Utiliser le nouveau validateur dans le contrôleur

> ModificationClientController.java

Injecter le validator dans le contrôleur grâce à l’annotation `@Autowired`.

### 5.3. Ajouter les contrôles au formulaire de création d’un nouveau client.

> nouveau-client.jsp, NouveauClientController.java

:question: Pour exploiter au mieux les mécanismes de Spring MVC, il faut remplacer les balises HTML natives par des balises `<form:…>`. Or on constate que si le `modelAttribute` du formulaire est nul, les attributs `path` génèrent une erreur. Il faut donc préparer le formulaire avec un nouveau client vide dans le contrôleur.

## 6. Ajax

```bash
git add .
git commit -m "TP5 <idep>"
git checkout tp6-enonce
git pull
```

![Formulaire commande cocktails](images/ajax-commande.png)

### 6.1. Créer un contrôleur qui permet de passer une nouvelle commande

> CommandeController.java, accueil.jsp

Ce contrôleur récupère l’employé connecté (*cf.* TP3), l’ajoute au modèle et dirige vers la page existante `commande.jsp`.
Ajouter un lien vers cette page sur la page d’accueil.

### 6.2. Créer un contrôleur qui permet de rechercher un cocktail

> CocktailController.java

En fonction d’une chaîne de caractère `q`, le contrôleur recherche dans la liste des cocktails ceux qui correspondent.
La chaine de caratère `q` est passée en paramètre de la requête et récupérée dans le contrôleur avec annotation `@RequestParam`.
Pour trouver la liste des suggestions correspondant au paramètre `q`, utiliser la méthode `CocktailDao.search`.
Le contrôleur retourne la liste JSON des cocktails suggérés grâce à l’annotation `@ResponseBody`.

:exclamation: Ne pas oublier d’ajouter la librairie Jackson au classpath :

```xml
<dependency>
	<groupId>com.fasterxml.jackson.core</groupId>
	<artifactId>jackson-databind</artifactId>
	<version>2.8.4</version>
</dependency>
```

Tester l’appel à cette fonction en tapant l’URL dans le navigateur : `http://localhost/cocktails/recherche?q=russ`.

### 6.3. Déclencher la recherche d’un cocktail

> recherche.js

Écrire le corps de la fonction `rechercher`.
Cette fonction est appelée à chaque fois que l’utilisateur appuie sur une touche dans le champ de recherche.

La fonction doit faire un appel Ajax vers le contrôleur de recherche d’un cocktail :
 
 * vers l’URL `/cocktails/recherche` 
 * avec la méthode `GET`,
 * et le paramètre `q` URL encodé.
 
Elle reçoit en retour une liste de cocktails.
Dans la fonction `done`, appeler  la fonction `afficherSuggestions` avec en paramètre la liste de cocktails.

:question: On peut ajouter un coktail de la liste de suggestions en cliquant dessus, on peut ensuite le retirer en cliquant sur dessus dans la liste de droite.

### 6.2. Créer un contrôleur qui permet de calculer le montant d’une commande

> CocktailController.java

Ce contrôleur prend en paramètre une liste de cocktail qui sera postée en Ajax grâce à l’annotation `@RequestBody`.
Dans cette liste, les objets cocktails sont incomplets, il ne possèdent qu’un identifiant.
Utiliser la méthode CocktailDao.fill pour récupérer leur prix.
En faire la somme et retourner le résultat tel quel.

:question: C’est l’occasion idéale d’utiliser les fonctionnalités de Java 8 : *stream*, *map / reduce* et *method reference*.

### 6.3. Déclencher la commande d’une liste de cocktails

> recherche.js

Écrire le corps de la fonction `commander`.
Cette fonction est appelée à chaque fois que l’utilisateur appuie sur le bouton « Commander ».

La fonction doit faire un appel Ajax vers le contrôleur de recherche d’un cocktail :
 
 * vers l’URL `/cocktails/commande` 
 * avec la méthode `POST`,
 * et en paramètre, la liste des cocktails sélectionnés sous forme d’une chaine de caractère représentant du JSON.
 
Elle reçoit en retour le prix de la commande.
Dans la fonction `done`, appeler  la fonction `afficherPrix` avec en paramètre le prix.

> :question: Toute la difficulté réside dans la création de la liste des cocktails sélectionnés. Il faut parcourir les éléments `li.hidden` du bloc `#commande` et ajouter leur contenu un à un à un tableau vide. Chaque élément est ajouté sous la forme `{id: valeur}`. Cette forme représente en JSON un objet de type cocktail qui ne conteint qu’un identifiant.

Tester que tout fonctionne.

## 7. Exceptions

```bash
git add .
git commit -m "TP6 <idep>"
git checkout tp7-enonce
git pull
```

### 7.1. Vérifier que seul un responsable peut modifier ou créer un nouveau client

#### 7.1.1. Créer une nouvelle exception `BarDroitException`

> BarDroitException.java

La faire hériter de `BarException` au travers de `BarHttpException`.

#### 7.1.2. Créer une méthode qui vérifie que l’employé est un responsable

> EmployeService.java

La nouvelle méthode doit lever une exception de type `BarDroitException` si l’employé n’est pas un responsable.

#### 7.1.3. Réaliser cette vérification dans les contrôleurs

> NouveauClientController.java, ModificationClientController.java

Utiliser cette méthode dans les deux contrôleurs de création et de modification d’un client.

#### 7.1.4. Créer un contrôleur qui traite cette exception

> ExceptionController.java

Annoter ce contrôleur `ControllerAdvice`. Écrire une méthode qui est appelée dès qu’une exception de type `BarDroitException` est levée. Ce contrôleur dirige vers une JSP appelée `exception.jsp`. Cette JSP devra afficher le message d’erreur de l’exception.
Le contrôleur doit en plus retourner un code HTTP 403 (Forbidden).

#### 7.1.5. Créer une page d’erreur

> exception.jsp

Dans la nouvelle JSP, afficher le message d’erreur de l’exception.

#### 7.1.6. Modifier le profile pour pouvoir vérifier que le contrôle fonctionne

> web.xml

Remplacer `responsable` par `serveur`.

### 7.2. Vérifier qu’une commande comporte au moins un cocktail

#### 7.2.1. Créer une nouvelle exception `BarCommandeException`

> BarAjaxException.java

La faire hériter de `BarException` au travers de `BarAjaxException`.

#### 7.2.2. Créer une méthode qui vérifie qu’une commande n’est pas vide

> CocktailService.java

La nouvelle méthode doit lever une exception de type `BarCommandeException` si la commande ne contient pas de cocktail.

#### 7.2.3. Réaliser cette vérification dans le contrôleur

> CocktailControlleur.java

#### 7.2.4. Créer un contrôleur qui traite cette exception

> ExceptionController.java

Écrire une méthode qui est appelée dès qu’une exception de type `BarCommandeException` est levée. Le contrôleur doit en plus retourner un code HTTP 400 (Bad request) et le message d’erreur dans un objet `HttpEntity`.

#### 7.2.5. Afficher une erreur si la commande est passée sans contenir aucun cocktail

> recherche.js

Ajouter un *callback* `fail` en cas d’erreur. Dans ce *callback*, faire appel à la fonction `afficherErreur` avec le message d’erreur;

## 8. Tests

### 8.1. Ajouter les librairies nécessaires aux différents tests

> pom.xml

```xml
<dependency>
	<groupId>junit</groupId>
	<artifactId>junit</artifactId>
	<version>4.12</version>
	<scope>test</scope>
</dependency>
<dependency>
	<groupId>org.mockito</groupId>
	<artifactId>mockito-all</artifactId>
	<version>1.10.19</version>
	<scope>test</scope>
</dependency>
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-test</artifactId>
    <version>${spring.version}</version>
    <scope>test</scope>
</dependency>
<dependency>
   <groupId>org.glassfish.web</groupId>
   <artifactId>javax.el</artifactId>
   <version>2.2.4</version>
   <scope>test</scope>
</dependency>
```

### 8.2. Écrire des tests unitaires

Écrire des tests unitaires pour les méthodes de `AccueilController` et pour les méthodes `NouveauClientController` (à part pour `nouveauClient`).

#### 8.2.1 Préparer les tests de `AccueilController`

> AccueilControllerTestCase.java

 1. Injecter le contrôleur à tester grâce à l’annotation `@InjectMocks`.

 2. Déclarer un attribut de type `MockMvc`.

 3. Dans la méthode `@Before`, initialiser les *mocks* : `MockitoAnnotations.initMocks(this);`.

 4. Grâce à la classe `Whitebox`, valoriser l’attribut name du contrôleur.

 5. Instancier l’objet `MockMvc` en mode *stand-alone* :

```java
this.mockMvc = MockMvcBuilders
	.standaloneSetup(accueilController)
	.setViewResolvers(new InternalResourceViewResolver("/WEB-INF/views", "jsp"))
	.build();
```

#### 8.2.2 Tester la méthode welcome()

 * tester que le status est 301,
 * tester qu l’attribut `message` n’existe pas dans le modèle.

#### 8.2.3 Tester la méthode hello()

 * tester que le status est 200,
 * tester que l’attribut `message` existe dans le modèle,
 * tester que le nom de la vue est `accueil`.

#### 8.2.4 Préparer les tests de `NouveauClientController`

> NouveauClientControllerTestCase.java

 1. Injecter le contrôleur à tester grâce à l’annotation `@InjectMocks`.
 2. Injecter des *mocks* des services et DAO grâce à l’annotation `@Mock`.
 3. Écrire la méthode `@Before`.

#### 8.2.5 Tester la méthode nouveauClientPost() pour un formulaire valide

 1. Mocker le comportement des méthodes `validate` et `insert`.
 2. Exécuter un POST avec en paramètre des données de formulaire valides
 3. Puis tester que :
  * le status est 302,
  * l’attribut client n’existe pas,
  * le modèle ne contient aucune erreur,
  * l’url de redirection est `/clients`,
  * il existe un attribut flash qui s’appelle `nouveauClient`.
 
#### 8.2.5 Tester la méthode nouveauClientPost() pour un formulaire non valide

 1. Mocker le comportement des méthodes `validate` et `insert`.
 2. Exécuter un POST avec en paramètre des données de formulaire valides sauf pour l’email
 3. Puis tester que :
  * le status est `OK`,
  * l’attribut client existe,
  * le modèle contient une erreur,
  * le nom de la vue est `nouveau-client`.

### 8.3. Écrire des tests d’intégration

> AccueilControllerTestCase.java, NouveauClientControllerTest.java

Écrire des tests d’intégration pour toutes les méthodes de `AccueilController` et pour les méthodes `NouveauClientController`.

En particulier, cette fois-ci, tester la méthode `/client/nouveau` pour un profile "serveur".

## 9. Configuration

Remplacer toute la configuration xml par de la configuration Java. Penser à adapter le ficihier `web.xml`. Supprimer les fichiers `applicationContext.xml` et `dispatcher-servlet.xml`.
