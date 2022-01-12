package fr.uge.BabaIsYou.Draw;

import java.util.Objects;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import fr.uge.BabaIsYou.Element.*;
import fr.uge.BabaIsYou.Read.Position;

/**
 * Class for board.
 */
public class Board {
	private final LinkedHashMap<Position, ArrayList<Element>> board;

	/**
	 * Constructor of Board which initialises a LinkedHashMap of ArrayList of Element
	 * as value and Position as key which represents the board of the game in the
	 * following program.
	 */
	public Board() {
		this.board = new LinkedHashMap<Position, ArrayList<Element>>();
	}

	/**
	 * This function put in board the element pos which represents the key of 
	 * the LinkedHashMap and elem is add in an ArrayList of Element then put in board.
	 * @param pos(key) 
	 * 		the position to add in the board.
	 * @param elem(value) 
	 * 		the element to add in the board.
	 */
	public void add(Position pos, Element elem) {
		Objects.requireNonNull(pos);
		Objects.requireNonNull(elem);
		var tmp = new ArrayList<Element>();
		tmp.add(elem);
		board.put(pos, tmp);
	}

	/**
	 * Getter of the LinkedHashMap board.
	 * @return
	 * 		the board.
	 */
	public LinkedHashMap<Position, ArrayList<Element>> Map() {
		return board;
	}

	/**
	 * Every ArrayList of Element with more than 1 element to one Position
	 * get their status become to push.
	 */
	public void setStatusLst() {
		for(var pos: board.keySet()) {
			if(board.get(pos).size() > 1) {
				for(var elmt: board.get(pos)) {
					elmt.setStatus(Status.Push);
				}
			}
		}
	}

	/**
	 * Change the element at the pos by his new position x,y.
	 * @param pos 
	 * 		the current position of the element.
	 * @param x
	 * 		the new position x after changement of the element.
	 * @param y
	 * 		the new position y after changement of the element.
	 */
	private void transfList(Position pos, int x, int y) {
		var tmp = new ArrayList<Element>();
		tmp.add(board.get(pos).get(1));
		board.get(pos).remove(1);
		board.put(new Position(x, y), tmp);
	}

	/**
	 * This function remove the player and the element with status Sink.
	 * @param pos 
	 * 		the position of the player.
	 * @param x 
	 * 		the new Position x of the element.
	 * @param y
	 * 		the new Position y of the element.
	 */
	private void destroyElem(Position pos, int x, int y) {
		if(board.get(pos) != null && board.get(new Position(x, y)) != null) {
			if(board.get(pos).size() > 1) {
				transfList(pos, x, y);
			}
			else board.remove(pos);
			board.remove(new Position(x, y));
		}
	}
	
	/**
	 * This function make necessary changements to the board, after a movement of a player
	 * or after push of elements.
	 * @param pos 
	 * 		the key where the changement(s) is did in board.
	 * @param x 
	 * 		the position x of the end of the last element pushed.
	 * @param y
	 * 		the position y of the end of the last element pushed.
	 */
	public void updateBoard(Position pos, int x, int y) {
		if(Rule.verifStatus(this, x, y, Status.Sink)) {
			destroyElem(pos,x,y);
			return;
		}
		if(board.get(new Position(x, y)) != null) {
			if(board.get(pos).size() > 1) {
				board.get(new Position(x, y)).add(board.get(new Position(x, y)).size(), board.get(pos).get(1));
				board.get(pos).remove(1);
				return;
			} else
				board.get(new Position(x, y)).add(board.get(new Position(x, y)).size(), board.get(pos).get(0));
		} else {
			if(board.get(pos).size() > 1) {
				transfList(pos, x, y);
				return;
			} else
				board.put(new Position(x, y), board.get(pos));
		}
		board.remove(pos);
	}

	/**
	 * Checks in board the status of the element at the position x, y.
	 * If the status of the element is equals to Push it return true.
	 * @param x
	 * 		the position x of the element where the status are compared.
	 * @param y
	 * 		the position y of the element where the status are compared.
	 * @param i
	 * 		represents movement index for up, down.
	 * @param j
	 * 		represents movement index for left, right.
	 * @return
	 * 		if the status of the element at the position x, y is equals to Push return true.
	 * 		Else return false;
	 */
	private boolean canPush(int x, int y, int i, int j) {
		for(var status: board.get(new Position(x + j, y + i)).get(0).status()) {
			if(status.equals(Status.Push))
				return true;
		}
		return false;
	}

