package isse.model.strategies;

import java.util.Random;

import isse.model.FieldState;
import isse.model.GameBoard;
import isse.model.Move;
import isse.model.PlayStrategy;

/**
 * Takes the first free field
 * @author isse-soas
 *
 */
public class RandomStrategy implements PlayStrategy{

	@Override
	public Move getMove(GameBoard board) {
		
		Random random = new Random(System.currentTimeMillis());
		
		while (true) {
			if (board.isFull()) {
				return null;
			}
			
			int x = random.nextInt(3);
			int y = random.nextInt(3);
			
			if(board.read(x, y) == FieldState.EMPTY) {
				return new Move(x,y);
			}
		}
		
	}

}
