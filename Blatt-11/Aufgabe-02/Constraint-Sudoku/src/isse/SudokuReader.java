package isse;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Reads Sudoku puzzles from a .sdk-File 
 * as used in http://www.sudocue.net/
 * @author alexander
 *
 */
public class SudokuReader {

	protected final int size = 9;
	
	public Sudoku readFromFile(File sdkFile) throws FileNotFoundException {
		Scanner sc = new Scanner(sdkFile);
		Sudoku sudoku = new Sudoku();
		
		int i = 0;
		
		while(sc.hasNextLine()) {
			String line = sc.nextLine(); 
			for(int j = 0; j < size; ++j) {
				char c = line.charAt(j);
				if(c == '.') {
					sudoku.putEmpty(i,j);
				} else 
					sudoku.put(i,j, Character.getNumericValue(c));
			}
			++i;
		}
		sc.close();
		return sudoku;
	}
}
