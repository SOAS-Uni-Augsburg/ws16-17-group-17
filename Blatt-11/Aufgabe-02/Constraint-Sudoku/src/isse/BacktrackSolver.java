package isse;

import java.util.ArrayList;
import java.util.List;

import isse.VariableToCoordinates.Pair;

public class BacktrackSolver implements Solver {
	
	protected final int size = 9;
	protected final int nVars = size*size;
	protected StatisticsObject stats;
	
	/* 
	 * Uses a very simple encoding; 81 variables such
	 * that grid[i][j] is represented by 
	 * variable x_{i*size + j}; each having domain 0..9
	 * 
	 * constraints are the diverse row constraints; col constraints
	 * and sub-fields
	 * 
	 */
	
	public BacktrackSolver(StatisticsObject stats) {
		this.stats = stats;
	}

	@Override
	public Sudoku solve(Sudoku input) {
		stats.tickRuntime();
		Sudoku assignment = new Sudoku(input);
		List<Integer> unassignedVariables = new ArrayList<Integer>(size*size);
		for(int i = 0; i < size; ++i) {
			for(int j = 0; j < size; ++j) {
				if(input.isUnassigned(i,j)) {
					unassignedVariables.add(VariableToCoordinates.toIndex(new Pair(i,j), size));
				}
			}
		}
		assignment = solveRec(input, assignment, unassignedVariables, 0);
		stats.tockRuntime();
		return assignment;
	}

	private Sudoku solveRec(Sudoku input, Sudoku assignment, List<Integer> unassignedVariables, int variableIndex) {
		stats.markRecursiveCall();
		if(variableIndex == unassignedVariables.size()) { // done here 
			return assignment;
		}
		
		Pair p = VariableToCoordinates.toPair(unassignedVariables.get(variableIndex), size);
		
		for(int domainValue = 1; domainValue <= 9; ++domainValue) {
			assignment.put(p.i, p.j, domainValue);
			if(assignment.isValid()) {

				Sudoku result = solveRec(input, assignment, unassignedVariables, variableIndex+1);
				if(result != null)
					return result;
				else { // undo last assignment
					assignment.putEmpty(p.i, p.j);
				}
			}
			assignment.putEmpty(p.i, p.j);
		}
		return null;
		
	}

	@Override
	public StatisticsObject getStats() {
		return this.stats;
	}
}
