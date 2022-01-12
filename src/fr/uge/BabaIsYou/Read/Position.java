package fr.uge.BabaIsYou.Read;

import java.util.ArrayList;

/**
 * Class for position of element.
 */
public class Position {
	private int x;
	private int y;
	
	/**
	 * Constructor of Position, which requires x and y coordinates of a play area box.
	 * @param x
	 * 		coordinate x of the box
	 * @param y
	 * 		coordinate y of the box
	 */
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Returns the value of the x position.
	 * @return 
	 * 		the value of coordinate x in position.
	 */
	public int x() {
		return x;
	}
	
	/**
	 * Returns the value of the y position.
	 * @return 
	 * 		the value of coordinate y in position.
	 */
	public int y() {
		return y;
	}
	
	/**
	 * Returns the distance between two positions.
	 * @param pos1
	 * 		the first position.
	 * @param pos2
	 * 		the second position.
	 * @return 
	 * 		The result of calculating the distance between the two positions.
	 */
	public static double distance(Position pos1, Position pos2) {
		return Math.sqrt(Math.pow(pos2.x() - pos1.x(), 2) + Math.pow(pos2.y() - pos1.y(), 2));
	}
	
	
	/**
	 * creates a list of four possible positions around a position (up, down, left, right).
	 * @param pos
	 * 		the position.
	 * @param value
	 * 		the integer you wanted.
	 * @return 
	 * 		returns the list of 4 positions according to the integer given.
	 */
	public static ArrayList<Position> listPos(Position pos, int value) {
		var list = new ArrayList<Position>();
		list.add(new Position(pos.x() - value, pos.y()));
		list.add(new Position(pos.x(), pos.y() - value));
		list.add(new Position(pos.x() + value, pos.y()));
		list.add(new Position(pos.x(), pos.y() + value));
		return list;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Position other = (Position) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
}