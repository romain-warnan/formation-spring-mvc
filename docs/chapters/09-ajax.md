<!-- .slide: data-background-image="images/spring.png" data-background-size="1200px" class="chapter" -->
## 9
### Ajax





<!-- .slide: class="slide" -->
### Rappels sur Ajax

Appel serveur depuis le Javascript

Asynchrone
 - Callback lorsque la réponse est arrivée
  - `done(function(){…})` — `success`
  - `fail(function(){…})` — `error`
  - `always(function(){…})` — `complete`

Envoyer et recevoir des objets JavaScript

Pas de rechargement de la page
 - notion d’application single page

Facile à utiliser avec jQuery (ou avec *fetch*, ou encore *superagent*)
 - `$.ajax(), $.get(), $.post(), $(…).load()`





<!-- .slide: class="slide" -->
### Spring MVC et Ajax

Un contrôleur peut recevoir et retourner des objets JSON

La conversion JAVA &hArr; JSON est automatique
 - il suffit qu’il existe une librairie capable de la réaliser dans le classpath
 - Jackson par exemple

Renvoyer un objet dans le corps de la réponse 
 - &ne; vers une JSP comme d’habitude
 - `@ResponseBody`

Récupérer un objet présent dans le corps de la requête
 - &ne; dans l’URL ou posté par un formulaire
 - `@RequestBody`






<!-- .slide: class="slide" -->
### Conversion JAVA &hArr; JSON

```java
public class Personne {
    private String nom;
    private Integer age;
    private Adresse adresse;
    
    // Getters et Setters
}

public class Adresse {
    private String voie;
    private List<String> complement;
    
    // Getters et Setters
}
```

```json
{
  "nom": "Prénom Nom",
  "age": 33,
  "adresse": {
    "voie": "34 rue des Rosiers",
    "complement": ["complément 1", "complément 2"]
  }
}
```





<!-- .slide: class="slide" -->
### Appel Ajax avec jQuery – GET

```javascript
$.ajax({
    url: '/path/resource',
    method: 'GET',
    data: {
        'p1': encodeURI('Ma chaîne'),
        'p2': 123456
    }
})
.done(function(data, textStatus, xhr) {
    …
})
.fail(function(data, textStatus, error) {
    …
})
.always(function(data, textStatus, error | xhr) {
    …
});
```

`GET http://localhost/path/resource?p1=Ma+cha%C3%Aene&p2=123456`





<!-- .slide: class="slide" -->
### Traiter un GET Ajax avec Spring MVC

```java
@GetMapping(value = "/path/resource", produces = "application/json; charset=UTF-8")
@ResponseBody
public Personne personne(@RequestParam("p1") String p1, @RequestParam("p2") Integer p2) {
   return personneService.trouver(p1, p2);
}
```

```java
@GetMapping(value = "/path/resource", produces = "application/json; charset=UTF-8")
public ResponseEntity<Personne> personne(@RequestParam("p1") String p1, @RequestParam("p2") Integer p2) {
    Personne personne = personneService.trouver(p1, p2);
    ResponseEntity<Personne> response = new ResponseEntity<>(HttpStatus.OK, personne);
    return response;
}
```





<!-- .slide: class="slide" -->
### Appel Ajax avec jQuery – POST

```javascript
$.ajax({
    url: '/path/resource',
    method: 'POST',
    contentType : 'application/json; charset=utf-8',
    data: JSON.stringify(request)
}).done(function(response) {
    …  
});
```

`POST http://localhost/path/resource`

```javascript
{
    "p1": "Ma chaîne",
    "p2": "123456"
}
```





<!-- .slide: class="slide" -->
### Traiter un POST Ajax avec Spring MVC

```java
@PostMapping("/path/resource")
@ResponseStatus(HttpStatus.OK)
public void post(@RequestBody Personne personne) {
    personneService.modifier(personne);
}

```

```java
@PostMapping("/path/resource")
public ResponseEntity<HttpStatus> post(@RequestBody Personne personne) {
    ResponseEntity<HttpStatus> response = new ResponseEntity<>(HttpStatus.OK);
    if (ko) {
        response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    return response;
}
```

`ResponseEntity` > `@ResponseStatus`





<!-- .slide: class="slide" -->
### Ajax avec Spring MVC : cas général

```java
@GetMapping(value = "/path/resource", produces = "application/json; charset=UTF-8")
@ResponseBody
public Commande commande(@RequestBody Personne personne) {
    Commande commande = commandeService.trouver(personne);
    return commande;
}
```





<!-- .slide: class="slide" -->
### Validation des objets postés en Ajax

Le mécanisme de validation fonctionne aussi en Ajax

On peut écrire `@Valid @RequestBody`

Problème pour afficher les messages d’erreur
 - car il n’y a pas de modèle

Solution : regarder dans l’objet `ResultBinding`

```java
List<String> erreurs = result
    .getAllErrors()
    .stream()
    .map(ObjectError::getDefaultMessage)
    .collect(Collectors.toList());
```

Le mécanisme des fichiers de properties n’est pas disponible
 - penser à remplir l’attribut `message` des annotations de validation






<!-- .slide: data-background-image="images/tp.png" data-background-size="500px" class="tp" -->
## [TP6](https://github.com/romain-warnan/formation-spring-mvc#6-ajax)





<!-- .slide: data-background-image="images/question.png" data-background-size="700px" class="exercice" -->
## En plus…






<!-- .slide: class="slide" -->
### Contrôleur qui retourne une image
```java
@GetMapping("image/{file:.+}")
public ResponseEntity<byte[]> image(@PathVariable("nom") String nom) throws IOException {
        File file = …
        try (InputStream in = FileUtils.openInputStream(file)) {
            return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(30, TimeUnit.DAYS))
                .contentType(MediaType.IMAGE_PNG)
                .body(imageAsByteArray(IOUtils.toByteArray(in)));
        }
        return null;
}
```






<!-- .slide: class="slide" -->
### Télécharger un fichier
```java
@GetMapping(value = "/telechargement")
public ResponseEntity<FileSystemResource> telechargement() {
    File file = …
    return ResponseEntity.ok()
        .contentLength(file.length())
        .contentType(MediaType.APPLICATION_OCTET_STREAM)
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName())
        .body(new FileSystemResource(file));
}
```
