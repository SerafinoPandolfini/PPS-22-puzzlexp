# Metodologia di sviluppo
## Scrum
Abbiamo utilizzato la metodologia di sviluppo _Agile_, nello specifico è stata adottata la variante _Scrum_.
Il processo è dunque iterativo, ovvero suddiviso in _sprint_, e incrementale, infatti durante ogni sprint vengono aggiunte funzionalità o raffinate le funzionalità esistenti.
### Product backlog
Il _product backlog_ è uno strumento tipico di _Scrum_, si tratta di una tabella di sviluppo del prodotto, infatti contiene una lista delle feature relative all'utente, ordinata per priorità.
Per ogni feature è presente, oltre alla priorità, la stima della dimensione, rappresentata in punti che vengono "consumati" durante i vari sprint.
Il _product backlog_ è aggiornato durante il processo di sviluppo, nello specifico durante ogni sprint planning.
### Sprint
Uno _sprint_ è una singola iterazione del processo di sviluppo. All'inizio di ogni sprint viene fatto lo _sprint planning_ durante il quale viene definito lo _sprint backlog_.
Le attività che segnano la fine di uno sprint e l'inizio del successivo sono:
- _sprint review_: analisi del lavoro svolto durante lo sprint precedente
- _sprint retrospective_: valutazione del processo di sviluppo ed eventuali proposte di miglioramento 
- _refinement del product backlog_: eventuali migliorie o correzioni da apportare al product backlog
- _sprint planning_: produzione di uno _sprint backlog_ e selezione delle feature che lo comporranno, bilanciando il carico di lavoro tra sprint
Durante uno sprint vengono svolti dei meeting periodici per aggiornarsi sul progresso dello sviluppo del progetto.
Ogni sprint è stato strutturato per durare indicativamente due settimane, in modo da garantire ai membri del gruppo flessibilità lavorativa, stimando 14-19 ore di lavoro a testa.
#### Sprint backlog
Uno _sprint backlog_ è una tabella in cui vengono registrate le feature, e i task che le compongono, che devono essere sviluppati durante lo _sprint_.
Nello _sprint backlog_ vengono inoltre specificati i membri del gruppo che si occupano delle varie parti in modo che il carico di lavoro risulti bilanciato.

#### Periodic Meeting
Durante ogni _sprint_ sono stati periodicamente effettuati dei meeting tra tutti i membri del gruppo, generalmente con 
cadenza ogni 3-4 giorni, in cui ogni membro del gruppo esponeva lo stato di avanzamento dei task a lui assegnati e
venivano effettuate eventuali correzioni allo _sprint backlog_. La durata di questi incontri era basata sul numero di 
giorni trascorsi dal precedente, generalmente tra i 45-60 minuti.

## Git Flow
Per la struttura del lavoro su GitHub, si è deciso di utilizzare come flusso di lavoro Git Flow, costituito dai seguenti
branch:
- Master rappresenta la versione di produzione del software (release).
- develop: Utilizzato per sviluppare nuove funzionalità. È il punto di partenza per la creazione di nuovi
branch di feature.
- feature/(_feature_name_): Ogni nuova funzionalità in fase di sviluppo viene creata in un branch separato chiamato "feature branch".
Una volta che la funzionalità è completa e testata, il branch di feature viene fuso nuovamente nel branch di sviluppo (develop).
- release/(_version_): Ogni volta che viene effettuata una release del progetto viene creato un branch a partire da develop,
vengono verificati eventuali bug e problemi di prestazioni e, infine, viene poi fuso nel Master branch.
- hotflix/(_version_): utilizzati per risolvere eventuali bug riscontrati nella produzione.

## Build Automation
Come strumento di build automation si è scelto di utilizzare sbt (Scala Build Tool). Questa scelta è stata utilizzata in
quanto sbt nasce orientato a scala e utilizza la sintassi di scala. 

## Testing
Per la metodologia di testing si è scelto di utilizzare un approccio prevalentemente basato sul Test Driven Development,
seguendo il ciclo Red-Green-Refactor visto a lezione. <br>
Per la scrittura dei test si è utilizzato ScalaTest seguendo lo stile FlatSpec. <br>
Inoltre si è utilizzato JaCoCo come plugin per la verifica della coverage, fissata al 70% delle linee di codice e classi.

## Continuous Integration
Per limitare quanto possibile l'emergere di problemi durante lo sviluppo del progetto, in particolare in situazioni di 
merge su develop, si è scelto di utilizzare GitHub Actions come servizio. <br>
Tramite le Actions è possibile verificare la corretta esecuzione di tutti i test ogni volta che la code base viene 
aggiornata su diversi sistemi operativi (Windows, Linux, Mac), e di garantire che la coverage minima imposta sia 
rispettata.  

## Assegnazione dei ruoli
- **Pandolfini Serafino:** sviluppatore, esperto di dominio; tale ruolo ha la funzione di garantire qualità e usabilità del risultato, definendo i requisiti del prodotto
- **Tosi Sofia:** sviluppatore, product owner; tale ruolo ha la responsabilità di identificare feature del prodotto e creare e mantienere product backlog e sprint backlog
- **Leonardi Laura:** sviluppatore


[Torna all'indice](../index.md) | [Vai ad Analisi del sistema e requisiti](../03-requirements/report.md)
