package hw4;

import static api.Direction.DOWN;
import static api.Direction.LEFT;
import static api.Direction.RIGHT;
import static api.Direction.UP;

import java.util.ArrayList;
import java.util.Random;

import api.Descriptor;
import api.Direction;
import api.Location;
import api.MazeMap;
import api.Mode;

/**
 * Abstract superclass for ghosts.
 * 
 * @author jpark
 */
public abstract class Ghost extends Character {

	/**
	 * Next cell Ghost will go to.
	 */
	private Location nextLoc;

	/**
	 * Next direction Ghost will go to.
	 */
	private Direction nextDir;

	/**
	 * Current mode of ghost.
	 */
	private Mode currentMode;

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
	public Ghost(MazeMap maze, Location home, double baseSpeed, Direction homeDirection, Location scatterTarget,
			Random rand) {
		super(maze, home, baseSpeed, homeDirection, scatterTarget, rand);
		// TODO Auto-generated constructor stub

	}

	@Override
	/**
	 * Resets this actor to its initial location, direction, and mode.
	 */
	public void reset() {
		currentMode = Mode.INACTIVE;

		Location homeLoc = getHomeLocation();
		setRowExact(homeLoc.row() + 0.5);
		setColExact(homeLoc.col() + 0.5);
		setDirection(getHomeDirection());
		super.setCurrentIncrement(super.getBaseIncrement());
	}

	@Override
	/**
	 * Returns the next location of a ghost.
	 * 
	 * @return The next location of a ghost.
	 */
	public Location getNextCell() {
		return nextLoc;
	}

	@Override
	/**
	 * Returns the current mode of a ghost.
	 * 
	 * @return The current mode of a ghost.
	 */
	public Mode getMode() {
		// TODO Auto-generated method stub
		return currentMode;
	}

	@Override
	/**
	 * Sets the mode of the ghost.
	 * 
	 * @param newMode The new mode to be set.
	 * @param d       Descriptor of the current game.
	 */
	public void setMode(Mode newMode, Descriptor d) {
		// TODO Auto-generated method stub
		// Set new mode and calculate next cell
		currentMode = newMode;
		calculateNextCell(d);
		// Update speed
		if (currentMode == Mode.FRIGHTENED) {
			super.setCurrentIncrement(super.getBaseIncrement() * (2.0 / 3.0));
		} else if (currentMode == Mode.DEAD) {
			super.setCurrentIncrement(super.getBaseIncrement() * 2.0);
		} else {
			super.setCurrentIncrement(super.getBaseIncrement());
		}
	}

	/**
	 * Sets the next direction of the ghost.
	 * 
	 * @param newDir The new direction of the ghost.
	 */
	public void setNextDirection(Direction newDir) {
		nextDir = newDir;
	}

	/**
	 * Sets the next location of the ghost.
	 * 
	 * @param newLoc The new location of the ghost.
	 */
	public void setNextLocation(Location newLoc) {
		nextLoc = newLoc;
	}

	@Override
	/**
	 * Updates the actor's position. This method is typically invoked once per
	 * frame.
	 * 
	 * @param d current game descriptor
	 */
	public void update(Descriptor desc) {

		// Does nothing if the mode is inactive
		if (currentMode != Mode.INACTIVE) {
			double tempIncrement = super.getCurrentIncrement();
			double centerRow = (double) super.getCurrentLocation().row() + 0.5;
			double centerCol = (double) super.getCurrentLocation().col() + 0.5;

			// Adjust increment if distance to center of cell + ERR is less than current
			// increment
			if (isPastCenter(super.getCurrentDirection(), super.getCurrentLocation(), super.getRowExact(),
					super.getColExact()) == false && nextDir != super.getCurrentDirection()) {
				if (super.getCurrentDirection() == UP) {
					if (super.getRowExact() - (centerRow - ERR) < super.getCurrentIncrement()) {
						tempIncrement = super.getRowExact() - centerRow;
					}
				} else if (super.getCurrentDirection() == DOWN) {
					if ((centerRow + ERR) - super.getRowExact() < super.getCurrentIncrement()) {
						tempIncrement = centerRow - super.getRowExact();
					}
				} else if (super.getCurrentDirection() == RIGHT) {
					if ((centerCol + ERR) - super.getColExact() < super.getCurrentIncrement()) {
						tempIncrement = centerCol - super.getColExact();
					}
				} else {
					if (super.getColExact() - (centerCol - ERR) < super.getCurrentIncrement()) {
						tempIncrement = super.getColExact() - centerCol;
					}
				}
			}

			// update the position based on the current direction and current increment
			if (super.getCurrentDirection() == UP) {
				setRowExact(getRowExact() - tempIncrement);
			} else if (super.getCurrentDirection() == DOWN) {
				setRowExact(getRowExact() + tempIncrement);
			} else if (super.getCurrentDirection() == RIGHT) {
				if (super.getColExact() + tempIncrement + 0.5 >= super.getMaze().getNumColumns()) {
					System.out.println("Next Update: " + super.getColExact() + tempIncrement + 0.5);
					super.setColExact(super.getColExact() + tempIncrement + 0.5 - super.getMaze().getNumColumns());
				} else {
					super.setColExact(getColExact() + tempIncrement);
				}
			} else {
				if (super.getColExact() - tempIncrement - 0.5 < 0) {
					System.out.println("Next Update: " + (super.getColExact() - tempIncrement - 0.5));
					super.setColExact(super.getMaze().getNumColumns()
							+ (super.getColExact() - tempIncrement - 0.5));
				} else {
					super.setColExact(getColExact() - tempIncrement);
				}
			}

			// Once a new cell is reached, calculateNextCell
			if (super.getCurrentLocation().equals(nextLoc)) {
				super.setDirection(nextDir);
				calculateNextCell(desc);
			}

			// Change direction if Blinky is at the center of the cell and nextDir !=
			// currentDirection
			if (Math.abs(super.getRowExact() - centerRow) < ERR && Math.abs(super.getColExact() - centerCol) < ERR
					&& nextDir != super.getCurrentDirection()) {
				super.setDirection(nextDir);
				tempIncrement = super.getCurrentIncrement();
			}
		}
	}

