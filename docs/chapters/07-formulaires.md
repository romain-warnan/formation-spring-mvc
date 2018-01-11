<!-- .slide: data-background-image="images/spring.png" data-background-size="1200px" class="chapter" -->
## 7
### Formulaires





<!-- .slide: class="slide" -->
### Les formulaires
On sait afficher des informations provenant du serveur

Comment les modifier ? À l’aide d’un formulaire
 - Balise `<form>`
 - Méthode POST de préférence
 - Champ `<input>`, `<select>`, `<textarea>`
   - avec un name renseigné
   - `disabled="false"`
 - Des données sont envoyées vers le serveur
 - Sous forme de couple clé / valeur :
```
        personne.nom=Nom
        personne.prenom=Prénom
        personne.email=nom.prenom@gmail.com
```





<!-- .slide: class="slide" -->
### Les formulaires dans Spring — JSP

Utiliser les balises `<form:…>`

```html
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
```

Préciser `modelAttribute` dans la balise `<form:form>`

Dans le contrôleur, ajouter l’objet au modèle
 - L’objet est éventuellement vide
  - en cas de création par exemple
  - ici, `model.addAttribute("personne", new Personne());`
 - Sinon, les propriétés de l’objet servent à pré-remplir le formulaire
 
```html
<form:form action="/personne/modifier" modelAttribute="personne" method="post">
    <form:hidden path="id" />
    <label>Email : </label><form:input type="text" path="email" />
    <button type="submit">Modifier</button>
</form:form>
```





<!-- .slide: class="slide" -->
### Les balise `<form:…>`
Formulaire
```html
<form:form action="/test" modelAttribute="personne">
    <button type="submit">Envoyer</button>
</form:form>
```
```html
<form id="personne" action="/test" method="post">
    <button type="submit">Envoyer</submit>
</form>
```

<form id="personne" action="bravo.html" method="get" style="border: dotted black 1px">
    <button type="submit">Envoyer</button>
</form>

Dans le corps de la balise `<form:form>`, le __path__ est relatif au __modelAttribute__

Champ caché
```html
<form:hidden path="id" />
```
```html
<input id="id" name="id" type="hidden" value="1" />
```






<!-- .slide: class="slide" -->
Champ de texte
```html
<form:input path="email" />
```
```html
<input id="email" name="email" type="text" value="adresse@gmail.com"/>
```
<input id="email" name="email" type="text" value="prenom.nom@gmail.com"/>

Zone de texte
```html
<form:textarea path="verbatim"/>
```
```html
<textarea id="verbatim" name="verbatim">Verbatim, long texte</textarea>
```
<textarea id="verbatim" name="verbatim">Verbatim, long texte</textarea>






<!-- .slide: class="slide" -->
Boutons radio
```html
<form:radiobuttons path="genre.code" items="${genres}" itemLabel="libelle" itemValue="code" />
```
```html
<span>
    <input id="genre.code1" name="genre.code" type="radio" value="1"/>
    <label for="genre.code1">Masculin</label>
</span>
<span>
    <input id="genre.code2" name="genre.code" type="radio" value="2" checked="checked" />
    <label for="genre.code2">Féminin</label>
</span>
```
<span>
    <input id="genre.code1" name="genre.code" type="radio" value="1"/>
    <label for="genre.code1">Masculin</label>
</span>
<span>
    <input id="genre.code2" name="genre.code" type="radio" value="2" checked="checked" />
    <label for="genre.code2">Féminin</label>
</span>

Menu déroulant simple
```html
<form:select path="genre.code" items="${genres}" itemLabel="libelle" itemValue="code" />
```
```html
<select id="genre.code" name="genre.code">
    <option value="1" selected="selected">Masculin</option>
    <option value="2">Féminin</option>
</select>
```
<select id="genre.code" name="genre.code">
    <option value="1" selected="selected">Masculin</option>
    <option value="2">Féminin</option>
</select>






<!-- .slide: class="slide" -->
Cases à cocher
```html
<form:checkboxes path="jours" items="${jours}" itemLabel="libelle" itemValue="code" />
```
```html
<span>
    <input id="jours1" name="jours" type="checkbox" value="1"/>
    <label for="jours1">Lundi</label>
</span>
<span>
    <input id="jours2" name="jours" type="checkbox" value="2" checked="checked" />
    <label for="jours2">Mardi</label>
</span>
<span>
    <input id="jours7" name="jours" type="checkbox" value="7" />
    <label for="jours7">Dimanche</label>
</span>
<input type="hidden" name="_jours" value="on" />
```
<span>
    <input id="jours1" name="jours" type="checkbox" value="1"/>
    <label for="jours1">Lundi</label>
</span>
<span>
    <input id="jours2" name="jours" type="checkbox" value="2" checked="checked" />
    <label for="jours2">Mardi</label>
</span>
<span>
    <input id="jours" name="jours" type="checkbox" value="3" />
    <label for="jours3">Mercredi</label>
</span>
<span>
    <input id="jours4" name="jours" type="checkbox" value="4" checked="checked" />
    <label for="jours4">Jeudi</label>
</span>
<span>
    <input id="jours5" name="jours" type="checkbox" value="5" />
    <label for="jours5">Vendredi</label>
</span>
<span>
   <input id="jours6" name="jours" type="checkbox" value="6" />
   <label for="jours6">Samedi</label>
