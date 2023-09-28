# Design di dettaglio
scelte importanti, pattern di programmazione, organizzazione del codice

## Tecnologie utilizzate

- **TuProlog:** framework per la programmazione logica, utilizzato al fine di implementare e utilizzare regole in Prolog 
- **Swing:** framework utilizzato per lo sviluppo dell'interfaccia grafica.
- **Circe:** libreria utilizzata per la serializzazione e deserializzazione dei file in formato Json.

## Diagramma dei package

## Diagramma delle classi

## Model

### Cell
```Cell``` rappresenta una singola cella caratterizzata da una posizione che definisce le sue coordinate; da un ```Item```, un elemento con cui il giocatore può interagire; il suo stato di attraversabilità e se è mortale per il giocatore. Su questa base sono costruite tutte le varie tipologie di celle, ognuna con delle specifiche caratteristiche ottenute tramite il meccanismo di class composition. Tramite questo meccanismo è possibile modellare diversi elementi utilizzando la stessa base. Con questo approccio ogni singolo comportamento è sia isolato che riutilizzabile, ad esempio ```Hole```, che definisce il comportamento di una cella con un buco, viene utilizzato per comporre sia ```HoleCell``` che ```CoveredHoleCell```.
### Room
```Room``` rappresenta il concetto di stanza costituito da un nome, un insieme di celle e dai ```RoomLink``` che definiscono i collegamenti tra stanze. 
La costruzione di ```Room``` prevede differenti tipologie di parametri dalla costruzione dei confini della stanza al riempimento delle celle mancanti. Per agevolare la creazione delle stanze si è fatto uso del pattern Builder. Il pattern promuove il principio Single Responsibility Principle permettendo di incapsulare la logica di costruzione rendendola più flessibile e mantenibile. Tramite il builder è possibile impostare la validazione della stanza sulla base di specifiche regole, costruite anch'esse tramite il meccanismo di class composition. Queste regole permetteono di evitare complicati errori logici nella costruzione della stanza. Un esempio di regola è ```BorderCellsRule``` che definisce quali tipologie di celle possono essere sui bordi della stanza.
<p align="center">
  <img src="../Images/roomBuilder.png" alt="Pattern Builder"/>
</p>

### GameMap
```GameMap``` rappresenta il concetto di mappa di gioco, è costituita da un insieme di stanze connesse. Una sua rappresentazione è tramite una lista di  ```MinimapElement```, utilizzati per la costruzione della minimappa del menù di pausa e in relazione 1 a 1 con le stanze della mappa che rappresentano. Questi elementi vengono generati a partire dal metodo  ```CreateMinimap``` che viene aggiunto a ```GameMap``` tramite il pattern Pimp my library.

### Game

## View

### MenùView

### GameView

## Controller

### MenùController

### GameController

[Torna all'indice](../report.md) | [Vai a Implementazione](../06-implementation/report.md)
