# mosca-ceca-project
(i'm not dyslexic i swear)

## what is this repo about?
Mosca Ceca is an upcoming game developed by my class. This repository is just a folder full of prototype ideas. There's no certainty that those ideas are gonna be used in the final game. Enjoy.

# Mosca Ceca Project
Il gioco è una sorta di lotta strategica tra N agenti che si scontrano per accaparrarsi risorse, energia e spazi all'interno della scacchiera di gioco. Il Gioco termina quando si verifica una delle seguenti eventualità:
- Tutti i giocatori meno uno vengono eliminati
- Il tempo termina
- Si raggiunge il numero totale di turni
Nel primo caso, ovviamente, viene dichiarato vincitore l'unico agente rimasto. Nel secondo e terzo caso, tuttavia, il vincitore viene decretato utilizzando una classifica ordinata in base a: 
- Territori conquistati
- Pezzi di stoffa non utilizzati (in caso di parità sui territori conquistati, si conta il numero di potenziali territori conquistabili)
- Energia rimanente (in caso di parità anche sui pezzi di stoffa)
Di seguito, le linee guida che regolano il funzionamento delle varie parti del gioco. 

### Agenti
L'unità giocante. Ogni agente ha un comportamento che varia da sviluppatore a sviluppatore, tuttavia è obbligato a seguire le regole qui sotto elencate.
#### Comportamento
L'agente ha a disposizione, in ogni turno, una sola di queste opzioni: 
- Può **spostarsi** in verticale, orizzontale o diagonale, in un'altra casella libera (cioè non occupata da un altro agente)
    - N.B. *Tale azione comprende la raccolta di un'unità di stoffa, in caso la casella ne contenga una*
- **Stazionare su una colonnina di ricarica** per ricaricare unità di energia (10 a turno)
- Consumare quattro (4) unità di stoffa, su una *casella libera*, per **trasformarla in un territorio proprio**.
- Consumare otto (8) unità di stoffa, su una *casella occupata da un altro agente*, per **assimilarla al proprio territorio**. 
- Attaccare un giocatore se esso si trova in una delle caselle adiacenti (solo se l'agente avversario sta invadendo il suo territorio)
Nel fare questo, l'agente deve però tenere da conto che:
- Muoversi e assimilare territorio costa una (1) unità di energia. Se il suo livello energetico raggiunge lo 0, l'agente viene eliminato dal gioco. 
- Attaccare un avversario costa due (2) unità di energia e ha possibilità di riuscita del 50%. Chi perde, perde N unità di energia.
#### Informazioni conosciute
Ogni agente in ogni momento conosce:
- dove si trova e quanto dista la colonnina di ricarica più vicina;
- lo stato delle caselle adiacenti;
- Tutte le caselle (posizione e numero) che hanno un suo fazzoletto.

### Risorse
Sono presenti due tipi di risorse
- **Energia**, presente nelle colonnine di ricarica. Le colonne di ricarica hanno una quantità infinita di energia, ma ne trasferiscono 10 unità ad ogni turno.
- **Stoffa**, presente in alcuni punti della mappa. La quantità di stoffa presente in un punto è limitata ed ogni volta che un agente ne raccoglie un’unità, la risorsa corrispondente diminuisce di un’unità.

Ogni risorsa viene generata automaticamente dalla scacchiera all'inizio del gioco.
E' importante sottolineare che **un solo agente per volta** può usufruire di una colonnina di ricarica

### Classe 'Gioco'
La classe gioco è il fulcro di ciò che avviene all'interno dell'esecuzione. Essa si occupa di: 
- Tenere conto delle informazioni sul gioco. Tra queste, in particolare, ricordiamo:
    - Tempo trascorso da inizio gioco e tempo mancante alla fine della partita
    - Numero di turni mancanti al termine del gioco
- Gestire gli agenti, ovvero:
    - Permettere loro l'accesso al gioco a inizio partita
    - Ricevere le loro richieste di movimento, verificare se sono eseguibili e, in tal caso, eseguirle, altrimenti negarle e chiederne altre
    - Escluderli dal gioco quando si verifica l'occasione
        - Ciò può comprendere anche l'esclusione di un agente in caso non fornisca la sua mossa entro un ragionevole periodo di tempo
    - Sopperire al problema delle collisioni tra agenti (es. due agenti che vogliono operare sulla stessa risorsa/casella)
- Aggiornare la classifica con il passare dei turni
- Comunicare con la scacchiera per verificare l'eseguibilità delle mosse desiderate dagli agenti
La **gestione di un turno tipo** da parte della classe Gioco è la seguente: 
1. Viene verificato che ci siano ancora tempo e turni a disposizione per poter iniziare il nuovo turno
2. Viene inviata a tutti gli agenti la richiesta di invio delle proprie mosse desiderate
    - Dopo un periodo ragionevole di tempo, se un agente ancora non ha inviato la propria mossa si presume che esso si sia in qualche modo bloccato. In tal caso, esso viene escluso e la classe Gioco stampa un messaggio ad hoc (del tipo "AGENT XYZ ERROR: NO RESPONSE IN TIME") per permettere al suo sviluppatore di verificare cosa ci sia che non va
3. Le varie proposte vengono confrontate tra loro per controllare che non ce ne siano di uguali.
    - In caso due proposte coincidano, l'idea era far partire una specie di testa-croce per decidere quale delle due verrà tenuta e quale, invece, scartata.
    - Se invece un giocatore decide di stazionare in una casella e un altro vuole accedervi, ovviamente verrà scartata la seconda richiesta.
4. Una volta appurato che tutte le scelte sono eseguibili e non causano concorrenza né tanto meno vanno contro le regole, la classe Gioco comunica con la scacchiera per muovere gli agenti su di essa, aggiornare lo status delle caselle occupate dai giocatori e colonnine di ricarica, e rimuovere eventuali unità di risorsa raccolte. 
5. Il turno è terminato: il counter dei turni viene a questo punto diminuito di uno, e si passa al turno successivo. 