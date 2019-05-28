<!-- .slide: data-background-image="images/spring.png" data-background-size="1200px" class="chapter" -->
## 8
### Validation





<!-- .slide: class="slide" -->
### Validation

Contrôler les données avant de les intégrer dans le système

Validation côté client : facultative
 - Pour le confort de l’utilisateur
 - Javascript

Validation côté serveur : __obligatoire et systématique__
 
Contrôles unitaires sur le format des données (type, taille…)
 - on les retrouve quelle que soit l’application
 - Spring offre une solution clé en main

Contrôles métiers
 - parfois compliqués, ils dépendent largement de l’application
 - Spring permet d’intégrer ces contrôles dans son schéma de fonctionnement





<!-- .slide: class="slide" -->
### Validation unitaire

Spring s’appuie sur la JSR 303 : Bean Validation
 - Java Specification Requests 

Une série d’annotations
 - à ajouter sur les champs de la classe
 - définissant des contraintes en plus du type
  - taille, non nullité…
 - acceptant plus ou moins de paramètres
  - min, message…

Ces annotations seules ne font rien
 - il faut ajouter un moteur de traitement
 - en général on utilise l’implémentation d’Hibernate





<!-- .slide: class="slide" -->
### Mise en place de la validation unitaire

```xml
<!– JSR 303 : Bean validation -->
<dependency>
    <groupId>javax.validation</groupId>
    <artifactId>validation-api</artifactId>
    <version>1.1.0.Final</version>
</dependency>

<!-- Bean validation : implémentation -->
<dependency>
    <groupId>org.hibernate</groupId>
    <artifactId>hibernate-validator</artifactId>
    <version>5.3.1.Final</version>
</dependency>
```





<!-- .slide: class="slide" -->
### Liste des annotations de validation

`@Min, @Max`
```java
@Min(0)
private Integer quantite;
```

`@NotNull, @Null`
```java
@NotNull
private Ville ville;
```

`@Pattern`
```java
@Pattern(regexp = "0\\d-(?:\\d{2}-){3}\\d{2}")
private String numeroTelephone;
```

`@Past, @Future`
```java
@Future
private Date dateRendezVous;
```






<!-- .slide: class="slide" -->
`@Size`
```java
@Size(min = 1, max = 50)
private String pseudo;

@Size(min = 1, max = 3)
private List<Long> choixUtilisateur;
```

`@AssertTrue, @AssertFalse`
```java
@AssertTrue
private Boolean conditionsAcceptees;
```

`@DecimalMin, @DecimalMax`
```java
@DecimalMax(0.20)
private BigDecimal reduction;
```

`@Digits`
```java
@Digits(integer = 2, fraction = 2)
private BigDecimal tauxChomage;
```





<!-- .slide: class="slide" -->
### Déclencher la validation dans le contrôleur

Dans la signature de la méthode de contrôleur

Ajouter l’annotation `@Valid` devant l’objet posté

Ajouter un paramètre de type `BindingResult`
 - juste après l’argument `@Valid`
 - pour stocker le résultat de la validation
  - nombre d’erreurs, messages d’erreurs

`@Valid @ModelAttribute` peut être simplifié en `@Valid`

Dans le corps de la méthode
 - utiliser la méthode `hasErrors()` de l’objet bindingResult





<!-- .slide: class="slide" -->
### Exemple de validation unitaire

```java
public class Personne {
    
    @Size(max = 500)
    private String nom;
    
    public String getNom() {…
    public void setNom(String nom) {…
}
```

```java
@PostMapping("/modification")
public String modificationPersonnePost(@Valid Personne personne, BindingResult result) {
    if (result.hasErrors()) {
        return "modification-personne";
    }
	…
    return "redirect:/liste-personnes";
}
```

Le BindingResult est ajouté au modèle, donc :
 - on peut afficher les erreurs dans la JSP
 - le formulaire sera prérempli avec les données que l’on vient de saisir (y compris les erreurs)





<!-- .slide: class="slide" -->
### Validation métier