	/**
	 * Calculates the next cell that the ghost will go to. If the current mode is
	 * dead, the target cell is home. If the current mode is scatter, ghost's target
	 * cell is scatterTarget. If the current mode is chase, Blinky's target cell is
	 * Pac-Man's current cell. If the current mode is frightened, the ghost picks a
	 * neighboring cell at random based on rand from constructor. Does nothing if
	 * the current mode is inactive.
	 * 
	 * @param d Descriptor of the current game
	 */
	public void calculateNextCell(Descriptor d) {

		if (getMode() != Mode.INACTIVE) {
			double minDistance;
			Location target = null;
			Location nextCell = null;
			ArrayList<Location> possibleLocations = new ArrayList<Location>();
			possibleLocations = findPossibleCells(currentMode, getCurrentLocation(), super.getCurrentDirection(),
					super.getMaze());

			if (isPastCenter(super.getCurrentDirection(), super.getCurrentLocation(), super.getRowExact(),
					super.getColExact()) == false) {
				// Figure out what the target is
				if (currentMode == Mode.DEAD) {
					target = super.getHomeLocation();
				}
				if (currentMode == Mode.SCATTER) {
					target = super.getScatterTarget();
				}
				if (currentMode == Mode.CHASE) {
					// Create setChaseTarget method in each ghost class
					target = setChaseTarget(d);
				}
				if (currentMode == Mode.FRIGHTENED) {
					target = possibleLocations.get(super.getRand().nextInt(possibleLocations.size()));
				}

				// Get possible cells
				possibleLocations = findPossibleCells(currentMode, getCurrentLocation(), super.getCurrentDirection(),
						super.getMaze());

				// Get cell with the shortest distance
				minDistance = calculateDistance(possibleLocations.get(0), target);
				nextCell = possibleLocations.get(0);
				for (int i = 1; i < possibleLocations.size(); i++) {
					// Check distance between potential cell and target cell
					double temp = calculateDistance(possibleLocations.get(i), target);
					if (temp < minDistance - ERR) {
						minDistance = temp;
						nextCell = possibleLocations.get(i);
					}
				}

				// Set next direction
				if (nextCell.row() == super.getCurrentLocation().row()) {
					if (nextCell.col() < super.getCurrentLocation().col()) {
						nextDir = LEFT;
					} else {
						nextDir = RIGHT;
					}
				} else {
					if (nextCell.row() < super.getCurrentLocation().row()) {
						nextDir = UP;
					} else {
						nextDir = DOWN;
					}
				}

				nextLoc = nextCell;
			}
		}
	}

	/**
	 * Protected helper method to determine if the ghost is past the center of the
	 * current cell or not.
	 * 
	 * @param currentLocation The current location of the ghost.
	 * @param rowExact        The exact row coordinate of the ghost.
	 * @param colExact        The exact col coordinate of the ghost.
	 * 
	 * @return Whether (true) or not (false) the ghost is past the center of the
	 *         cell.
	 */
	protected boolean isPastCenter(Location currentLocation, double rowExact, double colExact) {
		boolean result = false;
		double centerRow = (double) currentLocation.row() + 0.5;
		double centerCol = (double) currentLocation.col() + 0.5;

		if (super.getCurrentDirection() == Direction.RIGHT) {
			// If past center going right
			if (colExact > centerCol + ERR) {
				result = true;
			}
		} else if (super.getCurrentDirection() == Direction.LEFT) {
			// If past center going left
			if (colExact < centerCol - ERR) {
				result = true;
			}
		} else if (super.getCurrentDirection() == Direction.UP) {
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
	 * Sets the target of the ghost in chase mode.
	 * 
	 * @param d The descriptor of the current game.
	 * 
	 * @return The location of the target in chase mode.
	 */
	protected abstract Location setChaseTarget(Descriptor d);

}
