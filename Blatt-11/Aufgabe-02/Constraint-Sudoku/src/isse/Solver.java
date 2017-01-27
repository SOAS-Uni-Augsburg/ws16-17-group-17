package isse;

/**
 * Generic solver interface - just asks for a solved instance and
 * a stats object
 * @author alexander
 *
 */
public interface Solver {

	public abstract StatisticsObject getStats();

	public abstract Sudoku solve(Sudoku input);

}
