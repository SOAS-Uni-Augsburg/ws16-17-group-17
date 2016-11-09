# Selbst-organisierende, adaptive Systeme

## Übungsblatt 3

### Aufgabe 3
a) Dass der Staubsaugroboter rational handelt heißt, dass er unter Berücksichtigung
all seines eingebauten und erworbenen Wissens über die Umwelt für alle
Wahrnehmungssequenzen diejenige Aktion wählt, die den erwarteten Wert des
Performancemaßes maximiert.

Der Staubsaugroboter speichert keine Information über seine Umwelt, d.h. er hat
kein Gedächtnis und lernt demnach auch nichts. Es bedeutet auch, dass er nur
seine aktuelle Umgebung wahrnimmt und seine Entscheidungen nur darauf basiert.

Des weiteren hat die Umgebung die Eigenschaft, dass eine Kachel, sobald sie
einmal sauber ist, nicht mehr dreckig wird.

Da man um eine Kachel zu säubern, einen Zeitschritt benötigt und es 2 Kacheln
gibt, ist das Performancemaß im Falle, dass alle Kacheln zu Beginn dreckig
sind, 196. Es ist

    P = 2•100 - Σ|dreckigeKachel_i|.

Im ersten Schritt sind alle
Kacheln dreckig. Um eine weiter Kachel zu säubern benötigt der Roboter
mindestens 2 Schritte, 1. Kachel wechseln, 2. Säubern.

    ⇒ P = 200 - 2 - 2•1 - 2•0 = 196.

Sind weniger Kacheln dreckig, so ist es höher.

Angenommen der Staubsaugroboter würde nicht rational handeln, so müsste eine
Kachel für 4 Zeitschritte dreckig sein... Ahh screw it.

Sauber = O, dreckig = #, Roboter Position = _.
Aus Symmetriegründen reicht es nur die Fälle zu betrachten in denen der Roboter
auf dem linken Feld startet.

    _   _ _   _
    OO→OO→OO→00→... ⇒ 200
    _   _  _ _
    O#→O#→OO→00→... ⇒ 198
    _  _   _ _ 
    #O→OO→OO→00→... ⇒ 199
    _  _   _  _
    ##→O#→O#→00→... ⇒ 196

b) `P' = P - Anzahl der Bewegungen` wäre ein Performance Maß, das unnötige
Bewegungen bestraft. Sei ο eine Wahrnemungsfolge und ο(t) = {loc, state} die
letzte Wahrnehmung. Dann ist die folgende Agentenfunktion rational bezüglich
des Performancemaßes `P'`:

      ο(t-2)      ο(t)       f
    {A, dirty} {A, dirty}  suck # This should not be possible!
    {A, dirty} {A, clean}  stay
    {A, clean} {A, dirty}  Table flip!
    {A, clean} {A, clean}  stay
    {A, dirty} {B, dirty}  suck
    {A, dirty} {B, clean}  stay # This should not be possible!
    {A, clean} {B, dirty}  suck # This should not be possible!
    {A, clean} {B, clean}  stay
    {B, dirty} {A, dirty}  suck
    {B, dirty} {A, clean}  stay
    {B, clean} {A, dirty}  suck # This should not be possbile!
    {B, clean} {A, clean}  stay
    {B, dirty} {B, dirty}  suck # This should not be possible!
    {B, dirty} {B, clean}  stay
    {B, clean} {B, dirty}  Table flip!
    {B, clean} {B, clean}  stay

Der Simple Agent ist nun nicht mehr rational, weil er zwischen den Kacheln hin
und her springt, wenn sie sauber sind. Das wird von dem neuen Performancemaß
bestraft.

c) Bieten auf einen Gegenstand bei einer Auktion
|         Performance        |    Environment    | Actuators | Sensors |
|----------------------------|-------------------|-----------|---------|
| Erhalten des Gegenstandes  | Andere Teilnehmer | Hände,    | Augen,  |
| Kaufpreis des Gegenstandes | Auktionsleiter    | Stimme    | Ohren   |
|                            | Gegenstand        |           |         |

Suche nach Wrackteilen mit autonomem Unterwasser-Fahrzeugen
|         Performance        |    Environment    | Actuators | Sensors      |
|----------------------------|-------------------|-----------|--------------|
| Gefundene Wrackteile       | Andere Teilnehmer | Propeller | Kompass      |
| Verbrauchte Energie        | Gesteine          | Steuer    | Tiefensensor |
|                            | Meer              | Motor     | Sonar        |
|                            | andere Schiffe    |           | Radar        |
|                            | Tiere             |           |              |
|                            | Mensch            |           |              |

Intelligenter Stromverbrauch: Zu Zeiten hoher Strompreise sollen geplante,
enerieintensive Vorgänge verschoben werden.
|           Performance          |           Environment        |      Actuators     | Sensors      |
|--------------------------------|------------------------------|--------------------|--------------|
| Gesamtkosten über den Zeitraum | Eine Firma:                  | Mitarbeiter        | Server       |
| Anzahl verschobener Vorgänge   | Geräte die Energie benötigen | Energieverbraucher | Mitarbeiter  |
|                                | Arbeitszeiten Mitarbeiter    |                    |              |
|                                | Deadlines                    |                    |              |
|                                | Kunden (Abhängigkeiten)      |                    |              |
|                                | Ggf. Stell- und Lagerplätze  |                    |              |
|                                | Resourcenangebot             |                    |              |

d) Siehe Staubsauger-Zustandsmaschine.pdf
Nicht-determinismus kann für ein zufälliges Ablaufen der Felder genutzt werden.
Dies würde dann zu einem *random walk* ähnlichen Verhalten führen und vielleicht
eine erwartete optimale Effizienz liefern.

Da die Wahrnehmung in dieser Antwort nur der Zustand der Kachel ist, auf der
sich der Staubsauger gerade befindet kann sein im Zustandsdiagramm
vorgeschlagenes Verhalten nicht durch eine Agentenfunktion auf der Menge der
Wahrnehmungsfolgen dargestellt werden.

Dazu müssten die Wahrnehmungen auch noch den Ort des Staubsaugers enthalten.
Dann könnte ein Teil der Agentenfunktion so aussehen:

|  Wahrnemungsfolge   | Aktion |
|---------------------|--------|
| (*, {(0,0), Clean}) |   Up   |
| (*, {(0,1), Dirty}) |  Suck  |
| (*, {(0,1), Clean}) |   Up   |
| (*, {(0,2), Clean}) |  Right |
