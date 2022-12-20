package hw4;

import static api.Direction.DOWN;

import static api.Direction.LEFT;
import static api.Direction.RIGHT;
import static api.Direction.UP;

import java.util.ArrayList;
import java.util.Random;

import api.Actor;
import api.Descriptor;
import api.Direction;
import api.Location;
import api.MazeMap;
import api.Mode;

/**
 * Abstract superclass for all characters in Pacman game.
 * 
 * @author jpark
 */
public abstract class Character implements Actor {
	/**
	 * Margin of error for comparing exact position to centerline of cell.
	 */
	public static final double ERR = .001;

	/**
	 * Maze configuration.
	 */
	private MazeMap maze;

	/**
	 * Initial location on reset().
	 */
	private Location home;

	/**
	 * Initial direction on reset().
	 */
	private Direction homeDirection;

	/**
	 * Basic speed increment, used to determine currentIncrement.
	 */
	private double baseIncrement;

	/**
	 * Current direction of character.
	 */
	private Direction currentDirection;

	/**
	 * Current speed increment, added in direction of travel each frame.
	 */
	private double currentIncrement;

	/**
	 * Row (y) coordinate, in units of cells. The row number for the currently
	 * occupied cell is always the int portion of this value.
	 */
	private double rowExact;

	/**
	 * Column (x) coordinate, in units of cells. The column number for the currently
	 * occupied cell is always the int portion of this value.
	 */
	private double colExact;

	/**
	 * Fixed target for use during SCATTER mode.
	 */
	private Location scatterTarget;

	/**
	 * Predefined global random generator for use during FRIGHTENED mode
	 */
	private Random rand;

	/**
	 * Constructs a new Ghost with the given maze, home location, base speed,
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
	public Character(MazeMap maze, Location home, double baseSpeed, Direction homeDirection, Location scatterTarget,
			Random rand) {
		this.maze = maze;
		this.home = home;
		this.baseIncrement = baseSpeed;
		this.currentIncrement = baseSpeed;
		this.homeDirection = homeDirection;
		this.scatterTarget = scatterTarget;
		this.rand = rand;
	}

	/**
	 * Constructs a new Pacman with the given maze, home location, base speed, and
	 * initial direction.
	 * 
	 * @param maze          maze configuration
	 * @param home          initial location
	 * @param baseSpeed     base speed increment
	 * @param homeDirection initial direction
	 */
	public Character(MazeMap maze, Location home, double baseSpeed, Direction homeDirection) {
		this.maze = maze;
		this.home = home;
		this.baseIncrement = baseSpeed;
		this.currentIncrement = baseSpeed;
		this.homeDirection = homeDirection;
	}

	@Override
	/**
	 * Returns the increment of the actor at the start of the game.
	 * 
	 * @return The base increment of the actor.
	 */
	public double getBaseIncrement() {
		return baseIncrement;
	}

	@Override
	/**
	 * Returns the exact column location of the actor.
	 * 
	 * @return The exact column location of the actor.
	 */
	public double getColExact() {
		return colExact;
	}

	@Override
	/**
	 * Returns the current increment of the actor.
	 * 
	 * @return The increment of the actor.
	 */
	public double getCurrentIncrement() {
		return currentIncrement;
	}

	@Override
	/**
	 * Returns the current location of the actor.
	 * 
	 * @return The current location of the actor.
	 */
	public Location getCurrentLocation() {
		return new Location((int) rowExact, (int) colExact);
	}

	@Override
	/**
	 * Returns the current direction of the actor.
	 * 
	 * @return The current direction of the actor.
	 */
	public Direction getCurrentDirection() {
		return currentDirection;
	}

	@Override
	/**
	 * Returns the direction of the actor at the start of the game.
	 * 
	 * @return The direction of the actor at the start of the game.
	 */
	public Direction getHomeDirection() {
		return homeDirection;
	}

	@Override
	/**
	 * Returns the location of the actor at the start of the game.
	 * 
	 * @return The location of the actor at the start of the game.
	 */
	public Location getHomeLocation() {
		return home;
	}

	/**
	 * Returns the next location of a ghost.
	 * 
	 * @return The next location of a ghost.
	 */
	protected abstract Location getNextCell();

	@Override
	/**
	 * Returns the current mode of a ghost.
	 * 
	 * @return The current mode of a ghost.
	 */
	public abstract Mode getMode();

	/**
	 * Returns the current MazeMap.
	 * 
	 * @return The current MazeMap.
	 */
	protected MazeMap getMaze() {
		return maze;
	}

	@Override
	/**
	 * Returns the exact row location of the actor.
	 * 
	 * @return The exact row location of the actor.
	 */
	public double getRowExact() {
		return rowExact;
	}

	/**
	 * Returns the target of a ghost in scatter mode.
	 * 
	 * @return The target of a ghost in scatter mode.
	 */
	protected Location getScatterTarget() {
		return scatterTarget;
	}

	/**
	 * Returns the random number generator for the ghost during frightened mode.
	 * 
	 * @return The random number used for frightened mode.
	 */
	protected Random getRand() {
		return rand;
	}

	@Override
	/**
	 * Resets this actor to its initial location, direction, and mode.
	 */
	public abstract void reset();

