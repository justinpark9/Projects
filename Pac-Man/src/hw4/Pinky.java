package hw4;

import static api.Direction.LEFT;
import static api.Direction.RIGHT;
import static api.Direction.UP;

import java.util.Random;

import api.Descriptor;
import api.Direction;
import api.Location;
import api.MazeMap;

/**
 * Pinky class for a Pacman game.
 * 
 * @author jpark
 */
public class Pinky extends Ghost {
	/**
	 * Constructs a new Pinky with the given maze, home location, base speed,
	 * initial direction, scatter target, and random number.
	 * 
	 * @param maze          maze configuration
	 * @param home          initial location
	 * @param baseSpeed     base speed increment
	 * @param homeDirection initial direction
	 * @param scatterTarget fixed target for use during SCATTER mode
	 * @param rand          predefined global random generator for use during
	 *                      FRIGHTENED mode
	 */
	public Pinky(MazeMap maze, Location home, double baseSpeed, Direction homeDirection, Location scatterTarget,
			Random rand) {
		super(maze, home, baseSpeed, homeDirection, scatterTarget, rand);
		// TODO Auto-generated constructor stub
	}

	@Override
	/**
	 * Returns the target location of Pinky during chase mode. The target is the 4
	 * cells in front of player's current location.
	 * 
	 * @param d Data container that contains Player's location, Player's direction,
	 *          and Blinky's location.
	 * @return	The target location of Pinky.
	 */
	protected Location setChaseTarget(Descriptor d) {
		// TODO Update target algorithm for Pinky
		int targetRow = 0;
		int targetCol = 0;

		if (d.getPlayerDirection() == RIGHT) {
			targetRow = d.getPlayerLocation().row();
			targetCol = d.getPlayerLocation().col() + 4;
		} else if (d.getPlayerDirection() == LEFT) {
			targetRow = d.getPlayerLocation().row();
			targetCol = d.getPlayerLocation().col() - 4;
		} else if (d.getPlayerDirection() == UP) {
			targetRow = d.getPlayerLocation().row() - 4;
			targetCol = d.getPlayerLocation().col();
		} else {
			targetRow = d.getPlayerLocation().row() + 4;
			targetCol = d.getPlayerLocation().col();
		}

		Location target = new Location(targetRow, targetCol);

		return target;
	}

}
