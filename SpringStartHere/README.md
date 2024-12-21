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



