# Implementazione
## Schermata di gioco
## Suddivisione del lavoro
## Sofia Tosi
Riporto di seguito un elenco di file raggruppati per package a cui ho lavorato totalmente, sono esclusi i file scritti in cooperazione con gli altri membri del team.
- controller
  - menu
    - ``` MenuController ```
- model
  - cell
    - logic
      - ``` ItemHolderExtension ```
      - ``` TreasureExtension ```
      - ``` UseItemExtension ```
    - traits
      - ``` Lock ```
      - ``` Plant ```
      - ``` Rock ```
    - ``` LockCell ```
    - ``` PlantCell ```
    - ``` RockCell ```
  - game
    - ``` ItemHolder ```
- utils
  - givens
    - ``` ItemConversion ```
  - extensions
    - paths
      - ``` WallPathExtractor ```
- view
  - menu
    - ``` ControlsView ```
    - ``` CustomCellRenderer ```
    - ``` CustomScrollBarUI ```
    - ``` ForegroundElements ```
    - ``` MenuView ```
    - ``` SelectMapExtension ```
    - ``` TransparentButton ```
      
Ripoterò di seguito una descrizione più approfondita degli aspetti implementativi più importanti, non trattati nei capitoli precedenti.

### RockCell e PlantCell
``` RockCell ``` e ``` PlantCell ``` rappresentano due celle della mappa di gioco, la prima con una roccia e la seconda con un albero che impediscono al player di attraversarle. Grazie a specifici power-up ottenuti esplorando la stanza, il player è in grado di frantumare la roccia e abbattere l'albero in modo tale da poter attraversare le celle.
Gli elementi roccia e albero sono modellati con un mixin. Grazie a quest'ultimo ``` RockCell ``` e ``` PlantCell ``` possono estendere la classe atratta ``` Cell ``` e al tempo stesso ereditare i membri dei traìt rock e plant (quali, ad esempio, broken/cut che restituiscono un booleano che permette di indicare se la roccia e la pianta sono rotte/tagliate) in modo molto flessibile. Infatti, un mixin rende il codice estremamente riutilizzabile, evitando la fragilità dell’ereditarietà utilizzata in ambiti di pura programmazione ad oggetti dove una modifica alla classe base può introdurre bug all’intera gerarchia.

### LockCell
In modo analogo a ``` RockCell ``` e ``` PlantCell ``` anche ``` LockCell ``` eredita campi e metodi dal corrispettivo Mixin.
I tre tipi di celle citate concretizzano il mixin attraverso una case class che garantisce immutabilità e permette di evitare side effects indesiderati. Inoltre, otteniamo getter, setter, toString, equals e hashCode dei parametri specificati. È stato molto utilizzato anche il metodo copy che permette di ottenere una nuova istanza della classe con alcuni campi modificati.

### ItemHolder
Una parte di rilievo è il metodo removeItem nella classe ItemHolder. Difatti in esso viene utilizzato un for comprehension per iterare sugli elementi della lista itemOwned e sui relativi indici. Lo yield permette di ottenere una nuova lista i cui elementi sono diversi da quello in input oppure sono uguali ma non sono la prima occorrenza.  Grazie al for comprehension operazioni complesse possono essere espresse in modo più leggibile e dichiarativo. Viene inoltre usato il metodo copy per creare una nuova istanza di ``` ItemHolder ```, essendo esso immutabile, e ciò risulta possibile in quanto è implementato tramite una case class.

