<!-- .slide: data-background-image="images/spring.png" data-background-size="1200px" class="chapter" -->
## 4
### Méthodes de contrôleur





<!-- .slide: class="slide" -->
### Types de retours possibles

| Type                 | Remarque                                                        |
| -------------------- | ----------------------------------------------------------------|
| `void`               | Il faut écrire soi-même dans la réponse                         |
| `View`               | Objet contenu le nom de la vue                                  |
| `ModelAndView`       |                                                                 |
| `RedirectView`       |                                                                 |
| `String`             | Nom de la vue                                                   |
| `HttpHeaders`        | Réponse sans corps                                              |
| `ResponseEntity<T>`  | Encapsule un objet JSON, ou un fichier ou une image par exemple |





<!-- .slide: class="slide" -->
### Types de retours possibles

Les autres types sont considérés différemment selon l’annotation portée par la méthode

| Annotation                     | Conséquence                                                             |
| ------------------------------ | ----------------------------------------------------------------------- |
| `@ResponseBody`                | L’objet est sérialisé en JSON                                           |
| Aucune ou `@ModelAttribute`    | L’objet est ajouté au model avec le nom spécifié ou le nom de sa classe |





<!-- .slide: class="slide" -->
### Types d’arguments possibles
Requête, réponse et session

| Type                    | Remarque                  |
| ----------------------- | ------------------------- |
| `ServletRequest`        |                           |
| `HttpServletRequest`    |                           |
| `WebRequest`            |                           |
| `NativeWebRequest`      |                           |
| `HttpSession`           | Jamais `null`             |
| `InputStream / Reader`  | Corps de la requête       |
| `OutputStream / Writer` | Corps de la réponse       |





<!-- .slide: class="slide" -->
### Types d’arguments possibles
Éléments de la requête

| Type                    | Remarque                                                                           |
| ----------------------- | ---------------------------------------------------------------------------------- |
| `RequestParam`          | /foo/bar?baz=<span style="color:red">123</span>                                    |
| `PathVariable`          | /<span style="color:red">foo</span>/bar?baz=123                                    |
| `MatrixVariable`        | /a=<span style="color:red">1</span>;b=<span style="color:red">2</span>/bar?baz=123 |
| `RequestHeader`         | Entête                                                                             |
| `RequestAttribute`      | Attribut                                                                           |
| `RequestPart`           | *Upload* de fichiers                                                               |





<!-- .slide: class="slide" -->
### Types d’arguments possibles
Modèle et vue

| Type                 | Remarque                        |
| -------------------- | ------------------------------- |
| `Model`              |                                 |
| `Map`                | Modèle                          |
| `View`               |                                 |
| `RedirectView`       | URI de la redirection           |
| `ModelAndView`       |                                 |
| `RedirectAttributes` | Attributs en cas de redirection |





<!-- .slide: class="slide" -->
### Types d’arguments possibles
Ajax

| Type                 | Remarque                        |
| -------------------- | ------------------------------- |
| `@RequestBody`       |                                 |
| `RequestEntity<T>`   | ```HttpMethod method = request.getMethod();<br/>URI url = request.getUrl();<br/>String body = request.getBody();``` |





<!-- .slide: class="slide" -->
### Types d’arguments possibles
Formulaires

| Type                 | Remarque                                      |
| -------------------- | --------------------------------------------- |
| `@ModelAttribute`    | Objet posté par un formulaire                 |
| `BindingResult`      | Résultat de la validation de l’objet posté     |
| `Errors`             | Erreurs lors de la validation de l’objet posté |

```java
@PostMapping("/personne")
public String processSubmit(@ModelAttribute("personne") Personne personne, BindingResult result, Model model) {
```

Attention à l’ordre @ModelAttribute / BindingResult





<!-- .slide: class="slide" -->
### Convertisseurs

Spring est capable de convertir les données entrantes
 - Chaîne de caractère
 - Nombres
 - Dates (avec un peu d’aide)
  - `@DateTimeFormat(pattern = "dd-MM-yyyy")`
  - à mettre sur le champ de l’objet posté
  - ou devant le paramètre de la méthode du contrôleur
 - XML
  - nativement grâce à JAXB, objets annotés
 - JSON
  - ajouter Jackson au classpath

```java
@RequestMapping("/date/{date}")
public String date(Model model, @PathVariable("date") @DateTimeFormat(pattern = "dd-MM-yyyy") Date date)
```





<!-- .slide: class="slide" -->
### Convertisseurs personnalisés

On peut définir ses propres convertisseurs

```java
@Component
public class PersonneConverter implements Converter<String, Personne> {

    @Autowired
    private PersonneService personneService;

    @Override
    public Personne convert(String id) {
        Personne personne = personneService.trouver(Long.valueOf(id));
        return personne;
    }
}
```





<!-- .slide: class="slide" -->
### Enregistrer un convertisseur personnalisé

Il faut enregistrer le nouveau convertisseur

```xml
<mvc:annotation-driven conversion-service="conversionService" />
<bean id="conversionService" 
    class="org.springframework.format.support.FormattingConversionServiceFactoryBean">
    <property name="converters">
    <set>
        <bean class="x.y.z.PersonneConverter" />
    </set>
    </property>
</bean>
```

Résultat dans le contrôleur

```java
@RequestMapping("/personne/{id}")
public String accueil(@PathVariable("id") Personne personne, Model model) {
```





<!-- .slide: class="slide" -->
### Résolveur d’argument

Injecter d’autres objets du modèle

Indépendants de cette requête en particulier
 - utilisateur en session
 - langue de l’utilisateur connecté
 - …

Implémenter l’interface `HandlerMethodArgumentResolver`

Déclarer ce nouveau *bean*

```xml
<mvc:annotation-driven conversion-service="conversionService">
    <mvc:argument-resolvers>
        <bean class="x.y.z.UtilisateurResolver" />
    </mvc:argument-resolvers>
</mvc:annotation-driven>
```





<!-- .slide: class="slide" -->
### Exemple de résolveur d’argument

```java
@Component
public class UtilisateurResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return Utilisateur.class.equals(parameter.getParameterType());
    }
    
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        HttpSession session = request.getSession(true);
        return session.getAttribute("utilisateur");
    }
}
```

```java
@GetMapping("/administration")
public String administration(Model model, Utilisateur utilisateur) {
```