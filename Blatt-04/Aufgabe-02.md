# Selbst-organisierende, adaptive Systeme

## Übungsblatt 4

### Aufgabe 2

a)
Eine reine Strategie hat eine Aktion.
Eine strikt dominante Strategie ist es, eine stark dominante Aktion zu wählen.
Eine starke dominante Aktion für Spieler 1 ist c,
da für jede Aktion von Spieler 2 c den maximalen Nutzen seiner Aktionsmenge bietet.
Spieler 2 hat lediglich eine schwach dominante Aktion y.

b)
y

c)
<c, y>  mit (4,4); weitere prüfen

d)
Das Spiel hat keine dominante Strategie.

Nash-Gleichgewicht: <D, R> mit (5,6)
Methode:
Für Spieler 1 maxima der Spalten markieren (nur 1. Komponente des Tupels betrachten)
Für Spieler 2 maxima der Zeilen markieren (nur 2. Komponente des Tupels betrachten)

Zellen, wo beide Zahlen markiert sind, sind im Nash-Gleichgewicht

e)
Angenommen die Nutzenfunktion beider Agenten ist die *eigene
Gewinnmaximierung*, dann ist

- (0.3, 0.7) ein Nash-Gleichgewicht, da 0.3 + 0.7 = 1 und eine niedrigere Wahl
             des Anteils eine Verschlechterung zur Folge hätte. Eine Erhöhung
             würde die Summe über 1 wachsen lassen sodass der Spieler 0 Gewinn
             machen würde; eine Verschlechterung bzgl. der Nutzenfunktion.
- (0.5, 0.5) Auch ein Nach-Gleichgewicht mit dem gleichen Argument wie bei dem
             vorigen Punkt.
- (1.0, 1.0) Dies ist auch ein Nash-Gleichgewicht, da der Nutzen beider Spieler
             0 ist und keine einseitige Änderung im zulässigen Auswahlbereich
             a∈[0.0, 1.0] den Nutzen des Spielers größer 0 machen würde.

Angenommen die Nutzenfunktion beider Agenten ist die *Gewinnminimierung seines
"Gegners"*, dann ist

- (0.3, 0.7) ist kein Nash-Gleichgewicht, da jeder Spieler seinen Anteil erhöhen
             kann um dem Gegnerischen Spieler einen Gewinn von 0 zu bescheren.
- (0.5, 0.5) ist auch kein Nash-Gleichgewicht, mit selbem Argument wie zuvor.
- (1.0, 1.0) ist ein Nash-Gleichgewicht, da jede andere Wahl des Anteils den
             Gewinn des Gegners bei 0 lassen oder auf 1 heben würde.

f)
Wir gehen von der on-demand Produktion aus.

(0,0)
Falls der Preis von beiden Firmen 0 ist, haben beide Firmen den Profit -Dc/2.
Falls 1. Firma den Preis höher macht, wird die 2. Firma alle Güter verkaufen, also -D(p2 - c) Profit machen.
Die 1. Firma verkauft nichts, muss also nichts produzieren und hat den Profit von 0,
verbessert also ihr Profit (reduziert Verlust).
Es handelt es sich also nicht um einen Nash-Equilibrium.

(c,0)
1. Firma produziert in diesem Fall nichts, da alle Güter von der 2. Firma verkauft werden.
2. Firma hat den Profit von D(0 - c) = -Dc
Für die 1. Firma macht keinen Sinn den Preis zu ändern.
Die 2. Firma kann aber den Preis > c machen und dann den Verlust auf 0 zu reduzieren.
Es handelt es sich also nicht um einen Nash-Equilibrium.

(c,c)
Es ist ein Nash-Equilibrium.
In diesem Fall haben beide Firmen Profit von 0.
Falls eine Firma den Preis erhöht, verkauft sie nichts und ihr Profit ist immer noch 0.
Falls sie den Preis niedriger setzt, wird sie Verluste machen, was nicht ihren Profit verbessert.
