package hw4;

import java.util.Random;

import api.Descriptor;
import api.Direction;
import api.Location;
import api.MazeMap;

/**
 * Blinky class for a Pacman game.
 * 
 * @author jpark
 */
public class Blinky extends Ghost {
	/**
	 * Constructs a new Blinky with the given maze, home location, base speed,
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
	public Blinky(MazeMap maze, Location home, double baseSpeed, Direction homeDirection, Location scatterTarget,
			Random rand) {
		super(maze, home, baseSpeed, homeDirection, scatterTarget, rand);
		// TODO Auto-generated constructor stub
	}

	@Override
	/**
	 * Returns the target location of Blinky during chase mode. The target is the
	 * current player's location.
	 * 
	 * @param d Data container that contains Player's location, Player's direction,
	 *          and Blinky's location.
	 * @return	The target location of Blinky.
	 */
	protected Location setChaseTarget(Descriptor d) {
		Location target = new Location(d.getPlayerLocation().row(), d.getPlayerLocation().col());

		return target;
	}
}
