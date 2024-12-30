# Appunti del libro Spring Start Here

# Capitolo 2
- Il `groupId` di Maven permette di raggruppare più progetti correlati.

## Configuration Class
Una classe annotata con `@Configuration` funge da classe di configurazione e serve per configurare lo Spring 
Context. Una delle cose che posso fare con una classe di configurazione è aggiungere bean allo Spring Context. Per
farlo, è necessario definire un metodo che restituisce l'istanza dell'oggetto che si vuole aggiungere al contesto
e annotare tale metodo con l'annotazione `@Bean`. Nota che il nome del metodo diventerà il nome del bean.

## @Configuration
Un altro modo per aggiungere istanze di oggetti al context è tramite lo stereotype `@Configuration` che viene
posto sulla classe che si vuole aggiungere. Quando l'applicazione creerà lo Spring Context, automaticamente
istanzierà anche tutte le classi annotate così e le aggiungerà al contesto.

In generale, l'utilizzo degli stereotypes, prevede due step:
1. Annotare le classi che vogliamo aggiungere (come bean) allo Spring Context con `@Configuration`.
2. Annotare la classe di configurazione con `@ComponentScan` per specificare dove andare a cercare le classi 
da aggiungere.

# Capitolo 3
## Wiring e Autowiring
Il **wiring** è il processo di collegamento diretto dei bean invocando i metodi che creano tali bean.

L'**autowiring** è il processo attuato da Spring che automaticamente rileva e inietta le dipendenze tra componenti.

Nel progetto `Ch-3.1`, inizialmente aggiungo manualmente il collegamento tra la classe Person e la classe Parrot. Per fare questo,
nella classe di configurazione `ProjectConfig` che ho creato invoco il metodo che aggiunge il parrot al context anche nel metodo che
aggiunge la Person al contesto, come segue:
```java
package org.example.ch3_1.config;

import org.example.ch3_1.main.Parrot;
import org.example.ch3_1.main.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProjectConfig {
    @Bean
    public Parrot parrot() {
        return new Parrot("Koko");
    }

    @Bean
    public Person person() {
        Person p =  new Person();
        p.setName("Augusto");
        p.setParrot(parrot());
        return p;
    }
}
```
Si potrebbe pensare che in questo modo vengano create due istanze differenti del bean parrot: una che viene inserita nello Spring context
e un'altra che viene aggiunta al bean Person. Tuttavia, Spring riesce a capire che chiamando il metodo `parrot()` ci vogliamo riferire
al bean inserito nel contesto. Infatti, se il bean esiste già nel contesto, Spring, invece di invocare il metodo `parrot()` nuovamente,
passerà direttamente l'istanza presente nel contesto.

### Autowired
Non conviene annotare direttamente un campo di una classe con `@Autowired`, come mostrato di seguito, poiché non si avrebbe più la possibilità di rendere il
campo final e, dunque, di impedire di cambiarne il valore dopo l'inizializzazione. 
```java
@Component
public class Person {
    private String name;
    @Autowired
    private Parrot parrot;
    // private final Parro parrot; would not compile
}
```
Generalmente, `@Autowired` viene usato sul costruttore.


# Capitolo 4
Ricorda che non ha senso aggiungere annotazioni stereotype su interfacce o classi astratte poiché
esse non possono essere istanziate.

## Separazione delle responsabilità con le annotazioni
L'annotazione `@Component` è generica e non fornisce alcun dettaglio circa le responsabilità dell'oggetto che stai implementando.
Vi sono poi le annotazioni `@Service` e `@Repository`: i servizi sono gli oggetti responsabili dell'implementazione dei casi d'uso. Gli oggetti repository, invece, sono quelli responsabili della gestione della persistenza.


# Capitolo 5
Spring utilizza vari approcci per la creazione dei beans e tali approcci sono chiamati *scopes*. In particolare, in questo capitolo vedremo
lo scope **Singleton** e lo scope **Prototype**.

## Singleton
Singleton è lo scope di default, nonché quello più utilizzato, di Spring per la gestione dei beans nel suo contesto. Tuttavia, non bisogna
confondere questo concetto con il design pattern Singleton. Infatti, il design pattern Singleton prevede che vi sia una sola istanza di una classe
in tutta l'applicazione, mentre il concetto di Singleton in Spring, riguarda l'unicità del nome, ovvero una classe può avere più istanze, purché
esse abbiano nomi differenti. Nota, inoltre, che i Singleton beans non sono progettati per essere sincronizzati e, sebbene sia possibile la sincronizzazione
manuale, non è una good practice.
Uno dei vantaggi della constructor injection è che mi permette di dichiarare il campo del bean (quello da iniettare) immutabile, ovvero *final*.

## Prototype
L'idea dei prototype beans è molto semplice: ogni qualvolta richiedi un riferimento ad un bean prototype-scoped, Spring crea una nuova istanza
dell'oggetto. In questo caso, dunque, il framework gestisce il tipo dell'oggetto e crea una nuova istanza ogni volta che qualcuno richiede un riferimento
a tale bean. Per cambiare lo scope a prototype: `@Scope(BeanDefinition.SCOPE_PROTOTYPE)`.

## Capitolo 6 - Using aspects with Spring AOP
Gli aspects rappresentano un modo in cui il framework intercetta le chiamate a metodi ed eventualmente altera l'esecuzione degli stessi.
Questo approccio è chiamato *aspect-oriented programming* (AOP). Uno dei vantaggi degli aspects, ad esempio, è il miglioramento della comprensione
del codice poiché si possono separare parti di un codice dalla logica di business (ad es. il libro separa tutta la parte del logging).

Dunque, un aspect è semplicemente un pezzo di logica che il framework esegue quando chiami uno specifico metodo di tua scelta.<br>
Terminologia:
- **aspect**: *quale* codice vuoi che Spring esegua quando invochi uno specifico metodo;
- **advice**: quando l'applicazione dovrebbe eseguire la logica dell'aspect;
- **pointcut**: quali metodi il framework deve intercettare per eseguire la logica degli aspects.
- **joint point**: evento che triggera l'esecuzione di un aspect (in Spring è sempre una chiamata a metodo);
- **target object**: il bean che dichiara il metodo intercettato da un aspect.

Quando rendi un bean il target di un aspect, Spring non ti restituirà direttamente un'istanza del bean, ma ti darà un proxy object che invoca
la logica dell'aspect invece del metodo effettivo del bean. Questo approccio è chiamato **weaving**. L'oggetto proxy innanzitutto esegue la logica
dell'aspect, successivamente delega la chiamata del metodo reale alla classe effettiva che lo implementa.

Per creare un aspect:
- abilitare il meccanismo degli aspects annotando la classe di configurazione con `@EnableAspectJAutoProxy`.
- Creare una nuova classe e annotarla con l'annotazione `@Aspect` (tale classe deve essere aggiunta allo Spring
  context, dunque usando @Bean oppure le annotazioni stereotype).
- Definire un metodo che implementerà la logica dell'aspect e dirà a Spring quando e quali metodi intercettare utilizzando
    l'annotazione advice.
- Implementare la logica dell'aspect.

Nota che `@Aspect` non è uno stereotype, dunque Spring non lo vedrà come bean. Per farlo, va dichiarato esplicitamente come bean utilizzando le tecniche note viste in precedenza.

![AspectJ pointcut language](imgs/AspectJPointcutLanguage.png)
