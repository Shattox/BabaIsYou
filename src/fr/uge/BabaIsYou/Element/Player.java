package fr.uge.BabaIsYou.Element;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import fr.uge.BabaIsYou.Draw.*;
import fr.uge.BabaIsYou.Read.Position;
import fr.umlv.zen5.KeyboardKey;

/**
 * Class for player.
 */
public class Player {
	private final int[] keypresses;
	private final KeyboardKey[] keys;
	private LinkedHashMap<Position, Element> player;

	/**
	 * The constructor of Player which initialises the array of keyboard key.
	 * The LinkedHashMap of Element as Position as key which represents the player
	 * in the following of the program.
	 */
	public Player() {
		keys = new KeyboardKey[4];
		keypresses = new int[4];
		assignKeys();
		this.player = new LinkedHashMap<Position, Element>();
	}

	/**
	 * Return player who is a LinkedHashMap which contains the elements which represent the player.
	 * @return 
	 * 		the player.
	 */
	public LinkedHashMap<Position, Element> Map() {
		return player;
	}

	/** 
	 * Return a clone of KeyBoard array.
	 * @return
	 * 		Clone of keys array.
	 */
	public KeyboardKey[] getKeys() {
		return keys.clone();
	}

	/**
	 * Assign to the array KeyBoard 
	 */
	private void assignKeys() {
		keys[0] = KeyboardKey.LEFT;
		keys[1] = KeyboardKey.UP;
		keys[2] = KeyboardKey.RIGHT;
		keys[3] = KeyboardKey.DOWN;
	}

	/**
	 * Checks if a KeyBoard is pressed.
	 * @return
	 * 		true if a keyboard is pressed. Else return false;
	 */
	private boolean Action(){
		for(int i = 0; i < keypresses.length; i++) {
			if(keypresses[i] == 1)
				return true;
		}
		return false;
	}

	/**
     * Checks the mode and changes the values in the keypresses table, 1 for pressed, 0 for released.
     * @param i
     * 		index of the key in keypresses[].
     * @param mode
     * 		the boolean (true for pressed key or false for released key).
     */
	public void setKeypress(int i, boolean mode) {
		keypresses[i] = 0;
		if(mode) {
			keypresses[i] = 1;
		}
	}
	
	/**
	 * Getter for the keyboard array.
	 * @return
	 * 		array of int.
	 */
	public int[] keypresses() {
		return keypresses;
	}
	
	/**
	 * This function build the player from rule if we a rule have as property You.
	 * @param b 
	 * 		the board of the game.
	 * @param p
	 * 		the player of the game.
	 * @param rule
	 * 		list of the rule of the game.
	 */ 
	public void buildPlayer(Board b, Player p, Rule rule) {
		for(var elem: rule.list()) {
			if(elem.get(2).name().equals("YouT")) {
				for(var pos: b.Map().keySet()) {
					var tmp = elem.get(0).name().substring(0, elem.get(0).name().length() - 1);
					for(int i = 0; i < b.Map().get(pos).size(); i++) {
						if(b.Map().get(pos) != null && b.Map().get(pos).get(i).name().equals(tmp)) {
							p.Map().put(pos, new Element(tmp, Type.None, Status.valueOf(elem.get(2).name().substring(0, elem.get(2).name().length() - 1))));
						}
					}
				}
			}
		}
	}

	/**
	 * Checks if the position of the element where the player try to move is defeat or hot.
	 * If it's the case return true else false.
	 * @param b 
	 * 		the board of the game.
	 * @param p
	 * 		the player of the game.
	 * @param pos 
	 * 		the position pos is the current position(s) of the player.
	 * @return 
	 * 		if after the movement of the player he stays alive return false. Else return true.
	 */
	private boolean Dead(Board b, Player p, Position pos) {
		var listPos = Position.listPos(pos, 1);
		
		for(int i = 0; i < 4; i++) {
			if(keypresses[i] == 1 && (Rule.verifStatus(b, listPos.get(i).x(), listPos.get(i).y(), Status.Defeat) || Rule.Hot(b, p, pos, listPos.get(i).x(), listPos.get(i).y()))) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks if after a movement the elements will stay in the window size.
	 * @param listPos 
	 * 		represents an ArrayList of Position that we will compare to min et max window size.
	 * @param move 
	 * 		represents the diretion where an action is made.
	 * @param value 
	 * 		make reference to the number of elements which are moving.
	 * @return 
	 * 		if the movement is in the window size return true. Else return false.
	 */
	public static boolean canMove(ArrayList<Position> listPos, int move, int value) {
		if(move == 0 && listPos.get(move).x() - value < 0)
			return false;
		if(move == 1 && listPos.get(move).y() - value < 0)
			return false;
		if(move == 2 && listPos.get(move).x() + value > 39)
			return false;
		if(move == 3 && listPos.get(move).y() + value > 26)
			return false;
		return true;
	}

	/**
	 * This function move the player is some conditions are true.
	 * We also call here the function to make moving a player through a portal.
	 * At the end of the function every elements of the player are removed.
	 * @param b 
	 * 		the board of the game.
	 * @param p 
	 * 		the player of the game.
	 * @param tmp
	 * 		represents the number element(s) that the player is trying to push.
	 */
	public void Move(Board b, Player p, int tmp) {
		if(Action()) {
			for(var pos: p.Map().keySet()) {
				if(!Dead(b, p, pos)) {
					if(Rule.verifStatus(b, pos.x(), pos.y(), Status.Tp)) {
						Rule.Portal(b, p);
						break;
					}
					var listPos = Position.listPos(pos, 1);
					for(int i = 0; i < 4; i++) {
						if(keypresses[i] == 1 && !Rule.verifStatus(b, listPos.get(i).x(), listPos.get(i).y(), Status.Stop) && canMove(listPos, i, 0) && canMove(listPos, i, tmp)) {
							b.updateBoard(pos, listPos.get(i).x(), listPos.get(i).y());
							break;
						}
					}
				}
				else b.Map().remove(pos);
			}
			player = new LinkedHashMap<Position, Element>();
		}
	}
}