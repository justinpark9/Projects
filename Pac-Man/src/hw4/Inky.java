package hw4;

import static api.Direction.DOWN;
import static api.Direction.RIGHT;
import static api.Direction.UP;

import java.util.Random;

import api.Descriptor;
import api.Direction;
import api.Location;
import api.MazeMap;

/**
 * Inky class for a Pacman game.
 * 
 * @author jpark
 */
public class Inky extends Ghost {
	/**
	 * Constructs a new Inky with the given maze, home location, base speed, initial
	 * direction, scatter target, and random number.
	 * 
	 * @param maze          maze configuration
	 * @param home          initial location
	 * @param baseSpeed     base speed increment
	 * @param homeDirection initial direction
	 * @param scatterTarget fixed target for use during SCATTER mode
	 * @param rand          predefined global random generator for use during
	 *                      FRIGHTENED mode
	 */
	public Inky(MazeMap maze, Location home, double baseSpeed, Direction homeDirection, Location scatterTarget,
			Random rand) {
		super(maze, home, baseSpeed, homeDirection, scatterTarget, rand);
		// TODO Auto-generated constructor stub
	}

	@Override
	/**
	 * Returns the target location of Inky during chase mode. The target is double
	 * the vector from Blinky's current location to the cell that is two cells in
	 * front of player.
	 * 
	 * @param d Data container that contains Player's location, Player's direction,
	 *          and Blinky's location.
	 * @return	The target location of Inky.
	 */
	protected Location setChaseTarget(Descriptor d) {
		// TODO Update target algorithm for Inky
		int twoCellsRow;
		int twoCellsCol;

		// Find location of cell two cells in front of player.
		if (d.getPlayerDirection() == UP) {
			twoCellsRow = d.getPlayerLocation().row() - 2;
			twoCellsCol = d.getPlayerLocation().col();
		} else if (d.getPlayerDirection() == DOWN) {
			twoCellsRow = d.getPlayerLocation().row() + 2;
			twoCellsCol = d.getPlayerLocation().col();
		} else if (d.getPlayerDirection() == RIGHT) {
			twoCellsRow = d.getPlayerLocation().row();
			twoCellsCol = d.getPlayerLocation().col() + 2;
		} else {
			twoCellsRow = d.getPlayerLocation().row();
			twoCellsCol = d.getPlayerLocation().col() - 2;
		}
		Location twoCellsInFront = new Location(twoCellsRow, twoCellsCol);

		int differenceRow;
		int differenceCol;

		// Calculate distance between Blinky and twoCellsInFront.
		differenceRow = d.getBlinkyLocation().row() - twoCellsInFront.row();
		differenceCol = d.getBlinkyLocation().col() - twoCellsInFront.col();

		// Multiply distances by 2
		differenceRow = differenceRow * 2;
		differenceCol = differenceCol * 2;

		// Add the result to Blinky's location
		int resultRow = d.getBlinkyLocation().row() - differenceRow;
		int resultCol = d.getBlinkyLocation().col() - differenceCol;

		Location target = new Location(resultRow, resultCol);

		return target;
	}
}
