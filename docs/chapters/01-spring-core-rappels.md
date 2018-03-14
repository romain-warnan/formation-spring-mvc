<!-- .slide: data-background-image="images/spring.png" data-background-size="1200px" class="chapter" -->
## 1
### Spring core : rappels





<!-- .slide: class="slide" -->
### Spring core
Spring core : conteneur qui implémente le *pattern* «&nbsp;inversion de contrôle&nbsp;» (IoC)

Définitions des objets (*bean*)
 - XML : `applicationContext.xml`
 - Annotations
  - Stéréotypes : `@Component`, `@Service`, `@Repository`, `@Controller`…
  - Injection : `@Autowired`, `@Resource`, `@Value`…
  - Configuration : `@Configuration`





<!-- .slide: class="slide" -->
### Spring core
Au démarrage de l’application, chargement du contexte
Web : *listener*
```xml
<context-param>
    <param-name>contextConfigLocation</param-name>
    <param-value>classpath:applicationContext.xml</param-value>
</context-param>
<listener>
    <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
</listener>
```
Batch : de manière explicite en Java
```java
try(AbstractApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml")){
    Service service1 = (Service) context.getBean("beanName");
    Service service2 = context.getBean(Service.class);
    Service service3 = context.getBean("beanName", Service.class);
}
```





<!-- .slide: class="slide" -->
### Beans et injection des dépendances
En XML
```xml
<bean id="cacheManager" class="org.springframework.cache.ehcache.EhCacheCacheManager">
    <property name="cacheManager" ref="ehcache" />
</bean>
<bean id="ehcache" class="org.springframework.cache.ehcache.EhCacheManagerFactoryBean">
    <property name="configLocation" value="classpath:ehcache.xml" />
</bean>
```





<!-- .slide: class="slide" -->
### Beans et injection des dépendances
Par annotation, version `@Autowired`
```java
@Repository
public class AdresseDaoImpl implements AdresseDao {
```
```java
@Service
public class AdresseServiceImpl implements AdresseService {

@Autowired
private AdresseDao adresseDao;
```
Par annotation, version `@Resource`
```java
@Repository("adresseDao1")
public class AdresseDaoImpl implements AdresseDao {
```
```java
@Service
public class AdresseServiceImpl implements AdresseService {

@Resource(name = "adresseDao1")
private AdresseDao adresseDao;
```





<!-- .slide: class="slide" -->
### Activer la détection des annotations
```xml
<context:component-scan base-package="fr.insee" />
```





<!-- .slide: class="slide" -->
### Injection d’une source de propriétés
```
prefix.key.a=Valeur A
prefix.key.b=Valeur B
```

```xml
<context:property-placeholder location="file://${catalina.base}/webapps/*.properties" 
	file-encoding="UTF-8"
	ignore-resource-not-found="true"
	ignore-unresolvable="true"
	order="0" />
<context:property-placeholder location="classpath:*.properties"
	file-encoding="UTF-8"
	ignore-resource-not-found="true"
	ignore-unresolvable="true"
	order="1"/>
```

```java
@Component
public class ComponentImpl {

@Value("${prefix.key.a}")
private String a;
```