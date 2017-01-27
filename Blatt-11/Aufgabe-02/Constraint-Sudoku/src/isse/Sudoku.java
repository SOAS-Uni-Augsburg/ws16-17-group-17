package isse;

import java.util.Set;
import java.util.TreeSet;

/**
 * Represents one particular sudoku board
 * @author alexander
 *
 */
public class Sudoku {

	protected final int size = 9;
	protected final int cellSize = 3;
	protected final int nullInt = -1;
	protected int[][] board;
	
	public Sudoku() {
		board = new int[size][size];
	}
	
	public Sudoku(Sudoku input) {
		int length = input.board.length;
	    board = new int[length][input.board[0].length];
	    for (int i = 0; i < length; i++) {
	        System.arraycopy(input.board[i], 0, board[i], 0, input.board[i].length);
	    }
	}

	/**
	 * Make variable x_{i, j} unassigned, e.g, initially or during search
	 * @param i
	 * @param j
	 */
	public void putEmpty(int i, int j) {
		board[i][j] = nullInt;
	}

	public void put(int i, int j, int numericValue) {
		board[i][j] = numericValue;		
	}
	
	public int get(int i, int j) {
		return board[i][j];
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(4 *size*size*size); // allocate enough space for whitespace etc
		sb.append("-----------------------\n");
		for(int i = 0; i < size; ++i) {
			for(int j = 0; j < size; ++j) {
				if(board[i][j] == nullInt) 
					sb.append(" ");
				else 
					sb.append(board[i][j]);
				sb.append(' ');
				if(j % cellSize == cellSize-1) {
					sb.append("| ");
				}
			}
			sb.append("\n");
			if(i % cellSize == cellSize -1){
				sb.append("-----------------------\n");
			}
		}
		return sb.toString();
	}

	public boolean isUnassigned(int i, int j) {
		return board[i][j] == nullInt;
	}

	public boolean isSolution(Sudoku original) {
		// has to be fully assigned and has to match original
		for(int i = 0; i < size; ++i) {
			for(int j = 0; j < size; ++j){
				if(board[i][j] == nullInt) 
					return false;
				if(original.board[i][j] != nullInt && original.board[i][j] != board[i][j]) 
					return false;
			}
		}
		
		return isValid();
		
	}
	
	public boolean isValid() {
		// test rows
		int[] values = new int[size];
		
		for(int i = 0; i < size; ++i) {
			System.arraycopy(board[i], 0, values, 0, values.length);
			if(!checkDomain(values) || ! alldifferent(values))
				return false;
		}
		
		// test columns
		for(int j = 0; j < size; ++j) {
			for(int i = 0; i < size; ++i) {
				values[i] = board[i][j];
			}
			if(!checkDomain(values) || ! alldifferent(values))
				return false;
		}
		
		// test subfields
		int ctr = 0; 
		for(int sfI = 0; sfI < cellSize; ++sfI) {
			for(int sfJ = 0; sfJ < cellSize; ++sfJ) {
				// iterate from 
				
				ctr = 0;
				for(int i = 0; i < cellSize; ++i) {
					for(int j = 0; j < cellSize; ++j) {
						values[ctr] = board[sfI*cellSize + i][sfJ * cellSize + j];
						++ctr;
					}
				}
				if(!checkDomain(values) || ! alldifferent(values))
					return false;
			}	
		}
		return true;
	}

	/**
	 * Implements the feasibility check of an alldifferent constraint
	 * assumes that the domains are between 0 and 9
	 * @param values
	 * @return
	 */
	private boolean alldifferent(int[] values) {
		Set<Integer> seenVals = new TreeSet<Integer>();
		for(int i = 0; i < values.length; ++i) {
			if(values[i] == nullInt)
				continue;
			
			if(seenVals.contains(values[i]))
				return false;
			else 
				seenVals.add(values[i]);
		}
		return true;
	}

	private boolean checkDomain(int[] values) {
		for(int i = 0; i < values.length; ++i) {
			if(values[i] != nullInt) {
				if(values[i] < 1 || values[i] > 9)
					return false;
			}
		}
		return true;
	}

}
