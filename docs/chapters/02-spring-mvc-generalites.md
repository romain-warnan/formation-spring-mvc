<!-- .slide: data-background-image="images/spring.png" data-background-size="1200px" class="chapter" -->
## 2
### Spring MVC : généralités





<!-- .slide: class="slide" -->
<h3>Classement des frameworks web en 2017</h3>
<div class="center">
    <img src="images/web-frameworks.png" style="width: 500px" />
</div>





<!-- .slide: class="slide" -->
### Dépendances Maven
```xml
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-webmvc</artifactId>
    <version>${spring.version}</version>
</dependency>
<dependency>
    <groupId></groupId>javax.servlet</groupId>
    <artifactId>javax.servlet-api</artifactId>
    <version>3.1.0</version>
    <scope>provided</scope>
</dependency>
<dependency>
    <groupId></groupId>javax.servlet.jsp</groupId>
    <artifactId>jsp-api</artifactId>
    <version>2.2</version>
    <scope>provided</scope>
</dependency>
<dependency>
    <groupId></groupId>jstl</groupId>
    <artifactId>jstl</artifactId>
    <version>1.2</version>
</dependency>
```





<!-- .slide: class="slide" -->
<h3>Modèle MVC</h3>
<p>Modèle &ndash; Vue &ndash; Contrôleur</p>
<div class="center">
    <img src="images/modele-mvc.png" style="width: 1000px" />
</div>





<!-- .slide: class="slide" -->
### Le contrôleur frontal : ServletDispatcher
Toutes les requêtes sont interceptées par la même servlet

ServletDispatcher, fournie par Spring MVC

 1. intercepte la requête
 2. délègue la requête au contrôleur adéquat
 3. récupère le modèle
 4. transmet le modèle au générateur de vue
 5. récupère la vue générée
 6. retourne la réponse au client

La servlet possède son propre contexte Spring





<!-- .slide: class="slide" -->
### Déclarer la servlet ServletDispatcher
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
Attention : « / » et pas « /* »





<!-- .slide: class="slide" -->
### Configurer les servlets
Dans l’exemple, on redirige toutes les URL vers Spring MVC

On peut exclure certaines ressources
 - ressources statiques par exemple

Attention : l’ordre de déclaration dans web.xml est important

```xml
<servlet>
    <servlet-name>default</servlet-name>
    <servlet-class>org.apache.catalina.servlets.DefaultServlet</servlet-class>
    <load-on-startup>1</load-on-startup>
</servlet>
<servlet-mapping>
    <servlet-name>default</servlet-name>
    <url-pattern>/static/*</url-pattern>
</servlet-mapping>
```





<!-- .slide: class="slide" -->
<h3>Hiérarchie des contextes</h3>
<div class="center">
    <img src="images/double-contexte.png" style="width: 700px" />
</div>





<!-- .slide: class="slide" -->
### Le résolveur de vues
Indiquer à Spring quelle vue utiliser
- JSP, Tiles…

En général
- `InternalResourceViewResolver`
- À paramétrer pour faire la relation entre une chaîne de caractère et une JSP

```xml
<bean id="viewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
    <property name="prefix">
        <value>/WEB-INF/views/</value>
    </property>
    <property name="suffix">
        <value>.jsp</value>
    </property>
</bean>
```