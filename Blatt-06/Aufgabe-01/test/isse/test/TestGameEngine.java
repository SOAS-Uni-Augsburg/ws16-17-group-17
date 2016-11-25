package isse.test;

import static org.junit.Assert.*;

import isse.model.GameBoard;
import isse.model.GameEngine;
import isse.model.PlayStrategy;
import isse.model.Player;
import isse.model.strategies.InteractiveStrategy;
import isse.model.strategies.NaiveStrategy;
import isse.model.strategies.RandomStrategy;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class TestGameEngine {

	private GameEngine engine;
	
	@Before
	public void setup() {
		engine = new GameEngine();
	}
	
	@Test
	@Ignore
	public void testNaiveStrategies() {
		PlayStrategy naive = new NaiveStrategy();
		engine.registerStrategy(Player.CROSSES, naive);
		engine.registerStrategy(Player.NOUGHTS, naive);
		
		// now play
		engine.play();
	}
	
	@Test
	@Ignore
	public void testHumanStrategies() {
		PlayStrategy interactive = new InteractiveStrategy();
		engine.registerStrategy(Player.CROSSES, interactive);
		engine.registerStrategy(Player.NOUGHTS, interactive);
		
		// now play
		engine.play();
	}

	@Test
	@Ignore
	public void testNaiveVsRandomStrategies() {
		PlayStrategy naive = new NaiveStrategy();
		RandomStrategy random = new RandomStrategy();
		engine.registerStrategy(Player.CROSSES, naive);
		engine.registerStrategy(Player.NOUGHTS, random);
		
		// now play
		engine.play();
	}
	
	// TODO: define n as test input parameter 
	@Test
	public void testNaiveVsRandomStrategiesNtimes() {
		
		PlayStrategy crosses = new NaiveStrategy();
		PlayStrategy noughts = new RandomStrategy();
		engine.registerStrategy(Player.CROSSES, crosses);
		engine.registerStrategy(Player.NOUGHTS, noughts);
		
		int crossesWinCount = 0;
		int noughtsWinCount = 0;
		
		int n = 100;
		for (int i = 0; i < n; i++) {
			// now play
			engine.play();
			
			Player winner = engine.getWinner();
			if (winner != null && winner.equals(Player.CROSSES)) {
				crossesWinCount++;
			} else if (winner != null && winner.equals(Player.NOUGHTS)) {
				noughtsWinCount++;
			}
			
			// reset board
			engine.setBoard(new GameBoard());
		}
		
		System.out.println("After " + n + " Games Player X has won " + crossesWinCount + " times and Player O has won " + noughtsWinCount + " times.");
		
	}
}
