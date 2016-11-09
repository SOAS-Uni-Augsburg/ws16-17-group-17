# Selbst-organisierende, adaptive Systeme

## Übungsblatt 3

### Aufgabe 1

b) Irgendwann gibt es keine Menschen mehr, weil alle zu Zombies wurden. Danach
sterben die Zombies aus, da ihnen die Nahrung ausgegangen ist.
In der Simulation überleben jedoch manchmal ein paar Menschen wobei die Zombies
aussterben.

c) Das beobachtete Verhalten ist, dass alle immunen Menschen sowie gelegentlich
ein paar normale Menschen überleben und die Zombies aussterben.
Die Zombies erhalten nur von nicht-immunen Menschen Energie. Da die Anzahl der
nicht-immunen Menschen abnimmt, können sich die Zombies ab einem gewissen Punkt
nicht mehr vermehren.
Deswegen sterben sie letzten Endes aus wobei zumindest die immunen Menschen
übrig bleiben.

d) Dadurch, dass der Anteil der immunen Menschen auch wächst, immune Menschen
aber auch nicht-immune Menschen hervorbringt, entstehen immer nicht-immune
Menschen als Futter für die Zombies. Dadurch sterben die Zombies, für unsere
getesteten Parameter, nicht aus, sondern alle Populationen wachsen
kontinuierlich an.

e) Fällt die Gegenwehr der Menschen entsprechend heftig aus, so führt dies zur
vollständigen Ausrottung der Zombies.

f) Unsere Implementierung der Sterblichkeit lässt in jedem Tick einen festen
Prozentsatz an Menschen sterben. Je größer also die Population der Menschen
wird, desto mehr Menschen sterben in jedem Tick.
Bei hoher Gegenwehr und Reproduktion dominiert die Menschheit. Bei niedriger
Gegenwehr, hoher Energie für gebissene Menschen und niedrigem Energieverlust
setzen sich die Zombies durch. 
Eine stabile Koexistenz ergibt sich für folgende Parameter:
	
	population-size = 500
	initial-zombie-percentage = 5
	bite-energy-gain = 40
	energy-loss-per-tick = 5
	initial-energy = 70
	immune-percentage = 1
	reproduction-percentage = 5
	reproduce-immune-percentage = 10
	resistance-percentage = 50
	human-death-percentage = 1

Die Belegungen für die stabile Koexistenz sind weitestgehend realistisch, einzig
der reproduction-percentage ist zu hoch.