	/**
	 * This function allows to the function Push in Rule to know how many element(s)
	 * the player try to push. While at the next position of the same direction where
	 * the player is moving we find an element we increment by 1.
	 * @param x 
	 * 		the position x of the element that the player try to push.
	 * @param y
	 * 		the position y of the element that the player try to push.
	 * @param move 
	 * 		the direction where the player try to move.
	 * @return
	 * 		return the number of element(s) that the player is trying to push.
	 */
	public int NumberElem(int x, int y, int move) {
		int tmp = 0;
		int ind = 0;
		var listPos = Position.listPos(new Position(x, y), 0);
		
		while(board.get(new Position(listPos.get(move).x(), listPos.get(move).y())) != null) {
			var listPos1 = Position.listPos(new Position(0, 0), ind);
			
			if(canPush(x, y, listPos1.get(move).y(), listPos1.get(move).x())) {
				tmp ++;
			} else { return tmp; }
			ind++;
			listPos = Position.listPos(new Position(x, y), ind);
		}
		return tmp;
	}

	/**
	 * This function call to updateBoard to update every element(s) that
	 * the player had trying to push.
	 * @param pos 
	 * 		the position of the player.
	 * @param tmp 
	 * 		the number element(s) to push.
	 * @param move 
	 * 		the direction where the player push.
	 */
	public void pushElem(Position pos, int tmp, int move) {
		int ind = tmp - 1;
		var listPos = Position.listPos(pos, ind);
		var listPos1 = Position.listPos(pos, ind + 1);
		
		while(ind >= 0 && Player.canMove(listPos, move, 1)) {
			updateBoard(new Position(listPos.get(move).x(), listPos.get(move).y()), listPos1.get(move).x(), listPos1.get(move).y());
			ind--;
			listPos = Position.listPos(pos, ind);
			listPos1 = Position.listPos(pos, 1 + ind);
		}
	}
	
	/**
	 * This function allows to the player if some conditions are respected to
	 * choose the ouput of the player who took a portal.
	 * @param b
	 * 		board of the game.
	 * @param p
	 * 		player of the game.
	 * @param pos 
	 * 		the current position of the player.
	 * @param tmp 
	 * 		the position of the player after choosing his output direction.
	 */
	public void PortalChoice(Board b, Player p, Position pos, Position tmp) {
		var listPos = Position.listPos(tmp, 1);

		for(int i = 0; i < 4; i++) {
			if(p.keypresses()[i] == 1 && !Rule.verifStatus(b, listPos.get(i).x(), listPos.get(i).y(), Status.Stop) && !Rule.verifStatus(b, listPos.get(i).x(), listPos.get(i).y(), Status.Hot) && !Rule.verifStatus(b, listPos.get(i).x(), listPos.get(i).y(), Status.Sink) && !Rule.verifStatus(b, listPos.get(i).x(), listPos.get(i).y(), Status.Defeat)) {
				b.add(new Position(listPos.get(i).x(), listPos.get(i).y()), b.Map().get(pos).get(b.Map().get(pos).size() - 1));
				b.add(pos, b.Map().get(pos).get(0));
				break;
			}
		}
	}

	/**
	 * Put every status of the board to None except the elements which represent
	 * the text where their status is Push.
	 */
	public void resetStatus() {
		for(var pos: board.keySet()) {
			for(int i = 0; i < board.get(pos).size(); i++) {
				if(board.get(pos) != null && (board.get(pos).get(i).name().charAt(board.get(pos).get(i).name().length() - 1) == 'T')) {
					board.get(pos).get(i).resetStatus(Status.Push);
				}
				else if(board.get(pos) != null && !(board.get(pos).get(i).name().charAt(board.get(pos).get(i).name().length() - 1) == 'T')) {
					board.get(pos).get(i).resetStatus(Status.None);
				}
			}
		}
	}

	/**
	 * Put in a LinkedHashMap every element and their position which have 
	 * a name equals to the setting "String name".
	 * @param name 
	 * 		the name where we are comparing.
	 * @return
	 * 		return an LinkedHashMap which contains every element for name the setting name.
	 */
	public LinkedHashMap<Position, ArrayList<Element>> findWithNameElem(String name) {
		var tmp = new LinkedHashMap<Position, ArrayList<Element>>();

		for(var pos: board.keySet()) {
			for(int i = 0; i < board.get(pos).size(); i++) {
				if(board.get(pos) != null && board.get(pos).get(i).name().equals(name)) {
					tmp.put(pos, board.get(pos));
				}
			}
		}
		return tmp;
	}
}