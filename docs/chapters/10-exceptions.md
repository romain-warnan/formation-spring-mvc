<!-- .slide: data-background-image="images/spring.png" data-background-size="1200px" class="chapter" -->
## 10
### Exceptions





<!-- .slide: class="slide" -->
### Exception associée à un contrôleur

`@ControllerAdvice` au lieu de `@Controller`

`@ExceptionHandler` au lieu `@RequestMapping`
 - on spécifie un type d’exception
 - au lieu d’une URL

```java
@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(PersonneException.class)
    public String handlePersonneException(Exception e, Model model) {
        model.addAttribute("message", e.getMessage());
        return "page-erreur";
    }
```





<!-- .slide: class="slide" -->
### Exception associée à un contrôleur

Méthode associée à l’exception la plus spécifique
 - `Throwable` < `Exception` < `IOException` < `FileNotFoundException`

Réponse sous forme `ResponseEntity<T>`
 - ou bien `ResponseEntity<T>`
 - code erreur HTTP grâce à l’annotation `@ResponseStatus`

`ResponseEntity` implique `@ResponseBody`





<!-- .slide: class="slide" -->
### Cas des erreurs 404 NOT FOUND

Par défaut, pas d’exception Spring MVC
 - configurer la servlet Spring MVC dans `web.xml`

```xml
<init-param>
    <param-name>throwExceptionIfNoHandlerFound</param-name>
    <param-value>true</param-value>
</init-param>
```

Dès lors on se retrouve dans la situation précédente
 - il suffit d’intercepter l’exception :

```java
@ExceptionHandler(NoHandlerFoundException.class)
@ResponseStatus(HttpStatus.NOT_FOUND)
public String handleResourceNotFoundException(…){
```





<!-- .slide: data-background-image="images/tp.png" data-background-size="500px" class="tp" -->
## [TP7](https://github.com/romain-warnan/formation-spring-mvc#7-exceptions)
