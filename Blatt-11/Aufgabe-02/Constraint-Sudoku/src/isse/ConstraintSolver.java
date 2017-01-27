package isse;

/**
 * A constraint solver for sudokus, can use propagation 
 * as well as branching heuristics
 * @author alexander
 *
 */
public class ConstraintSolver implements Solver {
	protected StatisticsObject stats;
	
	public ConstraintSolver(StatisticsObject stats) {
		super();
		this.stats = stats;
	}

	@Override
	public StatisticsObject getStats() {
		return stats;
	}

	@Override
	public Sudoku solve(Sudoku input) {		
		return null;
	}

}