### UseItemExtension, TreasureExtension, ItemHolderExtension
``` UseItemExtension ```, ``` TreasureExtension ```, ``` ItemHolderExtension ``` sono tre file che aggiungono metodi aggiuntivi alla classe base. I metodi sono incapsulati nell’omonimo oggetto, il quale viene importato al momento dell’uso. Grazie a questi extension methods il codice della classe base rimane inalterato e può usufruire delle feature aggiuntive. Questo pattern promuove il principio DRY in quanto permette di astrarre i concetti comuni e di riutilizzare il codice senza doverlo ripetere.
In particolare, nell’estensione ``` ItemHolder ``` vengono ridefiniti dei metodi come DSL. Grazie infatti all’utilizzo di alias, è possibile far sì che il metodo risulti più leggibile ed espressivo al momento della chiamata. Il nome del metodo è inoltre più significativo e coinciso. Riporto un esempio:
```scala
    /** Alias for [[ItemHolder.addItems()]] */
    @targetName("addItemsAlias")
    def ++(items: List[Item]): ItemHolder = itemHolder.addItems(items)

    /** Alias for [[ItemHolder.addItem()]] */
    @targetName("addItemAlias")
    def +(items: Item): ItemHolder = itemHolder.addItem(items)

    /** Alias for [[ItemHolder.removeItem()]] */
    @targetName("removeItemAlias")
    def -(item: Item): ItemHolder = itemHolder.removeItem(item)
```
Di seguito si riporta un test:
```scala
"an item holder" should "be able to show its items" in {
    (itemHolder
      + Item.Axe
      ++ List(Item.Key, Item.Pick)
      - Item.Key).itemHolderToString() should be(
      "[ AXE | PICK ]"
    )
  }
````
Si noti come risulti più espressivo e sia subito chiaro l’intento: ++ per aggiungere una lista di elementi, + per aggiungerne uno e – per la rimozione.

### ItemConversion
In questo file sono implementate due conversioni riguardanti gli item. La prima ha come output un int che rappresenta il valore in termini di punti dell’item mentre l’altra ha una stringa che rappresenta l’item in una rappresentazione testuale. Grazie al meccanismo given la conversione tra item e l’output specificato avviene in modo automatico.

### MenuController
Il metodo searchMapFile permette di ottenere una listmap immutabile contenente come chiave i nomi delle mappe e come valore il nome dei file. È implementato con un for comprehension per iterare sulla lista delle mappe. L’utilizzo di questo costrutto permette al codice di essere più leggibile e dichiarativo. 

### GameView, ForegroungElements
La creazione della GUI del gioco è realizzata tramite un for comprehension che ciclando sulle righe e sulle colonne permette di costruire una listMap immutabile avente come chiave la posizione della cella e come valore viene creato il tile corrispondente. Nelle prime due righe della classe vengono richiamati due metodi dell’oggetto ``` ToolbarElements ```. Essendo metodi utilizzati solo nel package game le due label hanno come modificatore d’accesso private[game] che restringe la visibilità dei metodi: sono visibili solo all’interno del package. Anche il file ``` ForegroundElements ``` ha dei metodi richiamati solo in ``` SelectMapExtension ```. Per questo motivo il modificatore di accesso è private[menu], per ridurre la visibilità dei metodi al solo package menu. Gli ultimi tre metodi in ``` ForegroundElements ``` vengono utilizzati nel file stesso e per questo motivo l’accesso è ristretto ulteriormente: sono private in modo tale che siano visibili solo all’oggetto in cui sono stati definiti.

### WallPathExtractor
Mi sono occupata della realizzazione di extractWallPath, metodo che si occupa di determinare se una specifica cella è un muro e in questa casistica che tipologia di muro è. Restituisce la stringa che specifica la tipologia del muro da aggiungere al path. Originariamente questo codice è stato scritto in Prolog. Tramite delle clausole filter si filtravano le celle che erano un muro (determinato dall’ultima proprietà del termine composto cella ``` c(_, _, _, wall) ``` ). Ottenute le celle muro adiacenti a quella da analizzare si controllava, con la seconda parte della teoria, a quale delle quattordici tipologie di muro appartenesse. In particolare si controllava quanti dei quattro angoli adiacenti (TopLeft, TopRight, BottomLeft, BottomRIght) fossero costituiti da tutte celle muro.
Tuttavia, non è stato possibile lasciare Prolog in questa parte, nonostante sia utile per verificare le quattordici regole sui muri tramite una logica “theorem prover”.
Il ``` PrologEngine ``` si è rivelato estremamente poco performante in questo approccio. Infatti, questa teoria veniva risolta per ogni cella della matrice 25x13, per un totale di 325 celle. Il tempo di caricamento della mappa calcolato tramite cronometro è risultato 15.82 secondi.
Il gioco è pensato per renderizzare le celle della mappa ogni volta che riceve un input. Quindi ad ogni mossa dell’utente il player prima di muoversi aspettava più di 15 secondi. Il gioco è risultato ingiocabile. 
Una possibile soluzione trovata è stata quella di caricare le celle di tutte le mappe una sola volta all’inizio del gioco per avere un delay solamente a inizio partita, evitando un delay nel mezzo del gioco grazie a una schermata iniziale di loading. Tuttavia, nemmeno questa soluzione è risultata accettabile. Infatti caricando tutte le celle all’inizio il tempo del loading risultava 15.82 secondi * 9 stanze / 60 circa due minuti e 40. Si è ritenuto che il tempo calcolato fosse inaccettabile anche per una schermata di caricamento.
Come soluzione definitiva si è presa la decisione di trascrivere il codice Prolog in Scala che è in grado di ottenere il risultato desiderato in modo molto più performante delle precedenti casistiche. In particolare in questo codice è presente un for comprehension che  permette di ottenere una mappa immutabile avente come chiave le celle adiacenti e come valore un booleano che indica se sono ``` WallCell ``` oppure no in un modo dichiarativo e coinciso.

## Serafino Pandolfini
Nel corso dello sviluppo di questo progetto mi sono occupato dei seguenti aspetti:
### Cell, BasicCell, WallCell
`Cell` rappresenta l'elemento base di cui è composta una `Room`.
La classe è implementata come abstract class offrendo un implementazione comune per i metodi ```walkableState```, che
ritorna _Walkable(true)_, e `isDeadly`, che ritorna _false_,  e al contempo descrive le proprietà comuni delle celle
quali `position` e `cellItem`. <br>
Al suo interno è definito l'**alias type** `Position` che descrive una tupla _(int, int)_, utilizzato per definire la
posizione di ogni cella nella loro stanza. <br>
Infine `Cell` è provvista di un **companion object** che fornisce tramite **given** un ordering per le celle basato 
sulla loro posizione per righe.

`BasicCell` e `WallCell` sono **case class** che estendono `Cell` e rappresentano i due tipi più comuni di
celle: la prima riproduce il comportamento di Cell senza modifiche mentre la seconda fornisce nuove implementazioni per
i metodi cellItem e walkableState ereditati da Cell.

### HoleCell, CoveredHoleCell
`HoleCell` e `CoveredHoleCell` sono altre tipologie di celle, a differenza di BasicCell e WallCell viene fatto
uso di **Mixin**: viene usato il mixin `Hole` per HoleCell e CoveredHoleCell e `Covered` per CoveredHoleCell. 
I mixin utilizzati permettono di aggiungere funzionalità specifiche senza creare una gerarchia complicata di classi. <br>
Viene in seguito riportato un esempio del mixin Hole:

```scala
/** The mixin representing an obstacle that is walkable and deadly, but can be filled with a box item */
trait Hole extends Cell:
  /** if the hole is filled */
  def filled: Boolean

  abstract override def isDeadly: Boolean = !filled || super.isDeadly
```



### TeleportCell, TeleportDestinationCell

### PrologEngine

### Room, RoomLink, RoomBuilder

### RoomRules

### Minimap

### PathExtractor


### Laura Leonardi
[Torna all'indice](../report.md) | [Vai a Conclusioni](../07-conclusion/report.md)
