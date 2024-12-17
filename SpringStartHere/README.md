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

