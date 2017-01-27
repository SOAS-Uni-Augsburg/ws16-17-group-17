package isse;

public class VariableToCoordinates {
	public static class Pair {
		public int i;
		public int j;
		
		public Pair(int i, int j) {
			super();
			this.i = i;
			this.j = j;
		}
	}
	
	public static Pair toPair(int index, int size) {
		Pair p = new Pair(0,0);
		p.j = index % size;
		p.i = index / size;
		return p;
	}
	
	public static int toIndex(Pair p, int size) {
		return p.i * size + p.j;
	}
}
