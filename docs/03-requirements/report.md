# Analisi del sistema e requisiti

## Requisiti di business
- creare un sistema che permetta di effettuare una partita ad un gioco ispirato a "Sokoban"
- il sistema dovrà disporre di un menù da cui il giocatore portà iniziare una partita

## Modello di dominio
Il modello di dominio comprende le seguenti entità:
- Mappa
- Stanza
- Cella
- Oggetti
- Player


## Requisiti funzionali

### Requisiti utente

- L'utente interagisce tramite GUI
- L'utente può avviare una partita
- L'utente può muovere il player
  - All'interno di una stanza
  - Tra le diverse stanze della mappa
- L'utente può interagire con gli oggetti di una stanza
  - spostando gli oggetti di tipo scatola
  - collezionando chiavi, potenziamenti e tesori
- L'utente può visualizzare
  - I potenziamenti ottenuti
  - Le chiavi in suo possesso
  - Il suo punteggio attuale
  - Lo stato attuale della stanza

### Requisiti di sistema

- Una mappa è composta da stanze
- Possibilità di caricare la mappa da file
- Ogni stanza ha la stessa dimensione (25x13)
- Ogni stanza è composta da celle
- Ogni cella ha un interazione specifica
  - basic (base) 
    - ci si può entrare e uscire in ogni direzione
  - wall (muro) 
    - non ci si può entrare od uscire
  - hole (buca) 
    - ci si può entrare
    - comporta morte del player
    - se riempita con una scatola assume un comportamento analogo ad una cella basic 
  - covered hole (buca con copertura)
    - la prima volta ci si può entare e uscire liberamente grazie alla copertura
    - dopo la prima volta la copertura è rotta e la cella assume un comportamento analogo ad una cella hole
    - se la prima volta si inserisce una scatola la copertura si rompe e la scatola riempie la buca
  - cliff (burrone)
    - ci si può entrare in ogni direzione eccetto quella proibita
  - button (bottone)
    - si può premere passandoci sopra o tramite scatola
    - dotato di colore
  - button block (blocco apribile con bottone)
    - blocco dal comportamento analogo ad una cella wall
    - dotato di colore
    - se il bottone del colore corrispondente viene premuto una volta, assume il comportamento di una cella basic
  - pressure plate (pedana a pressione)
    - si può premere mettendoci sopra una scatola
    - se la scatola è rimossa, torna a essere non premuto
  - pressure plate block (blocco apribile con pedana a pressione)
    - ha un comportamento analogo a una cella muro o basic
    - il suo comportamento è invertibile tramite pressure plate
  - teleport (teletrasporto)
    - si può entrare in ogni direzione 
    - si viene trasportati nella teleport destination
  - teleport destination (destinazione del teletrasporto)
    - si può uscire in ogni direzione
    - ha un comportamento analogo a basic cell
  - rock (roccia)
    -   la roccia è attraversabile solo se viene rotta
    -   la roccia può essere sgretolata grazie al power-up piccone
    -   è possibile posizionare una cassa sulla roccia sgretolata ma non intera
  - plant (pianta)
    -   la pianta è attraversabile solo se viene tagliata
    -   la pianta può essere tagliata grazie al power-up ascia
    -   è possibile posizionare una cassa solo se la pianta è tagliata
  - door (porta)
    -   la porta è attraversabile solo se viene aperta
    -   l'unico modo per aprire una porta è usare una chiave
    -   il player non può richiudere la porta
  - tresure (tesoro)
    - il player è in grado di aprire un tesoro posizionandosi sopra di esso  
    - un tesoro acquisito aumenta il punteggio del giocatore
    - ci sono tre tipi di tesori:
        -   coin (moneta) => 10 punti
        -   bag (sacco) => 20 punti
        -   trunk (baule) => 50 punti
    - un sacco o un baule possono contenere degli item e il player li acquisirà al momento dell'apertura del tesoro
- Il gioco termina quando si completa la stanza conclusiva
- una cella è considerata libera se
  - non è una cella di tipo wall (o con un comportamento analogo)
  - non è occupata da una scatola
- Quando un giocatore sposta una scatola
  - se la cella nella direzione dello spostamento è libera il giocatore resta fermo e la scatola si sposta
  - altrimenti entrambi restano fermi


## Requisiti non funzionali

- Usabilità: il sistema dev'essere intuitivo e facile da usare, l'utente deve poter comprendere le azioni eseguibili e compierle senza difficoltà
- Prestazioni: il sistema deve essere responsivo 
- Affidabilità: il sistema deve evitare crash ed essere stabile e affidabile 
- Documentazione e supporto: il sistema deve essere ben documentato per permettere una facile modifica ed un facile riutilizzo futuri

## Tecnologie e metodi del processo di sviluppo
- Scrum
- Git
- GitHub
- sbt
- Trello



## Requisiti opzionali




[Torna all'indice](../report.md) | [Vai a Design architetturale](../04-architectural-design/report.md)
