# Design Architetturale

Data la tipologia di progetto scelta, la sua relativa semplicità a livello architetturale e dalle conoscenze/preferenze dei membri del gruppo, si è scelto di utilizzare il pattern MVC(Model-View-Controller).
Questo patterm permette di avere un'ottima suddivisone delle responsabilità, semplificando la suddivisione del lavoro all'interno del gruppo; rende più semplice uno sviluppo incrementale e iterativo, in quanto permette di aggiungere nuove caratteristiche senza stravolgere la struttura del codice precedente.    

<p align="center">
  <img src="../images/MVC.png" alt="Struttura pattern MVC"/>
</p>

Model:

- Il Model rappresenta la parte dei dati e della logica dell'applicazione.
- Gestisce il recupero, la manipolazione e l'archiviazione dei dati.
- Risponde a richieste provenienti dal Controller e gli notifica eventuali cambiamenti.
- È indipendente dall'interfaccia utente e contiene la logica di business dell'applicazione.

View:

- La View rappresenta l'interfaccia utente dell'applicazione.
- È responsabile di visualizzare i dati forniti in un formato comprensibile per l'utente.
- Non contiene logica di business, ma può reagire agli eventi utente e inoltrarli al Controller per ulteriori elaborazioni.

Controller:

- Il Controller funge da intermediario tra il Model e la View.
- Riceve le richieste dell'utente dalla View e le indirizza al Model appropriato.
- Gestisce gli input utente, esegue operazioni di logica di controllo e aggiorna il Model se necessario.
- Aggiorna la View per riflettere le modifiche apportate ai dati nel Model.
- È responsabile della gestione del flusso di controllo dell'applicazione.

[Torna all'indice](../report.md) | [Vai a Design di dettaglio](../05-detailed-design/report.md)