</span>
<span>
    <input id="jours7" name="jours" type="checkbox" value="7" />
    <label for="jours7">Dimanche</label>
</span>






<!-- .slide: class="slide" -->
Menu déroulant multiple
```html
<form:select path="jours" items="${semaine}" itemLabel="libelle" itemValue="code" multiple="multiple" />
```
```html
<select id="jours.code" name="jours" multiple="multiple">
    <option value="1">Lundi</option>
    <option value="2" selected="selected">Mardi</option>
    <option value="3">Mercredi</option>
    <option value="4" selected="selected">Jeudi</option>
    <option value="5">Vendredi</option>
    <option value="6">Samedi</option>
    <option value="7">Dimanche</option>
</select
<input type="hidden" name="_jours" value="1" />
```
<select id="jours.code" name="jours" multiple="multiple">
    <option value="1">Lundi</option>
    <option value="2" selected="selected">Mardi</option>
    <option value="3">Mercredi</option>
    <option value="4" selected="selected">Jeudi</option>
    <option value="5">Vendredi</option>
    <option value="6">Samedi</option>
    <option value="7">Dimanche</option>
</select





<!-- .slide: class="slide" -->
### Cas des enumérations

Pour les énumérations tout est automatique :
 - inutile de préciser l’attribut *items*
 - inutile de transmettre les *items* au modèle
 - inutile de préciser l’attribut *itemValue*

```java
public enum Genre {
    M("Masculin"),
    F("Féminin");
    …
}
```

```html
<form:select path="genre" >
    <form:options itemLabel="libelle" />
</form:select>
```

```html
<select id="genre.code" name="genre.code">
    <option value="M" selected="selected">Masculin</option>
    <option value="F">Féminin</option>
</select>
```





<!-- .slide: class="slide" -->
### Les formulaires côté serveur

Dans le contrôleur, les champs sont récupérés dans un objet
 - annoté `@ModelAttribute`
 - attributs de l’objet :  même nom que le *name* (ou le *path*) dans la JSP
 - l’objet doit respecter la norme Java Bean
 - l’objet peut être un objet du modèle
 - en pratique c’est souvent un objet sur mesure

Java bean
 - accesseurs et des mutateurs pour chaque attributs (*getters* / *setters*)
 - constructeur sans argument
 - chacun des attributs doit aussi respecter cette norme

```java
@PostMapping("/personne/ajout")
public String ajoutPersonne(@ModelAttribute Personne personne) {
```






<!-- .slide: class="slide" -->
### Champs vides

Si le champ n’est pas présent dans le formulaire :
 - En java : `null`

Si le champ est présent mais pas rempli :
 - En java : `""` (chaîne de caractère vide)





<!-- .slide: class="slide" -->
Dans le contrôleur, il faut préparer l’affichage du formulaire

Remplir les éventuelles listes
 - menus déroulants, cases à cocher, boutons radio…
 - exécuté à chaque appel du contrôleur, accepte des paramètres (resolver)

```java
@ModelAttribute("villes")
public List<Ville> ville() {
    return villeService.findAll();
}
```

Fournir l’objet qui sert à pré-remplir le formulaire

```java
@GetMapping("/modification/{personne}")
public String afficher(@PathVariable("personne") Personne personne, Model model){
    model.addAttribute("personne", personne);
    return "modification-personne";
}
```

```html
<form:form modelAttribute="personne" action="/modification">
```





<!-- .slide: class="slide" -->
### Redirect After Post

Après traitement du formulaire côté serveur
 - pour éviter de créer deux fois le même objet,
 - ou payer deux fois, etc.

Redirection vers une page qui fait uniquement de l’affichage
 - syntaxe : `return "redirect:/personnes";`
 - redirection temporaire, 302 (par défaut)

Après une redirection, on perd les objets du modèle
 - Spring MVC gère une flashMap stockée dans la session
 - la flashMap est déversée dans le modèle généré par le 2<sup>e</sup> contrôleur

```java
@PostMapping("/modification")
public String modification(@ModelAttribute Personne personne, RedirectAttributes redirectAttributes) {
    // Modification de la personne
    redirectAttributes.addFlashAttribute("personne", personne);
    return "redirect:/personnes";
}
```





<!-- .slide: class="slide" -->
### Formulaire : exemple complet

```html
<form:form action="/modification" modelAttribute="personne" method="post">
	Nom : <form:input type="text" path="nom" />
	<button type="submit">Modifier</button>
</form:form>
```

```java
@GetMapping("/modification/{personne}")
public String affichage(@PathVariable("personne") Personne personne, Model model) {
    model.addAttribute("personne", personne);
    return "modification-personne";
}

@PostMapping("/modification")
public String traitement(@ModelAttribute Personne personne, RedirectAttributes redirectAttributes) {
    personneService.modifier(personne);
    redirectAttributes.addFlashAttribute("personne", personne);
    return "redirect:/personnes";
}
```

```html
<c:if test="${not empty personne}">
	Le client <c:out value="${personne.nom}" /> a été créée avec succès.
</c:if>
```





<!-- .slide: data-background-image="images/tp.png" data-background-size="500px" class="tp" -->
## [TP4](https://github.com/romain-warnan/formation-spring-mvc#4-formulaires)