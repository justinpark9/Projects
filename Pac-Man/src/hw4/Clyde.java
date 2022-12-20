package hw4;

import java.util.Random;

import api.Descriptor;
import api.Direction;
import api.Location;
import api.MazeMap;

/**
 * Clyde class for a Pacman game.
 * 
 * @author jpark
 */
public class Clyde extends Ghost {
	/**
	 * Constructs a new Clyde with the given maze, home location, base speed,
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
	public Clyde(MazeMap maze, Location home, double baseSpeed, Direction homeDirection, Location scatterTarget,
			Random rand) {
		super(maze, home, baseSpeed, homeDirection, scatterTarget, rand);
		// TODO Auto-generated constructor stub
	}

	@Override
	/**
	 * Returns the target location of Clyde during chase mode. When Clyde is more
	 * than 8 cells away from the player, the target is the same as Blinky's.
	 * Otherwise, the target is scatterTarget.
	 * 
	 * @param d Data container that contains Player's location, Player's direction,
	 *          and Blinky's location.
	 * @return	The target location of Clyde.
	 */
	protected Location setChaseTarget(Descriptor d) {
		// Calculate distance between Clyde and Player.
		double distance = super.calculateDistance(super.getCurrentLocation(), d.getPlayerLocation());

		// If distance is greater than 8, target is the same as Blinky's. Else, target
		// is scatter.
		if (distance > 8) {
			return new Location(d.getPlayerLocation().row(), d.getPlayerLocation().col());
		} else {
			return new Location(super.getScatterTarget().row(), super.getScatterTarget().col());
		}
	}

}
