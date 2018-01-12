<!-- .slide: data-background-image="images/spring.png" data-background-size="1200px" class="chapter" -->
## 11
### Tests





<!-- .slide: class="slide" -->
### Tester un contrôleur

Classe de base : `MockMvc`

Mocks pour les contrôleurs

Librairie d’assertions adaptée

Utiliser des imports statiques pour la lisibilité du code :
```java
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
```





<!-- .slide: class="slide" -->
### Test d’un contrôleur : préparation

Annoter la classe de test
```java
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:context.xml", "classpath:servlet.xml" })
@ActiveProfiles("profile")
@WebAppConfiguration
```

Injecter le contexte web
```java
@Autowired
private WebApplicationContext wac;
```

Instancier l’objet MockMvc en mode context
```java
this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
```





<!-- .slide: class="slide" -->
### Test d’un contrôleur : exécution

Simuler la requête
```java
mockMvc.perform(get("/accueil"))
```

Puis utiliser des assertions

Tous les objets du contexte existent on peut tester la requête de bout en bout





<!-- .slide: class="slide" -->
### Assertions

Contenues dans la classe : MockMvcResultMatchers
```java
request(), model(), view(), flash(), status()
forwardedUrl(…), redirectedUrl(…)
…
```

Chaque élément possède des assertions spécifiques
```java
status().isOk()
model().attributeExists("personne")
view().name("accueil")
…
```

Pour démarrer les assertions :
```java
mockMvc.perform(get("/accueil"))
    .andExpect(status().isOk()) 
    .andExpect(…)
    …;
```





<!-- .slide: class="slide" -->
### Exemple complet

```java
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext.xml", "classpath:dispatcher-servlet.xml" })
@ActiveProfiles("profile")
@WebAppConfiguration
public class WelcomeControllerTest {

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	@Before
	public void before() {
		this.mockMvc = MockMvcBuilders
			.webAppContextSetup(this.webApplicationContext)
			.build();
	}

	@Test
	public void welcome() throws Exception {
		mockMvc
			.perform(get("/welcome"))
			.andExpect(status().isOk())
			.andExpect(model().attributeDoesNotExist("name"));
	}
```





<!-- .slide: data-background-image="images/tp.png" data-background-size="500px" class="tp" -->
## [TP8](https://github.com/romain-warnan/formation-spring-mvc#8-tests)