	@Override
	/**
	 * Sets the exact column location.
	 * 
	 * @param c The new column location.
	 */
	public void setColExact(double c) {
		colExact = c;
	}

	@Override
	/**
	 * Sets the new direction of the actor.
	 * 
	 * @param dir The new direction of the actor.
	 */
	public void setDirection(Direction dir) {
		currentDirection = dir;
	}

	@Override
	/**
	 * Sets the mode of the ghost.
	 * 
	 * @param mode New mode of the ghost.
	 * @param d    Descriptor of the current game.
	 */
	public abstract void setMode(Mode mode, Descriptor d);

	@Override
	/**
	 * Sets the exact row location.
	 * 
	 * @param r The new row location.
	 */
	public void setRowExact(double r) {
		rowExact = r;
	}

	/**
	 * Sets the increment of a character.
	 * 
	 * @param newIncrement The new increment of a character.
	 */
	protected void setCurrentIncrement(double newIncrement) {
		currentIncrement = newIncrement;
	}

	/**
	 * Updates the actor's position. This method is typically invoked once per
	 * frame.
	 * 
	 * @param d current game descriptor
	 */
	public abstract void update(Descriptor d);

	/**
	 * Calculates the distance between two cells.
	 * 
	 * @param nextCell   The potential next cell that is being considered
	 * @param targetCell The ghost's target cell
	 */
	protected static double calculateDistance(Location nextCell, Location targetCell) {
		double result = Math
				.sqrt((double) (targetCell.row() - nextCell.row()) * (double) (targetCell.row() - nextCell.row())
						+ (double) (targetCell.col() - nextCell.col()) * (double) (targetCell.col() - nextCell.col()));

		return result;
	}

	/**
	 * Protected static method that determines whether a character is past the
	 * center of the current cell or not.
	 * 
	 * @param currentDirection The current direction the character is heading.
	 * @param currentLocation  The current location of the character.
	 * @param rowExact         The exact row coordinate of the character.
	 * @param colExact         The exact col coordinate of the character.
	 */
	protected static boolean isPastCenter(Direction currentDirection, Location currentLocation, double rowExact,
			double colExact) {
		boolean result = false;
		double centerRow = (double) currentLocation.row() + 0.5;
		double centerCol = (double) currentLocation.col() + 0.5;

		if (currentDirection == Direction.RIGHT) {
			// If past center going right
			if (colExact > centerCol + ERR) {
				result = true;
			}
		} else if (currentDirection == Direction.LEFT) {
			// If past center going left
			if (colExact < centerCol - ERR) {
				result = true;
			}
		} else if (currentDirection == Direction.UP) {
			// If past center going up
			if (rowExact < centerRow - ERR) {
				result = true;
			}
		} else {
			// If past center going down
			if (rowExact > centerRow + ERR) {
				result = true;
			}
		}

		return result;
	}

	/**
	 * Protected static method that finds the possible cells for the character to
	 * move to.
	 * 
	 * @param currentMode      The current mode of the ghost.
	 * @param currentLocation  The current location of the ghost.
	 * @param currentDirection The current direction of the ghost.
	 * @param maze             The MazeMap of the current game.
	 */
	protected static ArrayList<Location> findPossibleCells(Mode currentMode, Location currentLocation,
			Direction currentDirection, MazeMap maze) {
		Location temp;
		ArrayList<Location> possibleLocations = new ArrayList<Location>();

		if (currentMode != Mode.INACTIVE) {
			if (!(currentMode != Mode.FRIGHTENED && currentDirection == DOWN)) {
				if (maze.isWall(currentLocation.row() - 1, currentLocation.col()) == false) {
					temp = new Location(currentLocation.row() - 1, currentLocation.col());
					possibleLocations.add(temp);
				}
			}
			if (!(currentMode != Mode.FRIGHTENED && currentDirection == RIGHT)) {
				if (maze.isWall(currentLocation.row(), currentLocation.col() - 1) == false) {
					temp = new Location(currentLocation.row(), currentLocation.col() - 1);
					possibleLocations.add(temp);
				}

			}
			if (!(currentMode != Mode.FRIGHTENED && currentDirection == UP)) {
				if (maze.isWall(currentLocation.row() + 1, currentLocation.col()) == false) {
					temp = new Location(currentLocation.row() + 1, currentLocation.col());
					possibleLocations.add(temp);
				}
			}
			if (!(currentMode != Mode.FRIGHTENED && currentDirection == LEFT)) {
				;
				if (maze.isWall(currentLocation.row(), currentLocation.col() + 1) == false) {
					temp = new Location(currentLocation.row(), currentLocation.col() + 1);
					possibleLocations.add(temp);
				}

			}
		}
		return possibleLocations;
	}

	/**
	 * Determines the difference between current position and center of current
	 * cell, in the direction of travel.
	 */
	protected static double distanceToCenter(double colExact, double rowExact, Direction currentDirection) {
		switch (currentDirection) {
		case LEFT:
			return colExact - ((int) colExact) - 0.5;
		case RIGHT:
			return 0.5 - (colExact - ((int) colExact));
		case UP:
			return rowExact - ((int) rowExact) - 0.5;
		case DOWN:
			return 0.5 - (rowExact - ((int) rowExact));
		}
		return 0;
	}

}
