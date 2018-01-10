<!-- .slide: data-background-image="images/spring.png" data-background-size="1200px" class="chapter" -->
## 3
### Introduction aux contrôleurs





<!-- .slide: class="slide" -->
### Généralités sur les contrôleurs
Stéréotype `@Controller`

Pas d’interface à implémenter ou de classe à étendre

Constructeur par défaut

Méthode de contrôleur `@RequestMapping`





<!-- .slide: class="slide" -->
### Un contrôleur typique

```java
@Controller // Stéréotype
@RequestMapping("/bar") // Namespace (début de l’URI, facultatif)
public class AccueilController { // Nom libre

     @Autowired
     private MessageService messageService; // Injection de dépendance

     @RequestMapping("/accueil") // Fin de l’URI
     public String accueil(Model model) { // Méthode de contrôleur (nom libre, arguments variables)
          model.addAttribute("message", messageService.message(…)); // Enrichissement du modèle
          return "accueil"; // Choix de la vue
     }
}
```

La méthode accueil() est exécuté
 - http://serveur:port/contextPath/bar/accueil
 - Les arguments de la méthodes sont injectés par Spring
 - Liste extensible d’arguments possibles





<!-- .slide: class="slide" -->
### Possibilités de mapping des requêtes

Cas simple :
```java
	@RequestMapping("/accueil")
```

Plusieurs URL vers la même méthode :
```java
	@RequestMapping({ "/accueil", "/" })
```

Préciser la méthode HTTP :
```java
	@RequestMapping(value = "/accueil", method = RequestMethod.GET)
	@GetMapping – @PostMapping – @PutMapping …
```

Présence ou absence de paramètres
```java
	@RequestMapping(value = "/accueil", params = { "a=1", "b!=0", "!c", "d" })
```

Valeur de l’entête de la requête :
```java
	@RequestMapping(value = "/accueil", headers = "Accept=text/html")
```






<!-- .slide: class="slide" -->
Bonus : préciser le type de réponse :
```java
	@RequestMapping(value = "/accueil", produces = "application/json; charset=UTF-8")
```





<!-- .slide: data-background-image="images/question.png" data-background-size="700px" class="exercice" -->
## Exercice






<!-- .slide: class="slide" -->
### Exemple 1 : cas simple
```java
	@RequestMapping("/accueil")
```
 - http://serveur/accueil <!-- .element class="fragment highlight-green" -->
 - http://serveur/ <!-- .element class="fragment highlight-red" -->
 - http://serveur/accueil?sommaire=123456 <!-- .element class="fragment highlight-green" -->
 - http://serveur/accueil/123456 <!-- .element class="fragment highlight-red" -->






<!-- .slide: class="slide" -->
### Exemple 2 : plusieurs URI
```java
    @RequestMapping({ "/accueil", "/" })
```
 - http://serveur/accueil/123456 <!-- .element class="fragment highlight-red" -->
 - http://serveur/accueil <!-- .element class="fragment highlight-green" -->
 - http://serveur/ <!-- .element class="fragment highlight-green" -->
 - http://serveur/accueil?sommaire=123456 <!-- .element class="fragment highlight-green" -->






<!-- .slide: class="slide" -->
### Exemple 3 : méthode HTTP
```java
    @RequestMapping(value = "/accueil", method = RequestMethod.GET)
```
 - GET http://serveur/accueil <!-- .element class="fragment highlight-green" -->
 - PUT http://serveur/accueil <!-- .element class="fragment highlight-red" -->
 - POST http://serveur/accueil?sommaire=123456 <!-- .element class="fragment highlight-red" -->
 - GET http://serveur/accueil?sommaire=123456 <!-- .element class="fragment highlight-green" -->






<!-- .slide: class="slide" -->
### Exemple 4 : paramètres
```java
    @RequestMapping(value = "/accueil", params = { "!sommaire", "geo" })
```
 - http://serveur/accueil?geo=COM-92046&id=123 <!-- .element class="fragment highlight-green" -->
 - http://serveur/accueil <!-- .element class="fragment highlight-red" -->
 - http://serveur/accueil/123456 <!-- .element class="fragment highlight-red" -->
 - http://serveur/accueil?geo=COM-80829 <!-- .element class="fragment highlight-green" -->
 - http://serveur/accueil?sommaire=123456&geo=DE <!-- .element class="fragment highlight-red" -->





<!-- .slide: class="slide" -->
### Récupérer des paramètres dans un contrôleur
`@RequestParam`

http://serveur/accueil<span style="color:red">?nom=Valeur</span>

```java
@Controller
public class AccueilController {

     @Autowired
     private MessageService messageService; 

     @RequestMapping("/accueil")
     public String accueil(@RequestParam("nom") String nom, Model model) { 
          model.addAttribute("message", messageService.message(nom));
          return "accueil";
     }
}
```
Spring récupère la valeur du paramètre « nom » 
 - dans l’URL
 - et l’injecte dans la méthode de contrôleur





<!-- .slide: class="slide" -->
### Récupérer des paramètres dans un contrôleur
`@PathVariable`

http://serveur/personne<span style="color:red">/123456</span>

```java
@Controller
public class AccueilController {

     @Autowired
     private PersonneService personneService;

     @RequestMapping("/personne/{id}")
     public String accueil(@PathVariable("id") Long id, Model model) { 
          model.addAttribute("personne", personneService.personne(id));
          return "personne";
     }
}
```
Il peut y avoir plusieurs `PathVariable`
 - On peut mélanger `PathVariable` et `RequestParam`
 - http://serveur/publication/{sommaire}/{produit}?intertire=1&vue=Complete





<!-- .slide: data-background-image="images/tp.png" data-background-size="500px" class="tp" -->
## [TP1](https://github.com/romain-warnan/formation-spring-mvc#0-récupérer-le-code-source-du-tp) 