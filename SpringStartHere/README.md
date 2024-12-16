# Appunti del libro Spring Start Here

# Capitolo 2
- Il `groupId` di Maven permette di raggruppare più progetti correlati.

## Configuration Class
Una classe annotata con `@Configuration` funge da classe di configurazione e serve per configurare lo Spring 
Context. Una delle cose che posso fare con una classe di configurazione è aggiungere bean allo Spring Context. Per
farlo, è necessario definire un metodo che restituisce l'istanza dell'oggetto che si vuole aggiungere al contesto
e annotare tale metodo con l'annotazione `@Bean`. Nota che il nome del metodo diventerà il nome del bean.