package ui;

import api.Descriptor;
import api.Direction;
import api.Location;
import api.PacmanGame;
import hw4.Blinky;

import static api.Direction.RIGHT;
import static api.Direction.UP;
import static api.Mode.*;

/**
 * Some ideas for initially testing the update() method.
 */

// NOTE: The tests below assume that you have a bogus, temporary implementation
// calculateNextCell in your Blinky class, like this:
//
//public void calculateNextCell(Descriptor d)
//{
//  Location currentLoc = getCurrentLocation();
//
//  if (currentLoc.row() > 2)
//  {
//    nextLoc = new Location(currentLoc.row() - 1, currentLoc.col());
//    nextDir = UP;
//  }
//  else
//  {
//    nextLoc = new Location(currentLoc.row(), currentLoc.col() + 1);
//    nextDir = RIGHT;
//  }
//} 
//

public class SimpleTest {

	public static final String[] SIMPLE1 = { "#######", "#.....#", "#.....#", "#.....#", "#.....#", "#..B..#",
			"#S....#", "#######", };

	public static void main(String[] args) {
		// using a frame rate of 10, the speed increment will be 0.4
		PacmanGame game = new PacmanGame(SIMPLE1, 10);

		// Blinky is always at index 0 in the enemies array
		Blinky b = (Blinky) game.getEnemies()[0];

		System.out.println(b.getCurrentLocation()); // expected (5, 3)
		System.out.println(b.getCurrentIncrement()); // expected 0.4
		System.out.println(b.getCurrentDirection()); // expected UP
		System.out.println(b.getRowExact() + ", " + b.getColExact()); // expected (5.5, 3.5)
		System.out.println(b.getNextCell()); // expected null
		System.out.println();

		// this should update value of getNextCell()
		b.calculateNextCell(makeDescriptor(game));
		System.out.println(b.getNextCell()); // expected (4, 3)
		System.out.println();

		// now some updates
		b.setMode(CHASE, makeDescriptor(game));
		b.update(makeDescriptor(game));
		System.out.println(b.getRowExact() + ", " + b.getColExact()); // ~5.1, 3.5
		b.update(makeDescriptor(game));
		System.out.println(b.getRowExact() + ", " + b.getColExact()); // ~4.7, 3.5
		System.out.println();

		// lots of updates! See the pdf for expected output
		for (int i = 0; i < 10; ++i) {
			b.update(makeDescriptor(game));
			System.out.println("Iteration " + i);
			System.out.println(b.getRowExact() + ", " + b.getColExact());
			System.out.println(b.getCurrentDirection());
			System.out.println(b.getNextCell());
			System.out.println();
		}
	}

	public static Descriptor makeDescriptor(PacmanGame game) {
		Location enemyLoc = game.getEnemies()[0].getCurrentLocation();
		Location playerLoc = game.getPlayer().getCurrentLocation();
		Direction playerDir = game.getPlayer().getCurrentDirection();
		return new Descriptor(playerLoc, playerDir, enemyLoc);
	}
}
