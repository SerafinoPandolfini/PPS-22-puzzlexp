# Analisi del sistema e requisiti

## Requisiti di business
- creare un sistema che permetta di effettuare una partita a un gioco ispirato a "Sokoban"
- il sistema dovrà disporre di un menù da cui il giocatore potrà iniziare una partita

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
- L'utente può visualizzare nel menù iniziale la schermata dei controlli del gioco oppure avviare una partita
- L'utente può muovere il player tramite i tasti "W" "A" "S" "D"
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
- L'utente può resettare la stanza 
  - premendo il tasto "R"
  - cambiando stanza
  - morendo
 
  
### Requisiti di sistema

- Una mappa è composta da stanze
- Possibilità di caricare la mappa da file
- Ogni stanza ha la stessa dimensione (25x13)
- Ogni stanza è composta da celle
- Ogni cella ha un interazione specifica
  - basic (base) 
    - ci si può entrare e uscire in ogni direzione
  - wall (muro) 
    - non ci si può entrare o uscire
  - hole (buca) 
    - ci si può entrare
    - comporta morte del player
    - se riempita con una scatola assume un comportamento analogo a una cella basic 
  - covered hole (buca con copertura)
    - la prima volta ci si può entrare e uscire liberamente grazie alla copertura
    - dopo la prima volta la copertura è rotta e la cella assume un comportamento analogo ad una cella hole
    - se la prima volta si inserisce una scatola la copertura si rompe e la scatola riempie la buca
  - cliff (burrone)
    - ci si può entrare in ogni direzione eccetto quella proibita
  - button (bottone)
    - si può premere passandoci sopra o tramite scatola
    - dotato di colore
  - button block (blocco apribile con bottone)
    - blocco dal comportamento analogo a una cella wall
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
    - vi si può inserire una scatola che viene trasportata in una cella adiacente a teleport destination in base alla direzione da cui è stata inserita
  - teleport destination (destinazione del teletrasporto)
    - si può uscire in ogni direzione
    - ha un comportamento analogo a basic cell
  - rock (roccia)
    -   ha un comportamento analogo a una cella muro
    -   può essere sgretolata grazie al power-up piccone
    -   una volta sgretolata assume un comportamento analogo a una cella basic
  - plant (pianta)
    -   ha un comportamento analogo a una cella muro
    -   può essere tagliata grazie al power-up ascia
    -   una volta tagliata assume un comportamento analogo a una cella basic
  - door (porta)
    -   ha un comportamento analogo a una cella muro
    -   può essere aperta grazie a una chiave
    -   una volta aperta assume un comportamento analogo a una cella basic
- una cella è considerata libera se
  - non è una cella di tipo wall (o con un comportamento analogo)
- Esistono degli item (elementi) posizionati su celle libere che possono essere:
  - box (scatole)
    - possono essere spostate da un giocatore
      - se la cella nella direzione dello spostamento è libera e non occupata da un altra scatola il giocatore resta fermo e la scatola si sposta
      - altrimenti entrambi restano fermi
    - non possono essere trasportate tra le stanze
  - key (chiavi)
    - possono essere raccolte camminandoci sopra
    - si utilizzano per attraversare le porte
    - un utilizzo consuma la chiave
  - treasure (tesoro)
    - il player è in grado di ottenere un tesoro posizionandosi sopra di esso
    - un tesoro acquisito aumenta il punteggio del giocatore
    - ci sono tre tipi di tesori:
      - coin (moneta) => 10 punti
      - bag (sacco) => 20 punti
      - trunk (baule) => 50 punti
  - goalGem (gemma obiettivo)
    - nel momento in cui questo oggetto viene ottenuto il gioco visualizza la schermata finale
  - power-up
    - axe (ascia)
      - possono essere raccolte camminandoci sopra
      - si utilizzano per tagliare gli alberi
      - il numero di utilizzi è infinito
    - pickaxe (piccone)
      - possono essere raccolte camminandoci sopra
      - si utilizzano per sgretolare le rocce
      - il numero di utilizzi è infinito
- Il gioco termina quando si ottiene la goalGem 
  - quando il gioco termina viene visualizzata una schermata conclusiva dove viene mostrata la percentuale di
  completamento della mappa espressa in punti ottenuti su punti totali
- Le stanze sono costruite in modo da rispettare regole di game design
  - Il bordo della stanza deve essere costituito da muri o link
  - Il numero di celle è fissato e pari a 325 e le loro posizioni devono essere all'interno dei limiti della stanza (25x13) 
  - Ci può essere al più un elemento teleport in una stanza e deve essere presente anche una e una sola sua controparte(teleport destination)
  - Ci può essere al più un elemento pressure plate in una stanza e deve essere presente anche almeno una sua controparte(pressure plate block)
  - Ci può essere al più un elemento button per colore in una stanza e deve essere presente anche almeno una sua controparte(button block) del medesimo colore
- Le mappe devono poter essere codificate/decodificate in JSON
  
## Requisiti non funzionali

- Usabilità: il sistema dev'essere intuitivo e facile da usare, l'utente deve poter comprendere le azioni eseguibili e compierle senza difficoltà
- Prestazioni: il sistema deve essere responsivo 
- Affidabilità: il sistema deve evitare crash ed essere stabile e affidabile 
- Documentazione e supporto: il sistema deve essere ben documentato per permettere una facile modifica ed un facile riutilizzo futuri

 
## Requisiti di implementazione
- Scala 3.x
- toProlog 4.x
- JDK 11+

## Requisiti opzionali

- presenza di una schermata di pausa durante il gioco dove:
  - visualizzare le stanze della mappa e la posizione del giocatore
  - ritornare alla partita in corso 
- possibilità di poter scegliere tra più mappe di gioco
  - schermata aggiunta dopo il menù iniziale contenente le mappe giocabili
- possibilità di salvare e riprendere la partita corrente
  - tasto salva nella schermata di pausa
  - possibilità di scegliere continua dalla schermata contenente le mappe giocabili se sono presenti dati di salvataggio
  per la mappa selezionata



[Torna all'indice](../report.md) | [Vai a Design architetturale](../04-architectural-design/report.md)
