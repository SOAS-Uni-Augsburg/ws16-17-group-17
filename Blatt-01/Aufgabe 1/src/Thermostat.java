
public class Thermostat {
	
	// zuerst Temperaturausgleich, dann Regeln

	public void feedback(double[] outsideTemp, double min, double max, double init) {
		double current = init;
		System.out.println("Raumtemperatur: " + current);
		for (double outside : outsideTemp) {
			double mixed = (outside + current) / 2;
			System.out.println("Mischlufttemperatur: " + mixed);
			if (mixed + 5 <= max) {
				mixed += 5;
				System.out.println("-> heizen");
			} else if (mixed - 5 >= min) {
				mixed -= 5;
				System.out.println("-> kühlen");
			} else {
				System.out.println("-> passiv");
			}
			current = mixed;
			System.out.println("Raumtemperatur: " + current);
		}
	}
	
	// andere Reihenfolge (zuerst Regeln, dan Ausgleich)
//	public void feedback(double[] outsideTemp, double min, double max, double init) {
//		double current = init;
//		System.out.println("Raumtemperatur: " + current);
//		for (double outside : outsideTemp) {
//			if (current + 5 <= max) {
//				current += 5;
//				System.out.println("-> heizen");
//			} else if (current - 5 >= min) {
//				current -= 5;
//				System.out.println("-> kühlen");
//			} else {
//				System.out.println("-> passiv");
//			}
//			System.out.println("Raumtemperatur nach dem Regeln: " + current);
//			
//			double mixed = (outside + current) / 2;
//			System.out.println("Mischlufttemperatur: " + mixed);
//			current = mixed;
//		}
//	}

	public static void main(String[] args) {
		double[] outsideTemp = new double[] { 10.0, 25.0, 30.0, 18.5, 31.0 };
		Thermostat t = new Thermostat();
		t.feedback(outsideTemp, 15.0, 20.0, 18.0);
	}

}
