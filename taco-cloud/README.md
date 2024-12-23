# Appunti Rapidi
<!-- TOC -->
- [Appunti Rapidi](#appunti-rapidi)
  - [Capitolo 2](#capitolo-2)
  - [@Controller](#controller)
    - [@RequestMapping](#requestmapping)
    - [Model Object](#model-object)
    - [Filtro (Stream API)](#filtro-stream-api)
    - [Nota sul codice DesignTacoController.java](#nota-sul-codice-designtacocontrollerjava)
    - [Nota sul passaggio degli attributi da Model a View](#nota-sul-passaggio-degli-attributi-da-model-a-view)
    - [Thymeleaf attribute: th:field](#thymeleaf-attribute-thfield)
  - [Lombok](#lombok)
    - [@Valid](#valid)
    - [Thymeleaf attribute: th:errors](#thymeleaf-attribute-therrors)
    - [Thymeleaf utility objects](#thymeleaf-utility-objects)
    - [Impelementazione alternativa di `HomeController`](#impelementazione-alternativa-di-homecontroller)
    - [Disattivare view caching in development](#disattivare-view-caching-in-development)
  - [Capitolo 3](#capitolo-3)
    - [H2 Embedded Database](#h2-embedded-database)
    - [@Repository](#repository)
    - [@Autowired](#autowired)
    - [schema.sql e data.sql](#schemasql-e-datasql)
    - [Spring Data JDBC](#spring-data-jdbc)
  - [JPA](#jpa)
    - [Ingredient Entity](#ingredient-entity)
    - [OrderRepository](#orderrepository)
    - [@Query](#query)
<!-- TOC -->
## Capitolo 2

## @Controller
Questo stereotipo, denota una classe come Controller. Un Controller è responsabile
della gestione delle richieste HTTP. Una volta gestita una richiesta, può inviare i dati
ad una *view* per la loro renderizzazione, oppure può scrivere direttamente nel
body della risposta, nel caso di un servizio (RESTful).

Tale annotazione, dunque, identifica una classe come Controller, segnandola come candidata
per il **component scanning**, in modo tale che Spring creerà un'istanza della classe come
un bean nell'application context di Spring.

### @RequestMapping
Tale annotazione permette di mappare richieste ai metodi di un Controller, oppure al controller 
stesso (in tal caso, l'annotazione viene apposta sulla classe). Nel seguente esempio, `@RequestMapping`
specifica che la classe `DesignTacoController` gestirà le richieste il cui path inizia con `/design`.

```java
@RequestMapping("/design")
public class DesignTacoController {
    //...
}
```
Posso raffinare l'utilizzo di tale annotazione, ad esempio, con `@GetMapping`. Nel seguente esempio, 
sto dicendo che, quando arriva una richiesta HTTP GET a `/design`, Spring MVC chiamerà il metodo
`showDesignForm()`:
```java
@RequestMapping("/design")
public class DesignTacoController {
    @GetMapping
    public string showDesignForm() {
        return "design";
    }
}
```
Nota che il valore String "design" restituito dal metodo, sarà il nome logico della **vista** che 
verrà utilizzata per renderizzare il modello del browser.

### Model Object
Un **Model Object** in Spring MVC è un oggetto Java che viene utilizzato per trasferire dati tra il
controller e la view. Elemento centrale del modello MVC, rappresenta i dati e la logica di business
dell'applicazione.

### Filtro (Stream API)
Per maggiori info guarda la relativa lezione di POO. Per capire basta guardare il seguente
codice che ho scritto in `DesignTacoController.java`:
```java
// Semplice filtro per tipologia di ingrediente. Utilizziamo la Stream API
private Iterable<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
    // converto la lista degli ingredients in uno stream. Filtro gli oggetti di tale stream in base ad un predicato.
    // Infine converto lo stream contenente gli oggetti che rispettano il predicato in una lista.
    return ingredients.stream().filter(x->x.getType().equals(type)).collect(Collectors.toList());
}
```

### Nota sul codice DesignTacoController.java
Il seguente metodo, nella parte finale, filtra gli ingredienti in base al tipo. Dunque, per ogni tipo verrà restituita
una lista che verrà impostata come attributo del model da passare alla view.
```java
@ModelAttribute
    public void addIngredientsToModel(Model model) {
        List<Ingredient> ingredients = Arrays.asList(
                new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
                //...
        );

        Type[] types = Ingredient.Type.values();
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
        }
    }
```
Ecco perché dalla view posso accedere, ad esempio, alla lista wrap:
```thymeleafiterateexpressions
<div th:each="ingredient : ${wrap}">
```

### Nota sul passaggio degli attributi da Model a View
Le librerie relative alle view sono disaccoppiate dai framework web. Dunque, non possono accedere direttamente all'astrazione del Model.
Per tale ragione Spring, prima di inoltrare la richiesta alla view, copia gli attributi del model nei request attributes, poiché questi sono accessibili alla view.

### Thymeleaf attribute: th:field
`th:field` serve a collegare un campo HTML (come un input o una checkbox) con una proprietà di un oggetto del modello in Spring MVC.
Questo mi permette, quando ho un form, di bindare automaticamente i dati inseriti da un utente ad un oggetto Java del backend.

Funziona in questo modo:
1.	Input HTML → Proprietà Java:
    Quando l’utente compila il form e lo invia, Spring MVC usa th:field per sapere dove inserire i dati nell’oggetto Java.
2.	Oggetto Java → Input HTML:
    Quando la pagina viene caricata, Spring MVC usa th:field per riempire automaticamente i campi del form con i valori dell’oggetto Java (se esistono).

In pratica è come un ponte che collega un campo HTML con una proprietà Java.

Semplice esempio dimostrativo:
```java
public class User {
    private String name;
    // Getter e Setter
}
```
Controller:

```java
@Controller
public class UserController {
    @GetMapping("/form")
    public String showForm(Model model) {
        model.addAttribute("user", new User());
        return "form";
    }
    
}
```
Pagina HTML con Thymeleaf:
```html
<form th:object="${user}" action="/submit" method="post">
    <input type="text" th:field="*{name}" />
    <input type="text" th:field="*{name}" />
</form>
```
Quando il form viene inviato, Spring MVC prenderà il valore inserito dall'utente e lo metterà
nella proprietà `name` dell'oggetto `user`.

## Lombok
Libreria per l’autogenerazione di boilerplate tramite annotazioni. Ad esempio @Getter per
generare metodi getter per tutti i campi di una classe. Per aggiungerlo
come dipendenza, in `pom.xml`:
```xml
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
</dependency>
```
Inoltre, nella sezione build devo aggiungere il seguente codice:
```xml
<configuration>
    <excludes>
        <exclude>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
        </exclude>
    </excludes>
</configuration>
```

### @Valid
Tale annotazione dice a Spring di validare un oggetto in cui sono state utilizzate le annotazioni della JavaBean Validation API.
Ad esempio, nella classe `DesignTacoController.java`:
```java
    @PostMapping
    public String processTaco(@Valid Taco taco, Errors errors, @ModelAttribute TacoOrder tacoOrder) {
        tacoOrder.addTaco(taco);
        if (errors.hasErrors()) {
            return "orderForm";
        log.info("Processing taco: {}", taco);
        return "redirect:/orders/current";
    }
```
l'annotazione `@Valid` sta praticamente dicendo a Spring MVC di effettuare una validazione dell'oggetto Taco (sulla base 
delle regole inserite nella classe stessa), non appena viene submittato il form, ma prima di eseguire il metodo `processTaco`. 
Se ci sono errori, questi veranno inseriti nell'oggetto `Errors`.

### Thymeleaf attribute: th:errors
Thymeleaf offre un modo semplice e conveniente per accedere agli errori nella view: utilizzando `th:errors`.

### Thymeleaf utility objects
Thymeleaf fornisce degli utility objects a cui si può accedere con `#`. Ad esempio, l'utility object `#fields` fornisce metodi
per lavorare con i campi di un form.

### Impelementazione alternativa di `HomeController`
Nella prima implementazione, l'HomeController è stato così definito:
```java
@Controller
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "home";
    }
    
}
```
Essendo molto semplice come controller, può essere implementato in una maniera alternativa, utilizzabile in caso di controller
che non fanno altro che inoltrare richieste ad una view.
```java
@Configuration
public class WebConfig implements WebMvcConfigurer {
    public static void main(String[] args) {
        SpringApplication.run(TacoCloudApplication.class, args);
    }
}

@Override
public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/").setViewName("home");
}
```

### Disattivare view caching in development
Il caching delle view è una caratteristica importante e utilissima in production, ma in development, ovviamente, risulta essere
scomoda siccome la cache non ci permetterà di visualizzare le modifiche apportate al template. In Thymeleaf, per disattivare la cache
devi inserire la seguente riga in `application.properties`:
```.properties
spring.thymeleaf.cache=false
```
**Importante**: rimuovere questa riga, oppure settarla a `true`, quando passi in produzione.

## Capitolo 3

### H2 Embedded Database
Per scopi di sviluppo, utilizziamo l'embedded database H2. Per attivarlo, lo aggiungiamo tra le dipendenze del progetto:
```xml
<dependency>
    <groupId>com.h2database</groupId>
        <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency>
```
Utilizzando gli Spring Boot DevTools, possiamo accedere alla console H2 al seguente indirizzo:
```
http://localhost:8080/h2-console
```
Siccome il nome del db viene generato automaticamente, può risultare particolarmente comodo cambiare il nome impostando
le seguenti proprietà nel file `application.properties`.
```properties
spring.datasource.generate-unique-name=false
spring.datasource.name=tacocloud
```

### @Repository
Una repository è una componente che si occupa di operazioni CRUD verso una soluzione di storage. Spring Data JPA mira a ridurre
il boilerplate, dunque la complessità, necessario per l'accesso ai dati.<br>
`@Repository`: una classe è annotata con `@Repository` per indicare che si occupa di operazioni CRUD. Di solito
viene utilizzata con implementazioni DAO (Data Access Object) o repository che interagiscono con database,
cache, ecc.

### @Autowired
I servizi vengono forniti ai controller tramite dependency injection. La dependency injection in Spring è dichiarata
tramite l’annotazione @Autowired.

### schema.sql e data.sql
Se nel classpath è presente un file chiamato `schema.sql`, questo verrà eseguito prima che l'applicazione venga eseguita. Il file va
posizionato in `src/main/resources/data.sql`. Questo file ci serve per la definizione del db. Possiamo avere, nella stessa posizione, anche 
un file `data.sql`, anch'esso verrà eseguito prima che l'applicazione parta, per popolare il db.
È tuttavia meglio (mi è capitato proprio di non riuscire a vedere i dati di popolamento) aggiungere la seguente riga in `application.properties`:
```properties
spring.jpa.defer-datasource-initialization=true
```
In questo modo mi assicuro che l'inizializzazione del db (esecuzione di `schema.sql`) avvenga prima del popolamento (`data.sql`).


### Spring Data JDBC
Per aggiungerla come dipendenza:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jdbc</artifactId>
</dependency>
```

## JPA
Per aggiungere JPA come dipendenza:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
```

### Ingredient Entity
JPA richiede che una Entity abbia un costrutture senza argomenti di default, dunque, con Lombok, utilizzo l'annotazione
`@NoArgsContstructor`. Tuttavia, voglio che per questa classe venga utilizzato sempre il costruttore con gli argomenti,
per cui imposto il costruttore senza argomenti come `private`:
```java
@NoArgsConstructor(access=AccessLevel.PRIVATE)
```
Inoltre, poiché nella classe Ingredient vi sono dei campi dichiarati come `final` che devono essere settati,
faccio in modo che Lombok li inizalizzi al loro valore di default, settando `force=true`:
```java
@NoArgsConstructor(access=AccessLevel.PRIVATE, force=true)
```
Ho bisogno anche di un `@RequiredArgsConstructor`, ovvero un costruttore che prende un parametro per ogni campo `final`, dunque
che richiede inizializzazione e per i valori annotati con `@NonNull` che non sono stati inizializzati dove sono stati dichiarati.
Di default, quanto utilizziamo `@Data`, questa annotazione è **implicita**, dunque è presente anche se non la utilizziamo. Tuttavia,
quando utilizziamo `@NoArgsConstructor` viene rimossa, dunque dobbiamo aggiungerla esplicitamente.

### OrderRepository
Spring Data riesce a determinare il tipo di query da effettuare in base alla firma del metodo e dal contesto. Ad esempio,
in `OrderRepository`, supponiamo che io necessiti di un metodo per trovare tutti gli ordini effettuati a un determinato Zip Code,
in uno specifico intervallo di tempo. Posso dichiarare il metodo come segue nella repository:
```java
List<TacoOrder> readOrdersByDeliveryZipAndPlacedAtBetween(String deliveryZip, Date startDate, Date endDate);
```
Innanzitutto, Spring Data leggerà il verbo `read` come prima cosa e capirà che la query da effettuare deve fetchare qualcosa dal db.
Al posto di `read` avrei potuto mettere anche `get` oppure `find`. `By` segna l'inizio delle proprietà da matchare: in particolare
deve matchare `.deliveryZip` con il l'argomento passato al metodo (oppure `.delivery.zip`). L'`And` viene interpretato, banalmente, come un And.
Infine, `Between` specifica che dopo l'And vi è un intervallo in cui devono cadere i corrispondenti valori.
Il soggetto del metodo, in questo caso `Orders` è opzionale, dunque può essere omesso. Potrei anche inserire `OrderBy` alla fine.
Di seguito riportata una lista degli operatori utilizzabili nella firma del metodo:
- IsAfter, After, IsGreaterThan, GreaterThan
- IsGreaterThanEqual, GreaterThanEqual
- IsBefore, Before, IsLessThan, LessThan
- IsLessThanEqual, LessThanEqual
- IsBetween, Between
- IsNull, Null
- IsNotNull, NotNull
- IsIn, In
- IsNotIn, NotIn
- IsStartingWith, StartingWith, StartsWith
- IsEndingWith, EndingWith, EndsWith
- IsContaining, Containing, Contains
- IsLike, Like
- IsNotLike, NotLike
- IsTrue, True
- IsFalse, False
- Is, Equals
- IsNot, Not
- IgnoringCase, IgnoresCase
### @Query
Se le naming conventions non dovessero bastare, potrei sempre definire una query customizzata come segue:
```java
@Query("Order o where o.deliveryCity='Seattle'")
List<TacoOrder> readOrdersDeliveredInSeattle();
```
Il linguaggio utilizzato per la definizione delle query può essere sia JPQL che SQL.
Esempi:
```java
// JPQL
@Query("SELECT p FROM Product p WHERE p.price > :price")
List<Product> findExpensiveProducts(@Param("price") Double price);

//SQL
@Query(value = "SELECT * FROM product WHERE price > :price", nativeQuery = true)
List<Product> findExpensiveProductsNative(@Param("price") Double price);
```