Implémenter l’interface Validator
 - `boolean supports(Class<?> clazz)`
  - quelles classes peut valider ce valideur ?
  - utiliser `class.isAssignableFrom` ou `class.equals`
 - `void validate(Object target, Errors errors)`
  - comment valider l’objet ?
  - `target` : l’objet à valider
  - `errors` : contient les précédentes erreurs.

`Errors` est l’objet `BindingResult`
 - en effet, `BindingResult` implémente l’interface `Errors`

Utiliser les méthodes du type :
 - `errors.reject(errorCode);`
 - `errors.rejectValue(field, errorCode);`
 - `ValidationUtils.rejectIfEmptyOrWhitespace(errors, field, errorCode);`
 - L’attribut `field` est une chaîne de caractère sans la partie `modelAttribute`





<!-- .slide: class="slide" -->
### Exemple de validation métier

```java
@Component
public class PersonneValidator implements Validator {
    @Autowired
    private PersonneService personneService;
    @Override
    public boolean supports(Class<?> clazz) {
        return Personne.class.isAssignableFrom(clazz);
    }
    @Override
    public void validate(Object target, Errors errors) {
        Personne personne = (Personne) target;
        if (!personneService.estValide(personne)) {
            errors.reject("erreur.globale.personne");
        }
        if (!personneService.adresseValide(personne)) {
            errors.rejectValue("adresse", "erreur.personne.adresse");
        }
    }
```

```java
@PostMapping("/modification")
public String modificationPersonnePost(@Valid Personne personne, BindingResult result) {
    personneValidator.validate(personne, result);
    if (result.hasErrors()) {
        return "modification-personne";
    }
	…
```





<!-- .slide: class="slide" -->
### Afficher les erreurs de validations

Si la validation échoue, on veut afficher les champs en erreur

Différence entre `fieldError` et `globalError`

Méthodes `hasErrors()`, `hasGlobalErrors()` et `hasFieldErrors()`

```html
<form:errors cssClass="erreur" />
<form:input type="text" path="nom" />
<form:errors cssClass="erreur" path="nom" />
```





<!-- .slide: class="slide" -->
### Source de message d’erreur

Instancier une source de message
```xml
<bean id="messageSource" class="org.springframework.context.support.ResourceBundleMessageSource">
    <property name="basenames">
        <list>
            <value>messages</value>
        </list>
    </property>
    <property name="defaultEncoding" value="UTF-8" />
    <property name="alwaysUseMessageFormat" value="true" />
</bean>
```

Déclarer cette source de message au valideur
```xml
<bean id="validator" class="org.springframework.validation.beanvalidation.LocalValidatorFactoryBean">
    <property name="validationMessageSource" ref="messageSource"/>
</bean>
```

Paramétrer Spring MVC pour utiliser le valideur
```xml
<mvc:annotation-driven conversion-service="conversionService" validator="validator">
```





<!-- .slide: class="slide" -->
### Fichiers de ressources i18n

```xml
<bean id="messageSource" class="org.springframework.context.support.ResourceBundleMessageSource">
    <property name="basenames">
        <list>
            <value>messages</value>
            <value>erreurs</value>
        </list>
    </property>
</bean>
```

Recherche les fichiers de la forme suivante à la racine du classpath :

 – `messages_fr.properties`,
 
 – `messages_fr_FR.properties`,
 
 – `messages_en.properties`,
 
 – `erreurs_fr.properties`,
 
 – `erreurs.properties`





<!-- .slide: class="slide" -->
### Source de message d’erreur

Pour les messages d’erreur on choisit le code

```
erreur.personne.globale=Erreur globale
```

Pour les message de la validation unitaire il faut respecter une norme :
```
Annotation.modelAttribute.champ=Erreur champ
```

Cas particulier des erreurs de conversion :
```
typeMismatch.modelAttribute.champ=Erreur champ
```

Exemple
```
Size.personne.nom=Le nom doit contenir au plus {1} caractères.
NotNull.personne.nom=Le nom de la personne doit être renseigné.
typeMismatch.personne.date=Le format de la date est incorrect.
```





<!-- .slide: data-background-image="images/tp.png" data-background-size="500px" class="tp" -->
## [TP5](https://github.com/romain-warnan/formation-spring-mvc#5-validation)